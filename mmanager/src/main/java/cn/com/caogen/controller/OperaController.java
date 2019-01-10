package cn.com.caogen.controller;

import cn.com.caogen.entity.Muser;
import cn.com.caogen.entity.Operation;
import cn.com.caogen.entity.Task;
import cn.com.caogen.entity.User;
import cn.com.caogen.mapper.OperaMapper;
import cn.com.caogen.service.IOperaService;
import cn.com.caogen.service.OperaServiceImpl;
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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * author:huyanqing
 * Date:2018/5/10
 */
@RestController
@RequestMapping("/opera")
public class OperaController {

    private static Logger logger = LoggerFactory.getLogger(OperaController.class);
    @Autowired
    private OperaServiceImpl operaServiceimpl;
    @Autowired
    private UserServiceImpl userServiceimpl;


    /**
     * 查询所有操作记录
     * @return
     */
    @RequestMapping(path = "/queryAll",method = RequestMethod.GET)
    public String queryAll(HttpServletRequest request){
        if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }
        logger.info("queryAll start:");

        return JSONArray.fromObject(operaServiceimpl.queryAll()).toString();
    }

    /**
     * 查询当天账户的所有记录
     * @return
     */
    @RequestMapping(path = "/queryByDay",method = RequestMethod.GET)
    public String queryByDay(@RequestParam("oi") String oi,HttpServletRequest request){
        if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }
        logger.info("queryByDay start: oi="+oi);
        if(!StringUtil.checkStrs(oi)){
            logger.error("queryByDay:"+ConstantUtil.ERROR_ARGS);
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.ERROR_ARGS)).toString();
        }

        Map<String,Object> parmMap=new HashMap<String,Object>();
        parmMap.put(ConstantUtil.QUERYDATE,ConstantUtil.DATE_TODAY);
        parmMap.put(ConstantUtil.MONEY_OI,Integer.parseInt(oi));
        Muser currentUser=(Muser)request.getSession().getAttribute("currentUser");
        parmMap.put("servicebranch",currentUser.getServicebranch());
        return JSONArray.fromObject(operaServiceimpl.queryByDate(parmMap)).toString();
    }


    /**
     * 查询本周账户的所有记录
     * @return
     */
    @RequestMapping(path = "/queryByWeek",method = RequestMethod.GET)
    public String queryByWeek(@RequestParam("oi") String oi,HttpServletRequest request){
        if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }
        logger.info("queryByWeek start: oi="+oi);
        if(!StringUtil.checkStrs(oi)){
            logger.error("queryByWeek:"+ConstantUtil.ERROR_ARGS);
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.ERROR_ARGS)).toString();
        }
        Map<String,Object> parmMap=new HashMap<String,Object>();
        parmMap.put(ConstantUtil.QUERYDATE,ConstantUtil.DATE_WEEK);
        parmMap.put(ConstantUtil.MONEY_OI,Integer.parseInt(oi));
        Muser currentUser=(Muser)request.getSession().getAttribute("currentUser");
        parmMap.put("servicebranch",currentUser.getServicebranch());
        return JSONArray.fromObject(operaServiceimpl.queryByDate(parmMap)).toString();
    }

    /**
     * 查询本月账户的所有记录
     * @return
     */
    @RequestMapping(path = "/queryByMonth",method = RequestMethod.GET)
    public String queryByMonth(@RequestParam("oi") String oi,HttpServletRequest request){
        if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }
        logger.info("queryByMonth start: oi="+oi);
        if(!StringUtil.checkStrs(oi)){
            logger.error("queryByMonth:"+ConstantUtil.ERROR_ARGS);
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.ERROR_ARGS)).toString();
        }
        Map<String,Object> parmMap=new HashMap<String,Object>();
        parmMap.put(ConstantUtil.QUERYDATE,ConstantUtil.DATE_MONTH);
        parmMap.put(ConstantUtil.MONEY_OI,Integer.parseInt(oi));
        Muser currentUser=(Muser)request.getSession().getAttribute("currentUser");
        parmMap.put("servicebranch",currentUser.getServicebranch());
        return JSONArray.fromObject(operaServiceimpl.queryByDate(parmMap)).toString();
    }

    /**
     * 查询本季度账户的所有记录
     * @return
     */
    @RequestMapping(path = "/queryByquarter",method = RequestMethod.GET)
    public String queryByquarter(@RequestParam("oi") String oi,HttpServletRequest request){
        logger.info("queryByquarter start: oi="+oi);
        if(!StringUtil.checkStrs(oi)){
            logger.error("queryByYear:"+ConstantUtil.ERROR_ARGS);
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.ERROR_ARGS)).toString();
        }
        Map<String,Object> parmMap=new HashMap<String,Object>();
        parmMap.put(ConstantUtil.QUERYDATE,ConstantUtil.DATE_QUARTER);
        parmMap.put(ConstantUtil.MONEY_OI,Integer.parseInt(oi));
        Muser currentUser=(Muser)request.getSession().getAttribute("currentUser");
        parmMap.put("servicebranch",currentUser.getServicebranch());
        return JSONArray.fromObject(operaServiceimpl.queryByDate(parmMap)).toString();
    }

    /**
     * 查询本年账户的所有记录
     * @return
     */
    @RequestMapping(path = "/queryByYear",method = RequestMethod.GET)
    public String queryByYear(@RequestParam("oi") String oi,HttpServletRequest request){
        logger.info("queryByYear start: oi="+oi);
        if(!StringUtil.checkStrs(oi)){
            logger.error(" queryByYear:"+ConstantUtil.ERROR_ARGS);
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.ERROR_ARGS)).toString();
        }
        Map<String,Object> parmMap=new HashMap<String,Object>();
        parmMap.put(ConstantUtil.QUERYDATE,ConstantUtil.DATE_YEAR);
        parmMap.put(ConstantUtil.MONEY_OI,Integer.parseInt(oi));
        Muser currentUser=(Muser)request.getSession().getAttribute("currentUser");
        parmMap.put("servicebranch",currentUser.getServicebranch());
        return JSONArray.fromObject(operaServiceimpl.queryByDate(parmMap)).toString();
    }


    @RequestMapping(path="queryoperatype",method = RequestMethod.GET)
    public String queryoperatype(@RequestParam("date") int date,HttpServletRequest request){
        logger.info("queryoperacount start: date="+date);
        if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }
        Muser currentUser=(Muser)request.getSession().getAttribute("currentUser");
        logger.info("user=:"+currentUser.getUsername());

        List<Map<String,Object>> list=operaServiceimpl .queryoperatype(date,currentUser.getServicebranch());

        return JSONArray.fromObject(list).toString();
    }

    /**
     * 查询各种操作的笔数
     * @param date
     * @param request
     * @return
     */
    @RequestMapping(path="queryoperacount",method = RequestMethod.GET)
    public String queryoperacount(@RequestParam("date") int date,HttpServletRequest request){
        logger.info("queryoperacount start: date="+date);
        if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }
        Muser currentUser=(Muser)request.getSession().getAttribute("currentUser");
        logger.info("user=:"+currentUser.getUsername());

        List<Map<String,Object>> list=operaServiceimpl .queryoperacount(date,currentUser.getServicebranch());
        return JSONArray.fromObject(list).toString();
    }


    @RequestMapping(path="queryCashLog",method = RequestMethod.GET)
    public String queryCashLog(HttpServletRequest request,@RequestParam("page") int page,@RequestParam("num") int num,@RequestParam("search") String key,@RequestParam("end") int end,@RequestParam("noend") int noend){
        logger.info("queryCashLog start:page="+page+"num="+num+"key="+key+",end="+end+",noend="+noend);
       if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }
        Muser currentUser=(Muser)request.getSession().getAttribute("currentUser");
        logger.info("user=:"+currentUser.getUsername());
        Map<String,Object> parmMap=new HashMap<String,Object>();
        parmMap.put("page",page*num);
        parmMap.put("num",num);
        if(StringUtil.checkStrs(key)){
            parmMap.put("key","%"+key+"%");
        }
        if(end==1&&noend==1){

        }else{
            if(end==1){
                parmMap.put("end","1");
            }else{
                parmMap.put("end",null);
            }
            if(noend==1){
                parmMap.put("noend","1");
            }else{
                parmMap.put("noend",null);
            }
        }

        Stream<Operation> list=operaServiceimpl .queryAll(parmMap,currentUser.getServicebranch()).stream();
        List<Operation> operationList=list.filter((e)->e.getOperaType().contains("现金")).collect(Collectors.toList());
        int count=operaServiceimpl.queryConditionCount(parmMap,currentUser.getServicebranch());
        JSONObject jsonObject=new JSONObject();

        jsonObject.element("count",count);
        jsonObject.element("data",operationList);
        return jsonObject.toString();
    }

    @RequestMapping(path="datarecover",method = RequestMethod.POST)
    public String datarecover(@RequestParam("snumber") String snumber,HttpServletRequest request){
        logger.info("datarecover start: snumber="+snumber);
        if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }
        Muser currentUser=(Muser)request.getSession().getAttribute("currentUser");
        logger.info("user=:"+currentUser.getUsername());
        int rs=operaServiceimpl.datarecover(snumber);
        if(rs==0){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
        }else{
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.ERROR_SNUMBER)).toString();
        }

    }




}
