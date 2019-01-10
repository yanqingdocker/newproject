package cn.com.caogen.controller;

import cn.com.caogen.entity.Appliy;
import cn.com.caogen.entity.Muser;
import cn.com.caogen.entity.Profits;
import cn.com.caogen.mapper.ProfitsMapper;
import cn.com.caogen.service.AppliyServiceImpl;
import cn.com.caogen.service.ProfitsServiceImpl;
import cn.com.caogen.util.ConstantUtil;
import cn.com.caogen.util.DateUtil;
import cn.com.caogen.util.FilterAuthUtil;
import cn.com.caogen.util.ResponseMessage;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author:huyanqing
 * Date:2018/5/8
 */
@RestController
@RequestMapping("/profits")
public class ProfitsController {
    private static Logger logger = LoggerFactory.getLogger(ProfitsController.class);
    @Autowired
    private ProfitsServiceImpl profitsService;


    @RequestMapping(path = "add",method = RequestMethod.POST)
    public String add(@RequestParam("destobj") String destobj,@RequestParam("moneytype") String moneytype,@RequestParam("num") Double num,@RequestParam("remark") String remark, HttpServletRequest request) throws Exception {
        logger.info("add start: destobj="+destobj+",moneytype="+moneytype+",num="+num+",remark="+remark);
        if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }
        Muser currentUser=(Muser)request.getSession().getAttribute("currentUser");
        logger.info("user=:"+currentUser.getUsername());
        Profits profits=new Profits();
        profits.setDestobj(destobj);
        profits.setCreattime(DateUtil.getTime());
        profits.setMoneytype(moneytype);
        profits.setNum(num);
        profits.setRemark(remark);
        Muser muser=(Muser)request.getSession().getAttribute("currentUser");
        profits.setOperauser(muser.getUsername());
        int rs=profitsService.add(profits);
        if(rs==0){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.SYSTEMCOUNT_LESS)).toString();
        }
        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
    }




   @RequestMapping(path = "queryAll",method = RequestMethod.GET)
    public String queryAll(HttpServletRequest request) throws Exception {
       logger.info("queryAll start:");
        if(!FilterAuthUtil.checkAuth(request)){
           return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
       }

       Muser currentUser=(Muser)request.getSession().getAttribute("currentUser");
       logger.info("user=:"+currentUser.getUsername());
        Map<String,Object> parmMap=new HashMap<String,Object>();
        List<Profits> profits=profitsService.queryAll();
        return JSONArray.fromObject(profits).toString();
      }

}
