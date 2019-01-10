package cn.com.caogen.service;

import cn.com.caogen.entity.Count;

import java.util.List;
import java.util.Map;

/**
 * author:huyanqing
 * Date:2018/4/20
 */
public interface ICountService {
    public String createCount(String username,String countType, String payPwd,String userid);

    public String updateCount(String id, double blance, String state,String payPwd);

    public String logoutCount(String id);

    public String queryAll();

    public String queryByUserId(String userid);

    public Count queryById(String id);

    List<Map<String,Object>> queryblancebyType();


}
