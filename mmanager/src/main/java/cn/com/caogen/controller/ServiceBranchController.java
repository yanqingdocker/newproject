package cn.com.caogen.controller;

import cn.com.caogen.entity.Muser;
import cn.com.caogen.entity.ServiceBranch;
import cn.com.caogen.mapper.ServiceBranchMapper;
import cn.com.caogen.service.CashPoolServiceImpl;
import cn.com.caogen.service.ServiceBranchImpl;
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
import java.util.List;

/**
 * author:huyanqing
 * Date:2018/6/4
 */
@RestController
@RequestMapping("/servicebranch")
public class ServiceBranchController {
    private static Logger logger = LoggerFactory.getLogger(ServiceBranchController.class);
    @Autowired
    private ServiceBranchImpl ServiceBranchImpl;


    /**
     * 添加网点
     * @param datas
     * @param request
     * @return
     */
    @RequestMapping("add")
    public String add(@RequestParam("datas") String datas, HttpServletRequest request){
        logger.info("add start: datas="+datas);
        if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }
        Muser currentUser=(Muser)request.getSession().getAttribute("currentUser");
        logger.info("user=:"+currentUser.getUsername());
        if (!StringUtil.checkStrs(datas)) {
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.ERROR_ARGS)).toString();
        }
        JSONObject jsonObject=JSONObject.fromObject(datas);
        ServiceBranch serviceBranch=new ServiceBranch();
        serviceBranch.setBranchname(jsonObject.getString("branchname"));
        serviceBranch.setServicephone(jsonObject.getString("servicephone"));
        serviceBranch.setAddress(jsonObject.getString("address"));
        serviceBranch.setAdministrator(jsonObject.getString("administrator"));
        serviceBranch.setCreatetime(DateUtil.getDate());
        ServiceBranchImpl.add(serviceBranch);

        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
    }

    /**
     * 查询网点
     * @param request
     * @return
     */
    @RequestMapping("queryAll")
    public String queryAll(HttpServletRequest request){
       logger.info("queryAll start:");
       if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }
        Muser currentUser=(Muser)request.getSession().getAttribute("currentUser");
        logger.info("user=:"+currentUser.getUsername());
        List<ServiceBranch> serviceBranchList=ServiceBranchImpl.queryAll();
        return JSONArray.fromObject(serviceBranchList).toString();
    }

    /**
     * 删除网点
     * @param id
     * @param request
     * @return
     */
    @RequestMapping("delete")
    public String delete(@RequestParam("id") int id,HttpServletRequest request){
        logger.info("delete start: id="+id);
        if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }
        Muser currentUser=(Muser)request.getSession().getAttribute("currentUser");
        logger.info("user=:"+currentUser.getUsername());
        if (!StringUtil.checkStrs(String.valueOf(id))) {
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.ERROR_ARGS)).toString();
        }
        ServiceBranchImpl.delete(id);
        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
    }

    /**
     * 更新网点
     * @param datas
     * @param request
     * @return
     */
    @RequestMapping(path = "update",method = RequestMethod.POST)
    public String update(@RequestParam("datas") String datas,HttpServletRequest request){
        logger.info("update start: datas="+datas);
        if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }
        Muser currentUser=(Muser)request.getSession().getAttribute("currentUser");
        logger.info("user=:"+currentUser.getUsername());
        if (!StringUtil.checkStrs(datas)) {
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.ERROR_ARGS)).toString();
        }
        JSONObject jsonObject=JSONObject.fromObject(datas);
        int id=jsonObject.getInt("id");
        ServiceBranch serviceBranch=new ServiceBranch();
        serviceBranch.setId(id);
        serviceBranch.setBranchname(jsonObject.getString("branchname"));
        serviceBranch.setServicephone(jsonObject.getString("servicephone"));
        serviceBranch.setAddress(jsonObject.getString("address"));
        serviceBranch.setAdministrator(jsonObject.getString("administrator"));
        ServiceBranchImpl.update(serviceBranch);
        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
    }


    /**
     * 查询单个网点
     * @param id
     * @param request
     * @return
     */
    @RequestMapping("queryById")
    public String queryById(@RequestParam("id") int id,HttpServletRequest request){
        logger.info("queryById start: id="+id);
        if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }
        Muser currentUser=(Muser)request.getSession().getAttribute("currentUser");
        logger.info("user=:"+currentUser.getUsername());
        if (!StringUtil.checkStrs(String.valueOf(id))) {
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.ERROR_ARGS)).toString();
        }
        return JSONObject.fromObject( ServiceBranchImpl.queryById(id)).toString();
    }
}
