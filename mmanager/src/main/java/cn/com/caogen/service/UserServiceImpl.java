package cn.com.caogen.service;


import cn.com.caogen.entity.Muser;
import cn.com.caogen.entity.User;
import cn.com.caogen.mapper.MuserMapper;
import cn.com.caogen.mapper.UserMapper;
import cn.com.caogen.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements IUserService{
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MuserMapper muserMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Override
    public void addUser(User user){
        userMapper.add(user);
    }



    @Override
    public List<User> queryAll(Map<String,Object> map){
        return userMapper.queryAll(map);
    }
    @Override
    public void update(User user){
        userMapper.update(user);
    }

    @Override
    public User querybyId(int id) {
        return userMapper.querybyId(id);
    }

    @Override
    public List<Muser> queryMusers() {
        return muserMapper.queryMusers();

    }

    @Override
    public void addMuser(Muser muser) {
        muserMapper.add(muser);

    }

    @Override
    public void deleteUser(String ids){
        String[] strs = ids.split("，");
        strs = strs[0].split(",");
        int[] arr = new int[strs.length];
        for (int i = 0; i < strs.length; i++) {
            arr[i] = Integer.parseInt(strs[i]);
        }
        muserMapper.delete(arr);
        for(int i=0;i<arr.length;i++){
            userRoleMapper.deleteByUserid(arr[i]);
        }
    }

    @Override
    public void updateMuser(String id,String password) {
        Map<String,Object> parmMap=new HashMap<String,Object>();
        parmMap.put("id",Integer.parseInt(id));
        parmMap.put("password",password);
        muserMapper.updateMuser(parmMap);
    }
}
