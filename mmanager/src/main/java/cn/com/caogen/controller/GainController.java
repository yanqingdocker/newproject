package cn.com.caogen.controller;

import cn.com.caogen.entity.Appliy;
import cn.com.caogen.entity.CashPool;
import cn.com.caogen.entity.Gain;
import cn.com.caogen.entity.Muser;
import cn.com.caogen.service.AppliyServiceImpl;
import cn.com.caogen.service.CashPoolServiceImpl;
import cn.com.caogen.service.GainServiceImpl;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author:huyanqing
 * Date:2018/5/8
 */
@RestController
@RequestMapping("/gain")
public class GainController {
    private static Logger logger = LoggerFactory.getLogger(GainController.class);
    @Autowired
    private GainServiceImpl gainService;

    @Autowired
    private CashPoolServiceImpl cashPoolService;

    /**
     * 查询结算记录
     * @return
     */
    @RequestMapping(path = "querybyserverch",method = RequestMethod.GET)
    public String queryByServerch(@RequestParam("servicebranch") String servicebranch,HttpServletRequest request){
       if(!FilterAuthUtil.checkAuth(request)){
           return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
       }
        logger.info("queryAll start: servicebranch="+servicebranch);
        Map<String,String> parmMap=new HashMap<String,String>();
        parmMap.put("servicebranch",servicebranch);

        return JSONArray.fromObject(gainService.queryGains(parmMap)).toString();
    }

    /**
     * 新增结算
     * @return
     */
    @RequestMapping(path = "add",method = RequestMethod.POST)
    public String add(@RequestParam("servicebranch") String servicebranch, HttpServletRequest request){
       if(!FilterAuthUtil.checkAuth(request)){
           return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
       }
        logger.info("queryAll start: servicebranch="+servicebranch);
        CashPool cashPool=cashPoolService.queryByType("CNY",servicebranch).get(0);
        Gain gain=new Gain();
        gain.setCreatime(DateUtil.getTime());
        gain.setNum(cashPool.getBlance());
        gain.setCouttype("CNY");
        Muser currentUser=(Muser)request.getSession().getAttribute("currentUser");
        gain.setOperaor(currentUser.getUsername());
        gain.setSnumber(SerialnumberUtil.Getnum());
        gain.setServicebranch(servicebranch);
        gainService.add(gain);
        cashPool.setBlance(0.0);
        cashPoolService.update(cashPool);
        CashPool tempcashPool=cashPoolService.queryByType("CNY",ConstantUtil.SERVICE_BRANCH).get(0);
        tempcashPool.setBlance(tempcashPool.getBlance()-gain.getNum());
        cashPoolService.update(tempcashPool);
        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
    }

}
