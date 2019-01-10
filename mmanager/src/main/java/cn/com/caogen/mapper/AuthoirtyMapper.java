package cn.com.caogen.mapper;

import cn.com.caogen.entity.Authoirty;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * author:huyanqing
 * Date:2018/5/11
 */
@Repository
public interface AuthoirtyMapper {
    public List<Authoirty> queryAll();
    public void add(Authoirty authoirty);
    public void delete(int[] arr);
    public void update(Authoirty authoirty);
    public Authoirty queryById(int id);
}
