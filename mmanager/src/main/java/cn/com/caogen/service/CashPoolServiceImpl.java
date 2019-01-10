package cn.com.caogen.service;

import cn.com.caogen.entity.CashPool;
import cn.com.caogen.entity.Operation;
import cn.com.caogen.mapper.CashPoolMapper;
import cn.com.caogen.mapper.OperaMapper;
import cn.com.caogen.util.*;
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
import java.util.stream.Collectors;

/**
 * author:huyanqing
 * Date:2018/5/17
 */
@Service
public class CashPoolServiceImpl implements ICashPoolService {
    @Autowired
    private CashPoolMapper cashPoolMapper;
    @Autowired
    private DataSourceTransactionManager transactionManager;
    @Autowired
    private OperaMapper operaMapper;

    @Override
    public List<CashPool> queryAll() {
        return cashPoolMapper.queryAll();
    }

    @Override
    @Transactional
    public void exchange(Map<String, Object> parmMap) {

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("exchange");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            //更新转出现金库数据
            CashPool srccount = (CashPool) parmMap.get("srccount");
            srccount.setBlance(srccount.getBlance() - (Double) parmMap.get("srcnum"));
            srccount.setLasttime(DateUtil.getTime());
            cashPoolMapper.update(srccount);

            //更新转入现金库数据
            CashPool destcount = (CashPool) parmMap.get("destcount");
            destcount.setBlance(destcount.getBlance() + (Double) parmMap.get("destnum"));
            destcount.setLasttime(DateUtil.getTime());
            cashPoolMapper.update(destcount);

            //更新总部现金库
            if (!srccount.getServicebranch().equals(ConstantUtil.SERVICE_BRANCH)) {
                List<CashPool> cashPools = cashPoolMapper.queryAll();
                cashPools = cashPools.stream().filter((e) -> e.getServicebranch().equals(ConstantUtil.SERVICE_BRANCH)).collect(Collectors.toList());
                for (CashPool cashPool : cashPools) {
                    if (cashPool.getCounttype().equals(srccount.getCounttype())) {
                        cashPool.setBlance(cashPool.getBlance() - (Double) parmMap.get("srcnum"));
                        cashPoolMapper.update(cashPool);
                    } else if (cashPool.getCounttype().equals(destcount.getCounttype())) {
                        cashPool.setBlance(cashPool.getBlance() + (Double) parmMap.get("destnum"));
                        cashPoolMapper.update(cashPool);
                    }
                }
            }

            //增加兑换转出记录
            Operation srcoperation = new Operation();
            srcoperation.setSnumber(parmMap.get("snum").toString());
            srcoperation.setOperaIp((String) parmMap.get("ip"));
            srcoperation.setOi(ConstantUtil.MONEY_OUT);
            srcoperation.setOperaUser((String) parmMap.get("operauser"));
            srcoperation.setOperaType(ConstantUtil.MONEY_EXCHANGE);
            srcoperation.setNum(-(Double) parmMap.get("srcnum"));
            srcoperation.setCountType(srccount.getCounttype());
            srcoperation.setCountid(srccount.getCountid());
            srcoperation.setOperaTime(DateUtil.getTime());
            srcoperation.setServicebranch((String) parmMap.get("servicebranch"));
            srcoperation.setPhone((String) parmMap.get("phone"));
            srcoperation.setUsername((String) parmMap.get("username"));
            srcoperation.setCardName((String) parmMap.get("cardName"));
            srcoperation.setCardUname((String) parmMap.get("carduname"));
            srcoperation.setCardNum((String) parmMap.get("cardNum"));
            srcoperation.setRate((String) parmMap.get("rate"));
            if (StringUtil.checkStrs((String) parmMap.get("remark"))) {
                srcoperation.setRemark((String) parmMap.get("remark"));
            } else {
                srcoperation.setRemark(null);
            }
            operaMapper.add(srcoperation);
            //增加兑换转入的记录
            Operation destoperation = new Operation();
            destoperation.setSnumber(parmMap.get("snum").toString());
            destoperation.setOperaIp((String) parmMap.get("ip"));
            destoperation.setOi(ConstantUtil.MONEY_IN);
            destoperation.setOperaUser((String) parmMap.get("operauser"));
            destoperation.setOperaType(ConstantUtil.MONEY_EXCHANGE);
            destoperation.setNum((Double) parmMap.get("destnum"));
            destoperation.setCountType(destcount.getCounttype());
            destoperation.setCountid(destcount.getCountid());
            destoperation.setOperaTime(DateUtil.getTime());
            destoperation.setServicebranch((String) parmMap.get("servicebranch"));
            destoperation.setUsername((String) parmMap.get("username"));
            destoperation.setPhone((String) parmMap.get("phone"));
            destoperation.setCardName((String) parmMap.get("cardName"));
            destoperation.setCardUname((String) parmMap.get("carduname"));
            destoperation.setCardNum((String) parmMap.get("cardNum"));
            destoperation.setRate((String) parmMap.get("rate"));
            if (StringUtil.checkStrs((String) parmMap.get("remark"))) {
                destoperation.setRemark((String) parmMap.get("remark"));
            } else {
                destoperation.setRemark(null);
            }
            operaMapper.add(destoperation);


        } catch (Exception e) {
            //有一个不成功能则回滚事务
            transactionManager.rollback(status);

        }

    }


    @Transactional
    public void exchange1(Map<String, Object> parmMap) {

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("exchange");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            String srccount = (String) parmMap.get("srccount");
            String destcount = (String) parmMap.get("destcount");
            String serverbranch = (String) parmMap.get("servicebranch");
            int receive = (int) parmMap.get("receive");
            int pay = (int) parmMap.get("pay");
            //更新总部现金库
            List<CashPool> initcashPools = cashPoolMapper.queryAll();
            CashPool inCashPool = null;
            CashPool outCashPool = null;
            List<CashPool> cashPools = initcashPools.stream().filter((e) -> e.getServicebranch().equals(ConstantUtil.SERVICE_BRANCH)).collect(Collectors.toList());
            for (CashPool cashPool : cashPools) {
                if (cashPool.getCounttype().equals(srccount)) {
                    inCashPool = cashPool;
                    if (receive==1){
                        cashPool.setBlance(cashPool.getBlance() + (Double) parmMap.get("srcnum"));
                        cashPoolMapper.update(cashPool);
                    }
                } else if (cashPool.getCounttype().equals(destcount)) {
                    outCashPool = cashPool;
                    if (pay==1){
                        cashPool.setBlance(cashPool.getBlance() - (Double) parmMap.get("destnum"));
                        cashPoolMapper.update(cashPool);
                    }

                }
            }
            List<CashPool> branchcashPools = initcashPools.stream().filter((e) -> e.getServicebranch().equals(serverbranch) && e.getCounttype().equals("CNY")).collect(Collectors.toList());
            CashPool tempCashPool = branchcashPools.get(0);
            //如果是非总部的单子按照提成比例进行收入
            if (!ConstantUtil.SERVICE_BRANCH.equals(serverbranch)) {
                if ("USD".equals(srccount)) {
                    //买美元 srcnum*2%
                    Double num = (Double) parmMap.get("srcnum") * 0.01;
                    tempCashPool.setBlance(tempCashPool.getBlance() + num);
                    tempCashPool.setLasttime(DateUtil.getTime());
                    cashPoolMapper.update(tempCashPool);
                } else if ("CNY".equals(srccount)) {
                    //卖美元  destnum*1%
                    Double num = (Double) parmMap.get("destnum") * 0.02;
                    tempCashPool.setBlance(tempCashPool.getBlance() + num);
                    tempCashPool.setLasttime(DateUtil.getTime());
                    cashPoolMapper.update(tempCashPool);

                }
            }

            //增加兑换转出记录
            Operation srcoperation = new Operation();
            srcoperation.setSnumber(parmMap.get("snum").toString());
            srcoperation.setOperaIp((String) parmMap.get("ip"));
            srcoperation.setOi(ConstantUtil.MONEY_OUT);
            srcoperation.setOperaUser((String) parmMap.get("operauser"));
            srcoperation.setOperaType(ConstantUtil.MONEY_EXCHANGE);
            srcoperation.setNum(-(Double) parmMap.get("destnum"));
            srcoperation.setCountType(destcount);
            srcoperation.setCountid(outCashPool.getCountid());
            srcoperation.setOperaTime(DateUtil.getTime());
            srcoperation.setServicebranch((String) parmMap.get("servicebranch"));
            srcoperation.setPhone((String) parmMap.get("phone"));
            srcoperation.setUsername((String) parmMap.get("username"));
            srcoperation.setCardName((String) parmMap.get("cardName"));
            srcoperation.setCardUname((String) parmMap.get("carduname"));
            srcoperation.setCardNum((String) parmMap.get("cardNum"));
            srcoperation.setRate((String) parmMap.get("rate"));
            srcoperation.setSettle(pay);
            if (StringUtil.checkStrs((String) parmMap.get("remark"))) {
                srcoperation.setRemark((String) parmMap.get("remark"));
            } else {
                srcoperation.setRemark(null);
            }
            operaMapper.add(srcoperation);
            //增加兑换转入的记录
            Operation destoperation = new Operation();
            destoperation.setSnumber(parmMap.get("snum").toString());
            destoperation.setOperaIp((String) parmMap.get("ip"));
            destoperation.setOi(ConstantUtil.MONEY_IN);
            destoperation.setOperaUser((String) parmMap.get("operauser"));
            destoperation.setOperaType(ConstantUtil.MONEY_EXCHANGE);
            destoperation.setNum((Double) parmMap.get("srcnum"));
            destoperation.setCountType(srccount);
            destoperation.setCountid(inCashPool.getCountid());
            destoperation.setOperaTime(DateUtil.getTime());
            destoperation.setServicebranch((String) parmMap.get("servicebranch"));
            destoperation.setUsername((String) parmMap.get("username"));
            destoperation.setPhone((String) parmMap.get("phone"));
            destoperation.setCardName((String) parmMap.get("cardName"));
            destoperation.setCardUname((String) parmMap.get("carduname"));
            destoperation.setCardNum((String) parmMap.get("cardNum"));
            destoperation.setRate((String) parmMap.get("rate"));
            destoperation.setSettle(receive);
            if (StringUtil.checkStrs((String) parmMap.get("remark"))) {
                destoperation.setRemark((String) parmMap.get("remark"));
            } else {
                destoperation.setRemark(null);
            }
            operaMapper.add(destoperation);
        } catch (Exception e) {
            //有一个不成功能则回滚事务
            transactionManager.rollback(status);

        }

    }


    @Override
    public List<CashPool> queryByType(String type, String servicebranch) {
        Map<String, String> parmMap = new HashMap<String, String>();
        if (type != null) {
            parmMap.put("counttype", type);
        }

        parmMap.put("servicebranch", servicebranch);
        return cashPoolMapper.queryByType(parmMap);
    }

    @Override
    public void update(CashPool cashPool) {
        cashPoolMapper.update(cashPool);
    }

    @Override
    public void add(CashPool cashPool) {

    }

}
