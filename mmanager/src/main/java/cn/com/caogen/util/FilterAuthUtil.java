package cn.com.caogen.util;

import cn.com.caogen.entity.Muser;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * author:huyanqing
 * Date:2018/5/28
 */
public class FilterAuthUtil {
    public static boolean checkAuth(HttpServletRequest request){
        Muser currentUser=(Muser) request.getSession().getAttribute("currentUser");
        if(currentUser==null){
            return false;
        }
        List<String> auths=currentUser.getAuths();
        String path=request.getRequestURI();
        for(String auth:auths){
            if(path.equals(auth)){
                return true;
            }
        }

        return false;
    }
}
