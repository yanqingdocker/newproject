package cn.com.caogen.service;

import cn.com.caogen.entity.Role;
import cn.com.caogen.mapper.RoleAuthMapper;
import cn.com.caogen.mapper.RoleMapper;
import cn.com.caogen.mapper.UserRoleMapper;
import cn.com.caogen.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * author:huyanqing
 * Date:2018/5/10
 */
@Service
public class RoleServiceImpl implements IRoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleAuthMapper roleAuthMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Override
    public void add(String rolename) {
        Role role=new Role();
        role.setRolename(rolename);
        role.setCreatetime(DateUtil.getDate());
        roleMapper.add(role);

    }

    @Override
    public void delete(String id) {
        userRoleMapper.deleteByRoleid(Integer.parseInt(id));
        roleAuthMapper.deleteByRoleid(Integer.parseInt(id));
        roleMapper.delete(Integer.parseInt(id));


    }

    @Override
    public List<Role> queryAll() {
        return roleMapper.queryAll();
    }

    @Override
    public void update(Map<String, Object> parmMap) {
        roleMapper.update(parmMap);
    }

    @Override
    public Role queryById(int id) {
        return roleMapper.queryById(id);
    }
}
