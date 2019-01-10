package cn.com.caogen.service;

import cn.com.caogen.entity.Warrantor;

import java.util.List;

/**
 * author:huyanqing
 * Date:2018/6/7
 */
public interface IWarrantorService {
    void add(Warrantor warrantor);
    List<Warrantor> queryAll();

    void update(Warrantor warrantor);

    Warrantor queryById(int id);

    void  deleteById(int id);
}
