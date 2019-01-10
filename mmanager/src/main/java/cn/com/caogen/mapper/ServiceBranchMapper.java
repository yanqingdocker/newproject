package cn.com.caogen.mapper;

import cn.com.caogen.entity.ServiceBranch;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * author:huyanqing
 * Date:2018/6/4
 */
@Repository
public interface ServiceBranchMapper {
    void add(ServiceBranch serviceBranch);
    void delete(int id);
    void update(ServiceBranch serviceBranch);
    List<ServiceBranch> queryAll();
    ServiceBranch queryById(int id);
}
