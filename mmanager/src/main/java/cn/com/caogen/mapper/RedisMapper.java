package cn.com.caogen.mapper;

import cn.com.caogen.entity.Redis;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * author:huyanqing
 * Date:2019/1/5
 */
@Repository
public interface RedisMapper {
    void update(Map<String,String> parmMap);
    Redis getvalueBykey(String kkey);
    void add(Redis redis);
}
