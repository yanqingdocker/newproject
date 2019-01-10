package cn.com.caogen.controller;

import cn.com.caogen.entity.Muser;
import cn.com.caogen.mapper.RedisMapper;
import cn.com.caogen.util.*;
import net.sf.json.JSON;
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
import java.util.*;

/**
 * author:huyanqing
 * Date:2018/4/19
 */
@RestController
@RequestMapping("/rate")
public class RateController {

    private static Logger logger = LoggerFactory.getLogger(RateController.class);
    @Autowired
    private RedisMapper redisMapper;

    /**
     * 获取汇率
     *
     * @param request
     * @return
     */
    @RequestMapping(path = "/queryAll", method = RequestMethod.GET)
    public String getRates(HttpServletRequest request) {
        logger.info("queryAll start");
        if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }

        Muser currentUser=(Muser)request.getSession().getAttribute("currentUser");
        logger.info("user=:"+currentUser.getUsername());
        List<String> list = new ArrayList<String>(7);
        list.add(redisMapper.getvalueBykey(ConstantUtil.ONE).getKvalue());
        list.add(redisMapper.getvalueBykey(ConstantUtil.TWO).getKvalue());
        list.add(redisMapper.getvalueBykey(ConstantUtil.THREE).getKvalue());
        list.add(redisMapper.getvalueBykey(ConstantUtil.FIVE).getKvalue());
        list.add(redisMapper.getvalueBykey(ConstantUtil.SIX).getKvalue());
        list.add(redisMapper.getvalueBykey(ConstantUtil.SENVEN).getKvalue());

        return JSONArray.fromObject(list).toString();
    }

    /**
     * 获取IP
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getIp", method = RequestMethod.GET)
    public String getIp(HttpServletRequest request) {
        return IpUtil.getIpAddr(request);
    }

    @RequestMapping(path = "/getSingleRate", method = RequestMethod.GET)
    public String getSingleRate(@RequestParam("type") String type,HttpServletRequest request){
        logger.info("getSingleRate start: type="+type);
        Muser currentUser=(Muser)request.getSession().getAttribute("currentUser");
        logger.info("user=:"+currentUser.getUsername());
        String rs=redisMapper.getvalueBykey(ConstantUtil.SENVEN).getKvalue();
        JSONObject jsonObject=JSONObject.fromObject(rs);
        String buyPid=jsonObject.getJSONObject(type).getString("buyPic");
        String sellPic=jsonObject.getJSONObject(type).getString("sellPic");
        StringBuffer sb=new StringBuffer();
        sb.append("{'buyPic':'").append(buyPid).append("','sellPic':'").append(sellPic).append("'}");

        return   JSONObject.fromObject(sb.toString()).toString();
    }

    /**
     * 修改买卖汇率
     * @param type
     * @param sellPic 卖出价
     * @param buyPic 买入价
     * @return
     */
    @RequestMapping(path = "/update",method = RequestMethod.POST)
    public String updateRate(HttpServletRequest request,@RequestParam("type") String type,@RequestParam("sellPic") String sellPic,@RequestParam("buyPic") String buyPic){
        logger.info("update rate start type="+type+",sellPic="+sellPic+",buyPic="+buyPic);

        if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }
        Muser currentUser=(Muser)request.getSession().getAttribute("currentUser");
        logger.info("user=:"+currentUser.getUsername());
        if(!StringUtil.checkStrs(type,sellPic,buyPic)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.ERROR_ARGS)).toString();
        }
        String rs=redisMapper.getvalueBykey(ConstantUtil.SENVEN).getKvalue();
        if(StringUtil.checkStrs(rs)){
            JSONObject jsonObject=JSONObject.fromObject(rs).getJSONObject(type);
            jsonObject.element("sellPic",buyPic);
            jsonObject.element("buyPic",sellPic);
            JSONObject result=JSONObject.fromObject(rs);
            result.element(type,jsonObject);
            Map<String,String> parm =new HashMap<String,String>();
            parm.put("vkey",ConstantUtil.SENVEN);
            parm.put("kvalue",result.toString());
            redisMapper.update(parm);

        }else{
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
        }

        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
    }
}
