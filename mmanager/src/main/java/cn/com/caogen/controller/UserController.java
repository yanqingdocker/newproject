package cn.com.caogen.controller;

import cn.com.caogen.entity.Count;
import cn.com.caogen.entity.Muser;
import cn.com.caogen.entity.Operation;
import cn.com.caogen.entity.User;
import cn.com.caogen.externIsystem.service.IDCardService;
import cn.com.caogen.externIsystem.service.MessageService;
import cn.com.caogen.externIsystem.util.Md5Util;
import cn.com.caogen.service.CountServiceImpl;
import cn.com.caogen.service.IUserService;
import cn.com.caogen.service.OperaServiceImpl;
import cn.com.caogen.util.*;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * author:huyanqing
 * Date:2018/4/19
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);


    @Autowired
    private IUserService userServiceImpl;

    @Autowired
    private CountServiceImpl countService;

    @Autowired
    private OperaServiceImpl operaService;

    private static String check_Num = "";

    private static String phone = "";
    public static final long baseCardId = 6884856023600001L;


    /**
     * 内部开户
     * @param username
     * @param phone
     * @param loginpwd
     * @param paypwd
     * @param request
     * @return
     */
    @RequestMapping(path = "/createuser", method = RequestMethod.POST)
    public String createuser(@RequestParam("username") String username,@RequestParam("phone") String phone,@RequestParam("loginpwd") String loginpwd,@RequestParam("paypwd") String paypwd,HttpServletRequest request) {
        if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }
        logger.info("createuser start: username="+username+",phone="+phone+",loginpwd="+loginpwd+",paypwd="+paypwd);
        if (!StringUtil.checkStrs(username,phone,loginpwd,paypwd)) {
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.ERROR_ARGS)).toString();
        }
        //查询手机号是否未注册
        Map<String,Object> parmMap=new HashMap<String,Object>();
        parmMap.put("phone",phone);
        List<User> userList=userServiceImpl.queryAll(parmMap);
        if(userList!=null&&!userList.isEmpty()){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.ALREADY_USER)).toString();
        }
        //新建会员
        User user=new User();
        user.setPhone(phone);
        user.setUsername(username);
        user.setPassword(Md5Util.strToMD5(loginpwd));
        user.setLeavel(1);
        user.setCreatetime(DateUtil.getTime());
        user.setIsauthentication(1);
        userServiceImpl.addUser(user);

        //开通双币账户
        userList=userServiceImpl.queryAll(parmMap);
        if(userList!=null&&!userList.isEmpty()){
            User temp=userList.get(0);
            countService.createCount(temp.getUsername(),"USD",Md5Util.strToMD5(paypwd),String.valueOf(temp.getUserid()));
            countService.createCount(temp.getUsername(),"CNY",Md5Util.strToMD5(paypwd),String.valueOf(temp.getUserid()));
        }

        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();


    }

    /**
     * 发送手机验证码
     * @param telphone
     */
    @RequestMapping(path = "/checkPhone", method = RequestMethod.POST)
    public String checkPhone(@RequestParam("telphone") String telphone,HttpServletRequest request) {
        if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }
        logger.info("checkPhone start: telphone="+telphone);
        if (!StringUtil.checkStrs(telphone)) {
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.ERROR_ARGS)).toString();
        }
        int num = MessageService.checkPhone(telphone);
        if (num != 0) {
            phone=telphone;
            check_Num = String.valueOf(num);
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
        }else{
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL)).toString();
        }
    }


    /**
     * 查看所有会员
     * @return
     */
    @RequestMapping(path = "/queryAll", method = RequestMethod.GET)
    public String batchdelete(HttpServletRequest request) {
        logger.info("queryMuser start: ");
        if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }


        Muser currentUser=(Muser)request.getSession().getAttribute("currentUser");
        logger.info("user=:"+currentUser.getUsername());
           List<User> users= userServiceImpl.queryAll(new HashMap<String,Object>());
           return JSONArray.fromObject(users).toString();

    }

    /**
     * 重置密码
     * @param telphone
     * @param password
     * @return
     */
    @RequestMapping(path="/resetpwd",method = RequestMethod.POST)
    public String resetpwd(@RequestParam("telphone") String telphone,@RequestParam("password") String password,HttpServletRequest request) {
        logger.info("resetpwd start: telphone="+telphone+",password="+password);
        if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }

        Muser currentUser=(Muser)request.getSession().getAttribute("currentUser");
        logger.info("user=:"+currentUser.getUsername());
        if (!StringUtil.checkStrs(telphone,password)) {
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.ERROR_ARGS)).toString();
        }
        password = MD5Util.string2MD5(password);
        User user=getUser(telphone,null);
        if(user==null){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL)).toString();
        }
        user.setPassword(password);
        user.setLasttime(DateUtil.getTime());
        userServiceImpl.update(user);
        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
    }

    /**
     * 授权VIP
     * @param telphone
     * @param
     * @return
     */
    @RequestMapping(path="/giveVip",method = RequestMethod.POST)
    public String giveVip(@RequestParam("telphone") String telphone,HttpServletRequest request) {
        logger.info("giveVip start: telphone="+telphone);
        if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }
        Muser currentUser=(Muser)request.getSession().getAttribute("currentUser");
        logger.info("user=:"+currentUser.getUsername());
        if (!StringUtil.checkStrs(telphone)) {
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.ERROR_ARGS)).toString();
        }
        User user=getUser(telphone,null);
        if(user==null){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL)).toString();
        }
        List<Count> countList=countService.queryByUserId(user.getUserid());
        Map<String,Object> parmMap=new HashMap<String, Object>();
        parmMap.put("operatype",ConstantUtil.EXCHANGE);
        parmMap.put("countid",countList.get(0).getCardId());
        List<Operation> operationList=operaService.queryAll(parmMap);

        if(!operationList.isEmpty()&&operationList.size()>3){
            user.setLeavel(1);
            user.setLasttime(DateUtil.getTime());
            userServiceImpl.update(user);
        }else{
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.GIVEVIP_NOTCONDTION)).toString();
        }

        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
    }

    public User getUser(String telphone,String userid){
        logger.info("getUser start: telphone="+telphone+",userid="+userid);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("phone", telphone);
        User temp = null;
        List<User> userList=userServiceImpl.queryAll(map);
        if(userList.isEmpty()){
            return null;
        }else {
            return userList.get(0);
        }
    }
    @RequestMapping(path = "queryByphone",method = RequestMethod.POST)
    public String queryByphone(@RequestParam("telphone") String telphone){
        User user=getUser(telphone,null);
        if(user==null){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.NOT_USER)).toString();
        }
        return JSONObject.fromObject(user).toString();
    }

}

