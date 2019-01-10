package cn.com.caogen.service;

import cn.com.caogen.entity.Authoirty;

import java.util.List;

/**
 * author:huyanqing
 * Date:2018/5/11
 */
public interface IAuthoirtyService {
    public List<Authoirty> queryAll();
    public void add(Authoirty authoirty);
    public void delete(String ids);
    public void update(Authoirty authoirty);
    public Authoirty queryById(int id);
}
