package cn.com.caogen.controller;

import cn.com.caogen.entity.CashPool;
import cn.com.caogen.entity.Redis;
import cn.com.caogen.externIsystem.service.RateService;
import cn.com.caogen.mapper.RedisMapper;
import cn.com.caogen.service.CashPoolServiceImpl;
import cn.com.caogen.util.ConstantUtil;
import cn.com.caogen.util.DataMonitor;
import cn.com.caogen.util.JedisUtil;
import cn.com.caogen.util.SerializeUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * author:huyanqing
 * Date:2018/4/23
 */

@Component
@Order(value = 1)
public class Starup implements CommandLineRunner {

    private static Logger logger = LoggerFactory.getLogger(Starup.class);
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private CashPoolServiceImpl cashPoolService;
    @Autowired
    private RedisMapper redisMapper;
    /**
     * 系统启动时首先调用汇率接口存入redis
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {

    }
   // @Override
    public void run3(String... args) throws Exception {
       String result = RateService.getRequest2();
        if(result==null){
            return;
        }
        result=DataMonitor.reSet(result);
        List<CashPool> cashPoolList=cashPoolService.queryAll();
        Redis one=new Redis();
        one.setVkey(ConstantUtil.ONE);
        one.setKvalue(result);
        redisMapper.add(one);

        one.setVkey(ConstantUtil.TWO);
        one.setKvalue(result);
        redisMapper.add(one);

        one.setVkey(ConstantUtil.THREE);
        one.setKvalue(result);
        redisMapper.add(one);

        one.setVkey(ConstantUtil.FOURE);
        one.setKvalue(result);
        redisMapper.add(one);

        one.setVkey(ConstantUtil.FIVE);
        one.setKvalue(result);
        redisMapper.add(one);

        one.setVkey(ConstantUtil.SIX);
        one.setKvalue(result);
        redisMapper.add(one);

        one.setVkey(ConstantUtil.SENVEN);
        one.setKvalue(result);
        redisMapper.add(one);

        one.setVkey("iosurl");
        one.setKvalue("iosurl");
        redisMapper.add(one);

        one.setVkey("content");
        one.setKvalue("content");
        redisMapper.add(one);

        one.setVkey("cash");
        one.setKvalue("cash");
        redisMapper.add(one);

        one.setVkey("andriodurl");
        one.setKvalue("andriodurl");
        redisMapper.add(one);

        List<String> list = new ArrayList<String>(7);
        list.add(redisMapper.getvalueBykey(ConstantUtil.ONE).getKvalue());
        list.add(redisMapper.getvalueBykey(ConstantUtil.TWO).getKvalue());
        list.add(redisMapper.getvalueBykey(ConstantUtil.THREE).getKvalue());
        list.add(redisMapper.getvalueBykey(ConstantUtil.FIVE).getKvalue());
        list.add(redisMapper.getvalueBykey(ConstantUtil.SIX).getKvalue());
        list.add(redisMapper.getvalueBykey(ConstantUtil.SENVEN).getKvalue());

         /* try {
              if(stringRedisTemplate.opsForValue().get(ConstantUtil.ONE)==null){
                  stringRedisTemplate.opsForValue().set(ConstantUtil.ONE, result);
              }

              if(stringRedisTemplate.opsForValue().get(ConstantUtil.TWO)==null){
                  stringRedisTemplate.opsForValue().set(ConstantUtil.TWO, result);
              }

              if(stringRedisTemplate.opsForValue().get(ConstantUtil.THREE)==null){
                  stringRedisTemplate.opsForValue().set(ConstantUtil.THREE, result);
              }

              if(stringRedisTemplate.opsForValue().get(ConstantUtil.FOURE)==null){
                  stringRedisTemplate.opsForValue().set(ConstantUtil.FOURE, result);
              }

              if(stringRedisTemplate.opsForValue().get(ConstantUtil.FIVE)==null){
                  stringRedisTemplate.opsForValue().set(ConstantUtil.FIVE, result);
              }

              if(stringRedisTemplate.opsForValue().get(ConstantUtil.SIX)==null){
                  stringRedisTemplate.opsForValue().set(ConstantUtil.SIX, result);
              }

              if(stringRedisTemplate.opsForValue().get(ConstantUtil.SENVEN)==null){
                  stringRedisTemplate.opsForValue().set(ConstantUtil.SENVEN, result);
              }
              stringRedisTemplate.opsForValue().set(ConstantUtil.ONE,stringRedisTemplate.opsForValue().get(ConstantUtil.TWO));
              stringRedisTemplate.opsForValue().set(ConstantUtil.TWO,stringRedisTemplate.opsForValue().get(ConstantUtil.THREE));
              stringRedisTemplate.opsForValue().set(ConstantUtil.THREE,stringRedisTemplate.opsForValue().get(ConstantUtil.FOURE));
              stringRedisTemplate.opsForValue().set(ConstantUtil.FOURE,stringRedisTemplate.opsForValue().get(ConstantUtil.FIVE));
              stringRedisTemplate.opsForValue().set(ConstantUtil.FIVE,stringRedisTemplate.opsForValue().get(ConstantUtil.SIX));
              stringRedisTemplate.opsForValue().set(ConstantUtil.SIX,stringRedisTemplate.opsForValue().get(ConstantUtil.SENVEN));
              stringRedisTemplate.opsForValue().set(ConstantUtil.SENVEN,result);
              if(SerializeUtil.unserialize(JedisUtil.getJedis().get("cash".getBytes()))!=null){
                  JedisUtil.getJedis().set("cash".getBytes(),SerializeUtil.serialize(cashPoolList));
              }

          }catch (Exception e){
                logger.info("exception");

          }

          try {
              if(SerializeUtil.unserialize(JedisUtil.getJedis().get("cash".getBytes()))!=null){
                  JedisUtil.getJedis().set("cash".getBytes(),SerializeUtil.serialize(cashPoolList));
              }

          }catch (Exception e){
              logger.info("exception");
          }*/
        logger.info("now time:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));




    }

}
