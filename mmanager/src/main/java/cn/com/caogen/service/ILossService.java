package cn.com.caogen.service;

import cn.com.caogen.entity.Loss;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author:huyanqing
 * Date:2018/7/24
 */
@Service
public interface ILossService {
    /**
     * 新增损耗
     * @param loss
     */
    int add(Loss loss);

    /**
     * 查询损耗
     * @return
     */
    List<Loss> queryAll();
}
