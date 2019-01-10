package cn.com.caogen.mapper;

import cn.com.caogen.entity.Gain;
import cn.com.caogen.entity.Muser;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * author:huyanqing
 * Date:2018/5/3
 */
@Component
public interface GainMapper {
    void add(Gain gain);
    List<Gain> queryGains(Map<String,String> parmMap);
}
