package cn.com.caogen.controller;

import cn.com.caogen.entity.Muser;
import cn.com.caogen.entity.Role;
import cn.com.caogen.entity.RoleAuth;
import cn.com.caogen.entity.UserRole;
import cn.com.caogen.service.*;
import cn.com.caogen.util.ConstantUtil;
import cn.com.caogen.util.FilterAuthUtil;
import cn.com.caogen.util.ResponseMessage;
import cn.com.caogen.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 * Date:2018/5/3
 */
@RestController
@RequestMapping("/muser")
public class MuserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private IUserService userServiceImpl;
    @Autowired
    private UserRoleServiceImpl userRoleService;
    @Autowired
    private RoleAuthServiceImpl roleAuthService;
    @Autowired
    private AuthoirtyServiceImpl authoirtyService;
    @Autowired
    private RoleServiceImpl roleService;
    private static Map<String,HttpSession> sessionMap =new HashMap<String,HttpSession>();

    private static Map<String,HttpServletResponse> respMap =new HashMap<String,HttpServletResponse>();

    private static Map<Integer,String> userMap =new HashMap<Integer,String>();


    /**
     * 删除成员信息
     * @param ids
     * @return
     */
    @RequestMapping(path = "/batchdelete", method = RequestMethod.GET)
    public String batchdelete(@RequestParam("ids") String ids,HttpServletRequest request) {
        logger.info("batchdelete start: ids="+ids);
        if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }
        Muser currentUser=(Muser)request.getSession().getAttribute("currentUser");
        logger.info("user=:"+currentUser.getUsername());
        if (StringUtil.checkStrs(ids)) {
            userServiceImpl.deleteUser(ids);
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
        }else {
            return net.sf.json.JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.ERROR_ARGS)).toString();
        }
    }

    /**
     * 查看成员信息
     * @return
     */
    @RequestMapping(path = "/queryAll", method = RequestMethod.GET)
    public String queryAll(HttpServletRequest request) {
        logger.info("queryMuser startst: ");
        if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }

        Muser currentUser=(Muser)request.getSession().getAttribute("currentUser");
        logger.info("user=:"+currentUser.getUsername());
        List<Muser> musers= userServiceImpl.queryMusers();
        for (Muser muser:musers){
            List<UserRole> userRoles=userRoleService.queryByUserId(muser.getId());
            for (UserRole userRole:userRoles){
                Role role=roleService.queryById(userRole.getRoleid());
                if(role!=null){
                    muser.getIdlist().add(role.getId());
                    muser.getNamelist().add(role.getRolename());
                }

            }
        }
        if(musers!=null){
            return JSONArray.fromObject(musers).toString();
        }
        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL)).toString();

    }

    /**
     * 重置密码
     * @param id
     * @param password
     * @return
     */
    @RequestMapping(path="/restPwd",method = RequestMethod.POST)
    public String update(@RequestParam("id") String id,@RequestParam("password") String password,HttpServletRequest request){
        logger.info("queryMuser startst: id="+id+",password="+password);
        if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }
        Muser currentUser=(Muser)request.getSession().getAttribute("currentUser");
        logger.info("user=:"+currentUser.getUsername());
        userServiceImpl.updateMuser(id,password);
        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
    }



    /**
     * 添加成员信息
     * @return
     */
    @RequestMapping(path ="/add", method = RequestMethod.POST)
    public String add(@RequestParam("servicebranch") String servicebranch,@RequestParam("username") String username,@RequestParam("password") String password,@RequestParam("roleids") String roleids,HttpServletRequest request) {
        logger.info("add start: servicebranch="+servicebranch+",username"+username+",password"+password+",roleids"+roleids);
        if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }

        Muser currentUser=(Muser)request.getSession().getAttribute("currentUser");
        logger.info("user=:"+currentUser.getUsername());
        if(!StringUtil.checkStrs(servicebranch,username,password,roleids)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.ERROR_ARGS)).toString();
        }
        Muser muser=new Muser();
        muser.setUsername(username);
        muser.setPassword(password);
        muser.setServicebranch(servicebranch);
        userServiceImpl.addMuser(muser);
        List<Muser> musers=userServiceImpl.queryMusers();
        int userid=0;
        for (Muser temp:musers){
            if(temp.getUsername().equals(muser.getUsername())&&temp.getPassword().equals(muser.getPassword())){
                userid=temp.getId();
                break;
            }
        }
        userRoleService.batchAdd(userid,roleids);
        return net.sf.json.JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
    }

    /**
     * 登录系统
     * @return
     */
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(@RequestParam("username") String username,@RequestParam("password") String password,HttpServletRequest request,HttpServletResponse response) {
        logger.info("login start: username="+username+",password"+password);


        boolean flag=StringUtil.checkStrs(username,password);
        if(!flag){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.ERROR_ARGS)).toString();
        }
        List<Muser> musers= userServiceImpl.queryMusers();
        if(!musers.isEmpty()){
            for(Muser muser:musers){
                if(username.equals(muser.getUsername())&&password.equals(muser.getPassword())){
                    request.getSession().setAttribute("userid",muser.getId());
                    request.getSession().setAttribute("username",muser.getUsername());
                    request.getSession().setAttribute("currentUser",muser);
                    request.getSession().setMaxInactiveInterval(36000);
                    getAuth(muser);
                    request.getSession().setAttribute("currentUser",muser);
                    Muser currentUser=(Muser)request.getSession().getAttribute("currentUser");
                    logger.info("user=:"+currentUser.getUsername());
                    return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
                }
            }
        }

        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL)).toString();

    }

    /**
     * 退出登录
     * @param request
     * @return
     */
    @RequestMapping(path = "/logout",method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        logger.info("logout start");
        Muser currentUser=(Muser)request.getSession().getAttribute("currentUser");
        logger.info("user=:"+currentUser.getUsername());

        sessionMap.remove(request.getSession().getId());
        userMap.remove(request.getSession().getAttribute("userid"));
        request.getSession().invalidate();


        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
    }

    /**
     * 退出登录
     * @param request
     * @return
     */
    @RequestMapping(path = "/getUser",method = RequestMethod.GET)
    public String getUser(HttpServletRequest request) {
        logger.info("getUser start");
        Muser currentUser=(Muser)request.getSession().getAttribute("currentUser");
        logger.info("user=:"+currentUser.getUsername());

        Muser muser=(Muser)request.getSession().getAttribute("currentUser");
        return JSONObject.fromObject(muser).toString();
    }

    private void getAuth(Muser muser){
       List<UserRole> userRoles=userRoleService.queryByUserId(muser.getId());
       for(UserRole userRole:userRoles){
           int roleId=userRole.getRoleid();
           List<RoleAuth> roleAuths=roleAuthService.queryByRoleId(roleId);
           for (RoleAuth roleAuth:roleAuths){
               muser.getAuths().add(authoirtyService.queryById(roleAuth.getAuthid()).getUrl());
           }
       }
    }


}
