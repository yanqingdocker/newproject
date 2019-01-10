package cn.com.caogen.controller;

import cn.com.caogen.util.ConstantUtil;
import cn.com.caogen.util.FilterAuthUtil;
import cn.com.caogen.util.ResponseMessage;
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

/**
 * author:huyanqing
 * Date:2018/10/11
 */
@RestController
@RequestMapping("/util")
public class UtilController {
    private static Logger logger = LoggerFactory.getLogger(UtilController.class);
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @RequestMapping(path = "upAndroidUrl",method = RequestMethod.POST)
    public String upAndroidUrl(HttpServletRequest request, @RequestParam("url") String url){
        if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }
        logger.info("upUrl start:   url="+url);
        stringRedisTemplate.opsForValue().set("andriodurl",url);
        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS,null)).toString();
    }

    @RequestMapping(path = "upIosUrl",method = RequestMethod.POST)
    public String upIosUrl(HttpServletRequest request, @RequestParam("url") String url){
        if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }
        logger.info("upUrl start:   url="+url);
        stringRedisTemplate.opsForValue().set("iosurl",url);
        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS,null)).toString();
    }

    @RequestMapping(path = "upContent",method = RequestMethod.POST)
    public String upContent(HttpServletRequest request, @RequestParam("content") String content){
        if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }
        logger.info("upUrl start:   content="+content);
        stringRedisTemplate.opsForValue().set("content",content);
        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS,null)).toString();
    }


    @RequestMapping(path = "getAndriodUrl",method = RequestMethod.GET)
    public String getAndriodUrl(){
        logger.info("getAndriodUrl start:");
        String url=stringRedisTemplate.opsForValue().get("andriodurl");
        logger.info("getAndriodUrl start url=:"+url);
        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS,url)).toString();
    }
    @RequestMapping(path = "getIosUrl",method = RequestMethod.GET)
    public String getIosUrl(){
        logger.info("getIosUrl start:");
        String url=stringRedisTemplate.opsForValue().get("iosurl");
        logger.info("getIosUrl start url=:"+url);
        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS,url)).toString();
    }


    @RequestMapping(path = "getContent",method = RequestMethod.GET)
    public String getContent(){
        logger.info("getContent start:");
        String content=stringRedisTemplate.opsForValue().get("content");
        logger.info("getContent start content=:"+content);
        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS,content)).toString();
    }

}
