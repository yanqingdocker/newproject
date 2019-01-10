package cn.com.caogen.service;

import cn.com.caogen.entity.Borrow;
import cn.com.caogen.entity.CashPool;
import cn.com.caogen.entity.Operation;
import cn.com.caogen.mapper.BorrowMapper;
import cn.com.caogen.mapper.CashPoolMapper;
import cn.com.caogen.util.ConstantUtil;
import cn.com.caogen.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author:huyanqing
 * Date:2018/6/7
 */
@Service
public class BorrowServiceImpl implements IBorrowService {
    @Autowired
    private BorrowMapper borrowMapper;
    @Autowired
    private CashPoolMapper cashPoolMapper;
    @Autowired
    private OperaServiceImpl operaService;
    @Autowired
    private DataSourceTransactionManager transactionManager;
    @Override
    @Transactional
    public String add(Borrow borrow,String ip) {
        DefaultTransactionDefinition def=new DefaultTransactionDefinition();
        def.setName("add");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status=transactionManager.getTransaction(def);
        try {

            Map<String,String> parmMap=new HashMap<String, String>();
            parmMap.put("servicebranch",borrow.getServicebranch());
            parmMap.put("counttype",borrow.getMoneytype());
            if(cashPoolMapper.queryByType(parmMap)!=null){
                CashPool cashPool=cashPoolMapper.queryByType(parmMap).get(0);
             /*   if(cashPool.getBlance()<borrow.getNum()){
                    return ConstantUtil.SYSTEMCOUNT_LESS;
                }*/
                cashPool.setBlance(cashPool.getBlance()-borrow.getNum());
                cashPoolMapper.update(cashPool);

            }
            borrowMapper.add(borrow);
            operaService.add(getOperaTion(borrow,ip));
        }catch (Exception e){
            //有一个不成功能则回滚事务
            transactionManager.rollback(status);
        }
        return null;
    }

    @Override
    public List<Borrow> queryAll(Map<String,Object> parmMap) {
        return borrowMapper.queryAll(parmMap);
    }

    @Override
    @Transactional
    public void update(int id,String ip) {

        DefaultTransactionDefinition def=new DefaultTransactionDefinition();
        def.setName("update");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status=transactionManager.getTransaction(def);
        try {
            borrowMapper.update(id);
            Map<String,Object> parmMap=new HashMap<String, Object>();
            parmMap.put("id",id);

            Borrow borrow=borrowMapper.queryAll(parmMap).get(0);
            Map<String,String> parmMap1=new HashMap<String, String>();
            parmMap1.put("servicebranch",borrow.getServicebranch());
            parmMap1.put("counttype",borrow.getMoneytype());
            List<CashPool> cashPools=cashPoolMapper.queryByType(parmMap1);
            if(!cashPools.isEmpty()){
                CashPool cashPool=cashPools.get(0);
                cashPool.setBlance(cashPool.getBlance()+borrow.getNum());
                cashPoolMapper.update(cashPool);
            }
            Operation temp=getOperaTion(borrow,ip);
            temp.setOi(1);
            temp.setOperaTime(DateUtil.getTime());
            operaService.add(temp);
        }catch (Exception e){
            //有一个不成功能则回滚事务
            transactionManager.rollback(status);
        }
    }

    public Operation getOperaTion(Borrow borrow,String ip){
        Operation operation=new Operation();
        operation.setSnumber(borrow.getSnumber());
        operation.setServicebranch(borrow.getServicebranch());
        operation.setOperaTime(borrow.getCreatetime());
        operation.setOperaIp(ip);
        operation.setOperaUser("操作员-"+borrow.getOperauser());
        operation.setCountType(borrow.getMoneytype());
        if(borrow.getStatus()==1){
            operation.setOperaType(ConstantUtil.MONEY_RETURN);
            operation.setNum(borrow.getNum());
        }else if(borrow.getStatus()==0){
            operation.setOperaType(ConstantUtil.MONEY_BORROW);
            operation.setNum(-borrow.getNum());
        }
        if(borrow.getMoneytype().equals(ConstantUtil.MONEY_TYPES[0])){
            operation.setCountid(ConstantUtil.CNY_LIB);
        }else if(borrow.getMoneytype().equals(ConstantUtil.MONEY_TYPES[1])){
            operation.setCountid(ConstantUtil.USD_LIB);
        }
        operation.setSettle(1);
        return operation;
    }
}
