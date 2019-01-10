package cn.com.caogen.util;


import cn.com.caogen.entity.User;

import java.util.HashMap;

public class LoginUser {
    private static HashMap<String, User> loginUserMap = new HashMap<String, User>();
    private static LoginUser loginUserVo;
    public static LoginUser getVo(){
        if(loginUserVo == null){
            loginUserVo = new LoginUser();
        }
        return loginUserVo;
    }
    public static HashMap<String, User> getLoginUserMap() {
        return loginUserMap;
    }
}
