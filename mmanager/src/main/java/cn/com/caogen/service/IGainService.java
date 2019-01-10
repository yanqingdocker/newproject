package cn.com.caogen.service;

import cn.com.caogen.entity.Gain;
import cn.com.caogen.entity.Loss;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * author:huyanqing
 * Date:2018/7/24
 */
@Service
public interface IGainService {
    /**
     * 新增结算
     * @param gain
     */
    void add(Gain gain);

    /**
     * 查询结算
     * @return
     */
    List<Gain> queryGains(Map<String,String> parmMap);
}
