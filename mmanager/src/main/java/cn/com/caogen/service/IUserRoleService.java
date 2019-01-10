package cn.com.caogen.service;

import cn.com.caogen.entity.UserRole;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author:huyanqing
 * Date:2018/5/17
 */
@Service
public interface IUserRoleService {
    void add(UserRole userRole);
    List<UserRole> queryByUserId(int userid);
}
