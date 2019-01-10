package cn.com.caogen.service;

import cn.com.caogen.entity.CashPool;
import cn.com.caogen.entity.Profits;
import cn.com.caogen.mapper.CashPoolMapper;
import cn.com.caogen.mapper.ProfitsMapper;
import cn.com.caogen.util.ConstantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

/**
 * author:huyanqing
 * Date:2018/7/24
 */
@Service
public class ProfitsServiceImpl implements IProfitsService {
    @Autowired
    private ProfitsMapper profitsMapper;
    @Autowired
    private CashPoolMapper cashPoolMapper;
    @Autowired
    private DataSourceTransactionManager transactionManager;
    @Override
    @Transactional
    public int add(Profits profits) {
        List<CashPool> cashPools=cashPoolMapper.queryAll();
        CashPool temp=null;
        for(CashPool cashPool:cashPools){
            if(cashPool.getServicebranch().equals(ConstantUtil.SERVICE_BRANCH)&&cashPool.getCounttype().equals(profits.getMoneytype())){
                temp=cashPool;
                break;
            }
        }
        if(temp!=null&&temp.getBlance()>profits.getNum()){
            temp.setBlance(temp.getBlance()-profits.getNum());
        }else{
            return 0;
        }

        DefaultTransactionDefinition def=new DefaultTransactionDefinition();
        def.setName("addprofits");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status=transactionManager.getTransaction(def);
        try{
            //更新资金库
            cashPoolMapper.update(temp);
            profitsMapper.add(profits);
        }catch (Exception e){
            //有一个不成功能则回滚事务
            transactionManager.rollback(status);
            return 0;
        }
        return 1;
    }

    @Override
    public List<Profits> queryAll() {
        return profitsMapper.queryAll();
    }
}
