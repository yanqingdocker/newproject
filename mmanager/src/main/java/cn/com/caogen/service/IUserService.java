package cn.com.caogen.service;

import cn.com.caogen.entity.Muser;
import cn.com.caogen.entity.User;

import java.util.List;
import java.util.Map;

public interface IUserService {
    void addUser(User user);
    List<User> queryAll(Map<String,Object> map);
    void update(User user);
    User querybyId(int id);

    List<Muser> queryMusers();
    void addMuser(Muser muser);
    void deleteUser(String ids);
    void updateMuser(String id,String password);

}
