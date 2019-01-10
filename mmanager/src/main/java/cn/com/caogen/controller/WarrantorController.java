package cn.com.caogen.controller;

import cn.com.caogen.entity.Muser;
import cn.com.caogen.entity.Warrantor;
import cn.com.caogen.mapper.WarrantorMapper;
import cn.com.caogen.service.WarrantorServiceImpl;
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

/**
 * author:huyanqing
 * Date:2018/6/7
 */
@RestController
@RequestMapping("/warrantor")
public class WarrantorController {

    private static Logger logger = LoggerFactory.getLogger(WarrantorController.class);
    @Autowired
    private WarrantorServiceImpl warrantorService;

    /**
     * 添加担保人
     * @param request
     * @return
     */
    @RequestMapping(path = "add",method = RequestMethod.POST)
    public String add(@RequestParam("datas") String datas,HttpServletRequest request){
        logger.info("add warrantor start; datas="+datas);
        if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }
        Muser currentUser=(Muser)request.getSession().getAttribute("currentUser");
        logger.info("user=:"+currentUser.getUsername());
        JSONObject jsonObject=JSONObject.fromObject(datas);
        try {
            Warrantor warrantor=(Warrantor) StringUtil.toBean(Warrantor.class,jsonObject);
            warrantor.setCreatetime(DateUtil.getDate());
            warrantorService.add(warrantor);
        }catch (Exception e){

        }
       return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
    }

    /**
     * 查询所有担保人信息
     * @param request
     * @return
     */
    @RequestMapping(path = "queryAll",method = RequestMethod.GET)
    public String queryAll(HttpServletRequest request){
        logger.info("queryAll start:");
        if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }

        return JSONArray.fromObject(warrantorService.queryAll()).toString();
    }
    /**
     * 查询单个担保人信息
     * @param request
     * @return
     */
    @RequestMapping(path = "queryById",method = RequestMethod.GET)
    public String queryById(@RequestParam("id") int id, HttpServletRequest request){

        return JSONObject.fromObject(warrantorService.queryById(id)).toString();
    }


    /**
     * 更新担保人信息
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
        JSONObject jsonObject=JSONObject.fromObject(datas);
        try{
            Warrantor warrantor=(Warrantor) StringUtil.toBean(Warrantor.class,jsonObject);
            warrantorService.update(warrantor);

        }catch (Exception e){

        }
        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
    }

    /**
     * 删除担保人信息
     * @param request
     * @return
     */
    @RequestMapping(path = "deleteById",method = RequestMethod.GET)
    public String deleteById(@RequestParam("id") int id, HttpServletRequest request){
        logger.info("deleteById start: id="+id);

        if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }
        Muser currentUser=(Muser)request.getSession().getAttribute("currentUser");
        logger.info("user=:"+currentUser.getUsername());
        warrantorService.deleteById(id);
        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
    }
}
