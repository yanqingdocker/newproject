package cn.com.caogen.mapper;

import cn.com.caogen.entity.Authoirty;
import cn.com.caogen.entity.RoleAuth;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * author:huyanqing
 * Date:2018/5/17
 */
@Repository
public interface RoleAuthMapper {
    void add(RoleAuth roleAuth);
    List<RoleAuth> queryByRoleId(int roleid);

    void batchAdd(List<RoleAuth> roleAuths);
    void deleteByRoleid(int roleid);
    void deleteByAuthid(int authid);
}
