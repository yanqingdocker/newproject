package cn.com.caogen.util;


import redis.clients.jedis.Jedis;


/**
 * author:huyanqing
 * Date:2018/5/22
 */
public class JedisUtil {
    public static Jedis jedis;

    public static Jedis getJedis(){
        if(jedis!=null){
            return jedis;
        }
        //r-j6ce364c2198fca4.redis.rds.aliyuncs.com
        try {
            jedis = new Jedis("r-j6ce364c2198fca4.redis.rds.aliyuncs.com",6379);
            //jedis.auth("Admin123");
            return jedis;
        }catch (Exception e){

        }finally {
            return null;
        }


    }


}
