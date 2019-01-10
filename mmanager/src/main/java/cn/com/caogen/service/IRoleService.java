package cn.com.caogen.service;

import cn.com.caogen.entity.Role;

import java.util.List;
import java.util.Map;

/**
 * author:huyanqing
 * Date:2018/5/9
 */
public interface IRoleService {
    /**
     * 添加角色
     * @param rolename
     */
    public void add(String rolename);

    /**
     * 删除角色
     * @param id
     */
    public void delete(String id);

    /**
     * 查询角色
     * @return
     */
    public List<Role> queryAll();

    /**
     * 修改角色
     * @param parmMap
     */
    public void update(Map<String,Object> parmMap);

    /**
     * 根据id查询角色
     * @param id
     * @return
     */
    public Role queryById(int id);
}
