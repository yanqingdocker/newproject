package cn.com.caogen.cron;


import cn.com.caogen.controller.UserController;
import cn.com.caogen.externIsystem.service.RateService;
import cn.com.caogen.mapper.RedisMapper;
import cn.com.caogen.util.ConstantUtil;
import cn.com.caogen.util.DataMonitor;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * author:huyanqing
 * Date:2018/4/23
 */
@Component
public class CronTask {

    private static Logger logger = LoggerFactory.getLogger(CronTask.class);

    @Autowired
    private RedisMapper redisMapper;

    /**
     * //0 0/2 * * * ?  每隔2分钟打印一次
     *  每天0晨12点调用一次汇率接口，并刷新redis //1 0 0 * * ?
     */
    @Scheduled(cron = "1 0 0 * * ?")
    public void setRate(){
        String result = RateService.getRequest2();
         result=DataMonitor.reSet(result);
         logger.info("result==="+result);
         Map<String,String> parmMap=new HashMap<String,String>();


        parmMap.put(ConstantUtil.ONE,redisMapper.getvalueBykey(ConstantUtil.TWO).getKvalue());
        redisMapper.update(parmMap);
        parmMap.clear();

        parmMap.put(ConstantUtil.TWO,redisMapper.getvalueBykey(ConstantUtil.THREE).getKvalue());
        redisMapper.update(parmMap);
        parmMap.clear();

        parmMap.put(ConstantUtil.THREE,redisMapper.getvalueBykey(ConstantUtil.FOURE).getKvalue());
        redisMapper.update(parmMap);
        parmMap.clear();

        parmMap.put(ConstantUtil.FOURE,redisMapper.getvalueBykey(ConstantUtil.FIVE).getKvalue());
        redisMapper.update(parmMap);
        parmMap.clear();

        parmMap.put(ConstantUtil.FIVE,redisMapper.getvalueBykey(ConstantUtil.SIX).getKvalue());
        redisMapper.update(parmMap);
        parmMap.clear();

        parmMap.put(ConstantUtil.SIX,redisMapper.getvalueBykey(ConstantUtil.SENVEN).getKvalue());
        redisMapper.update(parmMap);
        parmMap.clear();

        parmMap.put(ConstantUtil.SENVEN,result);
        redisMapper.update(parmMap);
        parmMap.clear();


        logger.info("now time:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }



}
