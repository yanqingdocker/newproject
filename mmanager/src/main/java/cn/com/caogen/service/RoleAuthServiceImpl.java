package cn.com.caogen.service;

import cn.com.caogen.entity.RoleAuth;
import cn.com.caogen.entity.UserRole;
import cn.com.caogen.mapper.RoleAuthMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * author:huyanqing
 * Date:2018/5/17
 */
@Service
public class RoleAuthServiceImpl implements IRoleAuthService {
    @Autowired
    private RoleAuthMapper roleAuthMapper;
    @Override
    public void add(RoleAuth roleAuth) {

        roleAuthMapper.add(roleAuth);
    }

    @Override
    public List<RoleAuth> queryByRoleId(int roleid) {
        return roleAuthMapper.queryByRoleId(roleid);
    }

    public void batchAdd(int roleid,String authids){
        List<RoleAuth> roleAuths=new ArrayList<RoleAuth>();
        String[] strs = authids.split("，");
        strs = strs[0].split(",");
        int[] arr = new int[strs.length];
        for (int i = 0; i < strs.length; i++) {
            RoleAuth roleAuth=new RoleAuth();
            roleAuth.setRoleid(roleid);
            roleAuth.setAuthid(Integer.parseInt(strs[i]));
            roleAuths.add(roleAuth);
        }
        roleAuthMapper.deleteByRoleid(roleid);
        roleAuthMapper.batchAdd(roleAuths);
    }
}
