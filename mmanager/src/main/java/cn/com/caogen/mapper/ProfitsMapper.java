package cn.com.caogen.mapper;

import cn.com.caogen.entity.Profits;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * author:huyanqing
 * Date:2018/7/24
 */
@Repository
public interface ProfitsMapper {
    /**
     * 添加利润分配
     * @param profits
     */
    void add(Profits profits);

    /**
     * 查询分配记录
     * @return
     */
    List<Profits> queryAll();
}
