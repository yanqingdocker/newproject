package cn.com.caogen.service;

import cn.com.caogen.entity.CashPool;
import cn.com.caogen.entity.Loss;
import cn.com.caogen.mapper.CashPoolMapper;
import cn.com.caogen.mapper.LossMapper;
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
public class LossServiceImpl implements ILossService{
    @Autowired
    private LossMapper lossMapper;
    @Autowired
    private CashPoolMapper cashPoolMapper;
    @Autowired
    private DataSourceTransactionManager transactionManager;


    public static final long baseCardId = 688485602360L;
    @Override
    @Transactional
    public int add(Loss loss) {
        List<CashPool> cashPools=cashPoolMapper.queryAll();
        CashPool temp=null;
        for(CashPool cashPool:cashPools){
            if(cashPool.getServicebranch().equals(ConstantUtil.SERVICE_BRANCH)&&cashPool.getCounttype().equals(loss.getMoneytype())){
                temp=cashPool;
                break;
            }
        }
        if(temp!=null&&temp.getBlance()>loss.getNum()){
            temp.setBlance(temp.getBlance()+loss.getNum());
        }else{
            return 0;
        }
        DefaultTransactionDefinition def=new DefaultTransactionDefinition();
        def.setName("addloss");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status=transactionManager.getTransaction(def);
        try{
            //更新资金库
            cashPoolMapper.update(temp);
            lossMapper.add(loss);
        }catch (Exception e){
            //有一个不成功能则回滚事务
            transactionManager.rollback(status);
            return 0;
        }

        return 1;
    }

    @Override
    public List<Loss> queryAll() {
        return lossMapper.queryAll();
    }
}
