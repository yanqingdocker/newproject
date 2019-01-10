package cn.com.caogen.service;

import cn.com.caogen.entity.CashPool;
import cn.com.caogen.entity.ServiceBranch;
import cn.com.caogen.mapper.CashPoolMapper;
import cn.com.caogen.mapper.ServiceBranchMapper;
import cn.com.caogen.util.ConstantUtil;
import cn.com.caogen.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author:huyanqing
 * Date:2018/6/4
 */
@Service
public class ServiceBranchImpl implements IServiceBranch {
    @Autowired
    private ServiceBranchMapper serviceBranchMapper;
    @Autowired
    private CashPoolMapper cashPoolMapper;
    @Override
    public void add(ServiceBranch serviceBranch) {
        serviceBranchMapper.add(serviceBranch);
        CashPool cashPool=new CashPool();
        cashPool.setLasttime(DateUtil.getTime());
        cashPool.setCountid(ConstantUtil.CNY_LIB);
        cashPool.setCounttype(ConstantUtil.MONEY_TYPES[0]);
        cashPool.setServicebranch(serviceBranch.getBranchname());
        cashPoolMapper.add(cashPool);
        cashPool.setCountid(ConstantUtil.USD_LIB);
        cashPool.setCounttype(ConstantUtil.MONEY_TYPES[1]);
        cashPoolMapper.add(cashPool);
    }

    @Override
    public void delete(int id) {
        serviceBranchMapper.delete(id);
    }

    @Override
    public void update(ServiceBranch serviceBranch) {
        serviceBranchMapper.update(serviceBranch);
    }

    @Override
    public List<ServiceBranch> queryAll() {
        return serviceBranchMapper.queryAll();
    }

    @Override
    public ServiceBranch queryById(int id) {
        return serviceBranchMapper.queryById(id);
    }
}
