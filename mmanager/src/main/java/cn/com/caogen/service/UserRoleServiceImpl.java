package cn.com.caogen.service;

import cn.com.caogen.entity.UserRole;
import cn.com.caogen.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * author:huyanqing
 * Date:2018/5/17
 */
@Service
public class UserRoleServiceImpl implements IUserRoleService {
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Override
    public void add(UserRole userRole) {
            userRoleMapper.add(userRole);
    }

    @Override
    public List<UserRole> queryByUserId(int userid) {
        return userRoleMapper.queryByUserId(userid);
    }

    public void batchAdd(int userid,String ids){
        List<UserRole> roles=new ArrayList<UserRole>();
        String[] strs = ids.split("，");
        strs = strs[0].split(",");
        int[] arr = new int[strs.length];
        for (int i = 0; i < strs.length; i++) {
            UserRole userRole=new UserRole();
            userRole.setUserid(userid);
            userRole.setRoleid(Integer.parseInt(strs[i]));
            roles.add(userRole);
        }
        userRoleMapper.deleteByUserid(userid);
        userRoleMapper.batchAdd(roles);
    }
}
