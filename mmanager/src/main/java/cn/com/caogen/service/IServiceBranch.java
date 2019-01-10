package cn.com.caogen.service;

import cn.com.caogen.entity.ServiceBranch;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author:huyanqing
 * Date:2018/6/4
 */
@Service
public interface IServiceBranch {
    void add(ServiceBranch serviceBranch);
    void delete(int id);
    void update(ServiceBranch serviceBranch);
    List<ServiceBranch> queryAll();
    ServiceBranch queryById(int id);
}
