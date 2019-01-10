package cn.com.caogen.controller;

import cn.com.caogen.entity.Borrow;
import cn.com.caogen.entity.Muser;
import cn.com.caogen.entity.User;
import cn.com.caogen.mapper.BorrowMapper;
import cn.com.caogen.service.BorrowServiceImpl;
import cn.com.caogen.service.UserServiceImpl;
import cn.com.caogen.util.*;
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
import java.util.HashMap;
import java.util.Map;

/**
 * author:huyanqing
 * Date:2018/6/7
 */
@RestController
@RequestMapping("/borrow")
public class BorrowController {

    private static Logger logger = LoggerFactory.getLogger(BorrowController.class);
    @Autowired
    private BorrowServiceImpl borrowService;
    @Autowired
    private UserServiceImpl userService;

    /**
     * 新增借贷记录
     * @param request
     * @return
     */
    @RequestMapping(path = "add",method = RequestMethod.POST)
    public String add(@RequestParam("datas") String datas, HttpServletRequest request){
        logger.info("addborrow start: datas="+datas);
        Muser currentUser=(Muser) request.getSession().getAttribute("currentUser");
        logger.info("user=:"+currentUser.getUsername());
        JSONObject jsonObject=JSONObject.fromObject(datas);
        Borrow borrow=null;
        try {
            borrow=(Borrow) StringUtil.toBean(Borrow.class,jsonObject);
        }catch (Exception e){
            logger.info("error");
        }

      logger.info(borrow.toString());
        Map<String,Object> parmMap=new HashMap<String,Object>();
        parmMap.put("phone",borrow.getBorrowerphone());
        User borrower=userService.queryAll(parmMap).get(0);
        if(borrower.getIsauthentication()==0){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.NOTUSER_OR_NOTAUTENTENTION)).toString();
        }
       if(borrower.getLeavel()==0){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.LEAVEL_NOTALLOW)).toString();
        }
        borrow.setCreatetime(DateUtil.getTime());
        borrow.setBorrower(borrower.getUsername());
        borrow.setSnumber(SerialnumberUtil.Getnum());
        borrow.setServicebranch(currentUser.getServicebranch());
        borrow.setOperauser(currentUser.getUsername());
        String message=borrowService.add(borrow,IpUtil.getIpAddr(request));
        if(message==null){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
        }
        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,message)).toString();

    }

    /**
     * 查询所有借贷记录
     * @param status
     * @param request
     * @return
     */
    @RequestMapping(path = "queryAll",method = RequestMethod.GET)
    public String queryAll(@RequestParam("status") int status, HttpServletRequest request){
        logger.info("queryAllborrower start=:"+status);
        Muser currentUser=(Muser) request.getSession().getAttribute("currentUser");
        logger.info("user=:"+currentUser.getUsername());
        Map<String,Object> par=new HashMap<String,Object>();
        par.put("status",status);

        return JSONArray.fromObject(borrowService.queryAll(par)).toString();
    }

    /**
     * 确认还款
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(path = "confirmdo",method = RequestMethod.POST)
    public String confirmdo(@RequestParam("id") int id, HttpServletRequest request){
        logger.info("confirmdo start: id="+id);
        Muser currentUser=(Muser) request.getSession().getAttribute("currentUser");
        logger.info("user=:"+currentUser.getUsername());
        if(id==0){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.ERROR_ARGS)).toString();
        }

        borrowService.update(id,IpUtil.getIpAddr(request));
        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
    }
}
