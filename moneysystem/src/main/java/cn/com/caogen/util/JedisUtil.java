package cn.com.caogen.util;

import cn.com.caogen.controller.AppliyController;
import cn.com.caogen.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;

/**
 * author:huyanqing
 * Date:2018/5/22
 */
public class JedisUtil {
    private static Logger logger = LoggerFactory.getLogger(JedisUtil.class);
    public static Jedis jedis;
    public static JedisPool pool;
    static {
        JedisPoolConfig config = new JedisPoolConfig();
        pool = new JedisPool(config, "127.0.0.1", 6379, 100000);
    }
    public static Jedis getJedis(){


      /*  if(jedis!=null&&jedis.isConnected()==true){

            return jedis;
        }*/
        //r-j6ce364c2198fca4.redis.rds.aliyuncs.com
        try {

         /*   jedis = new Jedis("r-j6ce364c2198fca4.redis.rds.aliyuncs.com",6379);

            jedis.auth("Admin123");*/
            jedis= pool.getResource();

            return jedis;
        }catch (Exception e){
            return null;
        }


    }

    public static User getUser(HttpServletRequest request){
        Jedis jedis=getJedis();

        if(jedis==null){
            return null;
        }
        Map<String,Object> map=(Map)SerializeUtil.unserialize(jedis.get(ConstantUtil.SESSIONCOLLCTION.getBytes()));

        if(map==null){
            return null;
        }
        Object obj= map.get(request.getSession().getId());
        User currentUser=(User)SerializeUtil.unserialize((byte[])obj);
        jedis.close();
        return currentUser;
    }

    public static Map<String,Object> getSessionMap(){
        Jedis jedis=getJedis();
        if(jedis==null){
            return null;
        }
        Map<String,Object> sessionMap=(Map)SerializeUtil.unserialize(jedis.get(ConstantUtil.SESSIONCOLLCTION.getBytes()));
        if(sessionMap==null){
            return null;
        }
        jedis.close();
        return sessionMap;
    }
}
