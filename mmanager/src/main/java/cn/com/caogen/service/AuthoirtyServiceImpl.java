package cn.com.caogen.service;

import cn.com.caogen.entity.Authoirty;
import cn.com.caogen.mapper.AuthoirtyMapper;
import cn.com.caogen.mapper.RoleAuthMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author:huyanqing
 * Date:2018/5/11
 */
@Service
public class AuthoirtyServiceImpl implements IAuthoirtyService {
    @Autowired
    private AuthoirtyMapper authoirtyMapper;
    @Autowired
    private RoleAuthMapper roleAuthMapper;
    @Override
    public List<Authoirty> queryAll() {
        return authoirtyMapper.queryAll();
    }

    @Override
    public void add(Authoirty authoirty) {
        authoirtyMapper.add(authoirty);
    }

    @Override
    public void delete(String ids) {
        String[] strs = ids.split("，");
        strs = strs[0].split(",");
        int[] arr = new int[strs.length];
        for (int i = 0; i < strs.length; i++) {

            arr[i] = Integer.parseInt(strs[i]);
            roleAuthMapper.deleteByAuthid(arr[i]);
        }

        authoirtyMapper.delete(arr);
    }

    @Override
    public void update(Authoirty authoirty) {
        authoirtyMapper.update(authoirty);
    }

    @Override
    public Authoirty queryById(int id) {
        return authoirtyMapper.queryById(id);
    }
}
