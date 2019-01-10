package cn.com.caogen.mapper;

import cn.com.caogen.entity.Loss;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * author:huyanqing
 * Date:2018/7/24
 */
@Repository
public interface LossMapper {
    /**
     * 新增损耗
     * @param loss
     */
    void add(Loss loss);

    /**
     * 查询损耗
     * @return
     */
    List<Loss> queryAll();
 }
