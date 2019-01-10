package cn.com.caogen.service;

import cn.com.caogen.entity.RoleAuth;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author:huyanqing
 * Date:2018/5/17
 */
@Service
public interface IRoleAuthService {
    void add(RoleAuth roleAuth);
    List<RoleAuth> queryByRoleId(int roleid);
}
