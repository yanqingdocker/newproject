package cn.com.caogen.service;

import cn.com.caogen.entity.Count;
import cn.com.caogen.entity.Operation;
import cn.com.caogen.mapper.CountMapper;
import cn.com.caogen.mapper.OperaMapper;
import cn.com.caogen.util.*;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author:huyanqing
 * Date:2018/4/20
 */
@Service
public class CountServiceImpl implements ICountService {
    public static final Logger logger = LoggerFactory.getLogger(CountServiceImpl.class);
    @Autowired
    private CountMapper countMapper;
    @Autowired
    private OperaMapper operaMapper;
    @Autowired
    private DataSourceTransactionManager transactionManager;


    public static final long baseCardId = 688485602360L;

    @Override
    public String createCount(String  username,String countType, String payPwd,String userId) {
        List<Count> list=countMapper.queryByUserId(Integer.parseInt(userId));
        for(Count count:list){
            if(countType.equals(count.getCountType())){
                return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.ALERADY_CUNT)).toString();
            }
        }
        Count count = new Count();
        List<Count> countList=countMapper.queryAll();
        if(countList.isEmpty()){
            count.setCardId(String.valueOf(baseCardId ));
        }else{
            long card=Long.parseLong(countList.get(countList.size()-1).getCardId());
            count.setCardId(String.valueOf(card+1));
        }
        count.setCountType(countType);
        count.setPayPwd(payPwd);
        count.setUserId(userId);
        count.setBlance(0);
        count.setCreateTime(DateUtil.getTime());
        count.setUsername(username);
        String checkCode=DataMonitor.getValiateCode(count,"id","checkCode","exception","state");
        count.setCheckCode(checkCode);
        try {
            countMapper.add(count);
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
        } catch (Exception e) {
            logger.error("createCount fail" + e.getMessage());
        }
        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL)).toString();
    }

    @Override
    public String updateCount(String id, double blance, String state,String payPwd) {

        Count count =countMapper.queryById(Integer.parseInt(id));
        if(count==null){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL)).toString();
        }
        if(blance>=0){
            count.setBlance(blance);
        }
        if(state!=null){
            count.setState(state);
        }
        if(payPwd!=null){
            count.setPayPwd(payPwd);
        }

        try {
            String checkCode=DataMonitor.getValiateCode(count,"id","checkCode","exception","state");
            count.setCheckCode(checkCode);
            countMapper.update(count);
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
        } catch (Exception e) {
            logger.error("updateCount fail" + e.getMessage());
        }

        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL)).toString();
    }

    @Override
    public String logoutCount(String id) {
        try {
            countMapper.delete(Integer.parseInt(id));
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
        } catch (Exception e) {
            logger.error("logoutCount fail" + e.getMessage());
        }
        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL)).toString();
    }

    @Override
    public String queryAll() {
        try {
            List<Count> countList=countMapper.queryAll();
          /*  for (Count count:countList){
                String checkCode=DataMonitor.getValiateCode(count,"id","checkCode","exception","state");
                if(!checkCode.equals(count.getCheckCode())){
                    count.setState("0");
                    updateCount(String.valueOf(count.getId()),0,"0",null);
                }
            }*/
            Map<String,Object> map=new HashMap<String,Object>();

            for(Count count:countList){
                if(map.get(count.getUserId())!=null){
                    List<Count> list=(List<Count>) map.get(count.getUserId());
                    list.add(count);
                    map.put(count.getUserId(),list);
                }else{
                    List<Count> list=new ArrayList<Count>();
                    list.add(count);
                    map.put(count.getUserId(),list);

                }

            }
            return JSONArray.fromObject(map).toString();
        } catch (Exception e) {
            logger.error("queryAllCount fail" + e.getMessage());
        }
        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL)).toString();
    }

    public List<Count> queryAllCount() {
        try {
            List<Count> countList=countMapper.queryAll();
            return countList;

        } catch (Exception e) {
            logger.error("queryAllCount fail" + e.getMessage());
        }
        return null;
    }



    @Override
    public String queryByUserId(String userid) {
        try {
            if(StringUtil.checkStrs(userid)){
                List<Count> countList=countMapper.queryByUserId(Integer.parseInt(userid));
             /*   for (Count count:countList){
                    String checkCode=DataMonitor.getValiateCode(count,"id","checkCode","exception","state");
                    if(!checkCode.equals(count.getCheckCode())){
                        count.setState("0");
                        updateCount(String.valueOf(count.getId()),0,"0",null);
                    }
                }*/
                return JSONArray.fromObject(countList).toString();
            }else{
                return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL)).toString();
            }
        } catch (Exception e) {
            logger.error("queryByUserId fail" + e.getMessage());
        }
        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL)).toString();
    }

    public List<Count> queryByUserId(int userid) {
        try {
                List<Count> countList=countMapper.queryByUserId(userid);
             /*   for (Count count:countList){
                    String checkCode=DataMonitor.getValiateCode(count,"id","checkCode","exception","state");
                    if(!checkCode.equals(count.getCheckCode())){
                        count.setState("0");
                        updateCount(String.valueOf(count.getId()),0,"0",null);
                    }
                }*/
                return countList;
        } catch (Exception e) {
            logger.error("queryByUserId fail" + e.getMessage());
        }
        return null;
    }

    @Override
    public Count queryById(String id) {
        Count count=countMapper.queryById(Integer.parseInt(id));
        String checkCode=DataMonitor.getValiateCode(count,"id","checkCode","exception","state");
       /* if(!checkCode.equals(count.getCheckCode())){
            count.setState("0");
            updateCount(String.valueOf(count.getId()),0,"0",null);
        }*/
        return count;
    }

    @Override
    public  List<Map<String,Object>> queryblancebyType() {

        return countMapper.queryblancebyType();
    }

    /**
     * 账户转账
     * @param srccount
     * @param destCount
     * @param moneynum
     */
    @Transactional
    public void countswitch(Count srccount, Count destCount,Double moneynum) {

        DefaultTransactionDefinition def=new DefaultTransactionDefinition();
        def.setName("countswitch");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status=transactionManager.getTransaction(def);
        try{
            //原账户扣除金额
            srccount.setBlance(srccount.getBlance()-moneynum);
            String checkCode=DataMonitor.getValiateCode(srccount,"id","checkCode","exception","state");
            countMapper.update(srccount);
            //目标账户增加金额
            destCount.setBlance(destCount.getBlance()+moneynum);
            checkCode=DataMonitor.getValiateCode(destCount,"id","checkCode","exception","state");
            countMapper.update(destCount);

        }catch (Exception e){
            //有一个不成功能则回滚事务
            transactionManager.rollback(status);

        }

    }

    public List<Count> queryCount(String userid){
        List<Count> countList=countMapper.queryByUserId(Integer.parseInt(userid));
        if(countList!=null){
          /*  for (Count count:countList){
                String checkCode=DataMonitor.getValiateCode(count,"id","checkCode","exception","state");
                if(!checkCode.equals(count.getCheckCode())){
                    count.setState("0");
                    updateCount(String.valueOf(count.getId()),0,"0",null);
                }
            }*/
            return countList;
        }else {
            return null;
        }

    }

    /**
     * 账户兑换
     * @param srccountid
     * @param destcountid
     * @param srcmoney
     * @param destmoney
     * @return
     */
    @Transactional
    public String exchange(String srccountid, String destcountid, Double srcmoney, Double destmoney,String paypwd) {
        //校验账户是否存在
        Count srcCount=countMapper.queryById(Integer.parseInt(srccountid));
        Count destCount=countMapper.queryById(Integer.parseInt(destcountid));
        if(srcCount==null||destCount==null){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.NOTSRCORDEST)).toString();

        }
        //校验支付密码是否正确
        if(!srcCount.getPayPwd().equals(paypwd)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.PAYPWDERROR)).toString();
        }
        //校验账户余额是否足够
        if(srcCount.getBlance()<srcmoney){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.NOTBLANCE)).toString();
        }
        DefaultTransactionDefinition def=new DefaultTransactionDefinition();
        def.setName("exchange");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status=transactionManager.getTransaction(def);
        try{
            //原账户扣除金额
            srcCount.setBlance(srcCount.getBlance()-srcmoney);
            countMapper.update(srcCount);
            //目标账户增加金额
            destCount.setBlance(destCount.getBlance()+destmoney);
            countMapper.update(destCount);

        }catch (Exception e){
            //有一个不成功能则回滚事务
            transactionManager.rollback(status);
        }
        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
    }

    /**
     * 保存操作记录
     * @param countid
     * @param counttype
     * @param num
     * @param operatype
     * @param operauser
     * @param oi
     * @param operaip
     */
    public Operation saveOperaLog(String servicebranch,String countid,String counttype,Double num,String operatype,String operauser,int oi,String operaip){
        Operation operation=new Operation();
        operation.setSnumber(SerialnumberUtil.Getnum());
        operation.setCountid(countid);
        operation.setCountType(counttype);
        operation.setNum(num);
        operation.setOperaType(operatype);
        operation.setOperaTime(DateUtil.getTime());
        operation.setOperaUser(operauser);
        operation.setOi(oi);
        operation.setOperaIp(operaip);
        operation.setServicebranch(servicebranch);
        operation.setSettle(1);
        operaMapper.add(operation);
        return operation;
    }


}
