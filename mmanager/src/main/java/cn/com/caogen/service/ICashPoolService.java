package cn.com.caogen.service;

import cn.com.caogen.entity.CashPool;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * author:huyanqing
 * Date:2018/5/17
 */
@Service
public interface ICashPoolService {
    List<CashPool> queryAll();
    void exchange(Map<String,Object> parmMap);
    List<CashPool> queryByType(String type,String servicebranch);
    void update(CashPool cashPool);
    void add(CashPool cashPool);
}
