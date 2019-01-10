package cn.com.caogen.mapper;

import cn.com.caogen.entity.RoleAuth;
import cn.com.caogen.entity.UserRole;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * author:huyanqing
 * Date:2018/5/17
 */
@Repository
public interface UserRoleMapper {
    void add(UserRole userRole);
    List<UserRole> queryByUserId(int userid);
    void batchAdd(List<UserRole> roles);

    void deleteByUserid(int userid);
    void deleteByRoleid(int roleid);
}
