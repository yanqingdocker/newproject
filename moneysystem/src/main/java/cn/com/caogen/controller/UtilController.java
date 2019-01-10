package cn.com.caogen.controller;

import cn.com.caogen.util.ConstantUtil;
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
    @RequestMapping(path = "getAndriodUrl",method = RequestMethod.GET)
    public String getAndriodUrl(){

        logger.info("getAndriodUrl start:");
       String url=stringRedisTemplate.opsForValue().get("andriodurl");
        logger.info("getAndriodUrl start url=:"+url);
        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS,"111")).toString();
    }
    @RequestMapping(path = "getIosUrl",method = RequestMethod.GET)
    public String getIosUrl(){
        logger.info("getIosUrl start:");
     /*   String url=stringRedisTemplate.opsForValue().get("iosurl");http://yanqingresponstory.oss-cn-hongkong.aliyuncs.com/nfgjversion/app1-3.apk
        logger.info("getIosUrl start url=:"+url);*/
        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS,"http://yanqingresponstory.oss-cn-hongkong.aliyuncs.com/nfgjversion/app1-3.apk")).toString();
    }

}
