package cn.com.caogen.mapper;

import cn.com.caogen.entity.Muser;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * author:huyanqing
 * Date:2018/5/3
 */
@Component
public interface MuserMapper {
    void delete(int[] arr);
    List<Muser> queryMusers();
    void add(Muser muser);
    void updateMuser(Map<String,Object> parmMap);
}
