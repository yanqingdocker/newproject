package cn.com.caogen.service;

import cn.com.caogen.entity.Profits;

import java.util.List;

/**
 * author:huyanqing
 * Date:2018/7/24
 */
public interface IProfitsService {
    /**
     * 增加分配记录
     * @param profits
     */
    int add(Profits profits);

    /**
     * 查询分配记录
     * @return
     */
    List<Profits> queryAll();
}
