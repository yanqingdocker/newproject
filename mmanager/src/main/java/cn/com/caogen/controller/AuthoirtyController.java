package cn.com.caogen.controller;

import cn.com.caogen.entity.Authoirty;
import cn.com.caogen.entity.Muser;
import cn.com.caogen.service.IAuthoirtyService;
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

/**
 * author:huyanqing
 * Date:2018/5/11
 */
@RestController
@RequestMapping("/authoirty")
public class AuthoirtyController {
    private static Logger logger = LoggerFactory.getLogger(AuthoirtyController.class);
    @Autowired
    private IAuthoirtyService authoirtyService;

    /**
     * 添加接口
     * @param authoirtyname
     * @param url
     * @return
     */
    @RequestMapping(path = "add",method =RequestMethod.POST )
    public String add(@RequestParam("authoirtyname") String authoirtyname,@RequestParam("url") String url,HttpServletRequest request){
        if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }
        logger.info("add start");
        Muser currentUser=(Muser) request.getSession().getAttribute("currentUser");
        logger.info("user=:"+currentUser.getUsername());
        if(!StringUtil.checkStrs(authoirtyname,url)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.ERROR_ARGS)).toString();
        }
        Authoirty authoirty=new Authoirty();
        authoirty.setAuthoirtyname(authoirtyname);
        authoirty.setUrl(url);
        authoirtyService.add(authoirty);
        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
    }

    /**
     * 修改接口
     * @param id
     * @param authoirtyname
     * @param url
     * @return
     */
    @RequestMapping(path = "update",method =RequestMethod.POST )
    public String update(@RequestParam("id") String id,@RequestParam("authoirtyname") String authoirtyname,@RequestParam("url") String url,HttpServletRequest request){
        if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }
        logger.info("add update");
        Muser currentUser=(Muser) request.getSession().getAttribute("currentUser");
        logger.info("user=:"+currentUser.getUsername());
        if(!StringUtil.checkStrs(id,authoirtyname,url)){
            logger.error("update"+ConstantUtil.ERROR_ARGS);
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.ERROR_ARGS)).toString();
        }
        Authoirty authoirty=authoirtyService.queryById(Integer.parseInt(id));
        if(authoirty==null){
            logger.info("update: authirty is null");
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL)).toString();
        }
        authoirty.setAuthoirtyname(authoirtyname);
        authoirty.setUrl(url);
        authoirtyService.update(authoirty);
        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();

    }

    /**
     * 删除接口
     * @param ids
     * @return
     */
    @RequestMapping(path = "/delete",method =RequestMethod.GET )
    public String delete(@RequestParam("ids") String ids,HttpServletRequest request){
        if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }
        logger.info("delete start: ids="+ids);
        Muser currentUser=(Muser) request.getSession().getAttribute("currentUser");
        logger.info("user=:"+currentUser.getUsername());
        if (!StringUtil.checkStrs(ids)) {
            logger.error("delete"+ConstantUtil.ERROR_ARGS);
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.ERROR_ARGS)).toString();
        }
        authoirtyService.delete(ids);
        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();


    }

    /**
     * 查询所有接口
     * @return
     */
    @RequestMapping(path = "queryAll",method =RequestMethod.GET )
    public String queryAll(HttpServletRequest request){
        if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }
        logger.info("queryAll");
        Muser currentUser=(Muser) request.getSession().getAttribute("currentUser");
        logger.info("user=:"+currentUser.getUsername());
        return JSONArray.fromObject(authoirtyService.queryAll()).toString();
    }

}
