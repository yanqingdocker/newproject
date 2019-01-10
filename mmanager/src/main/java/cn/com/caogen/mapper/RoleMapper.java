package cn.com.caogen.mapper;

import cn.com.caogen.entity.Role;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * author:huyanqing
 * Date:2018/5/9
 */
@Repository
public interface RoleMapper {
    public void add(Role role);
    public void delete(int id);
    public List<Role> queryAll();
    public void update(Map<String,Object> parmMap);
    public Role queryById(int id);

}
