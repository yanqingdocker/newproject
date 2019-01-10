package cn.com.caogen.service;

import cn.com.caogen.entity.CashPool;
import cn.com.caogen.entity.Operation;
import cn.com.caogen.mapper.CashPoolMapper;
import cn.com.caogen.mapper.OperaMapper;
import cn.com.caogen.mapper.TaskMapper;
import cn.com.caogen.util.ConstantUtil;
import cn.com.caogen.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * author:huyanqing
 * Date:2018/5/10
 */
@Service
public class OperaServiceImpl implements IOperaService {
    @Autowired
    private OperaMapper operaMapper;
    @Autowired
    private CashPoolMapper cashPoolMapper;
    @Autowired
    private TaskMapper taskMapper;
    @Override
    public void add(Operation operation) {
            operaMapper.add(operation);
    }

    @Override
    public List<Operation> queryAll( Map<String,Object> parmMap,String servicebranch) {

        if(ConstantUtil.SERVICE_BRANCH.equals(servicebranch)){
            return operaMapper.queryCondition(parmMap);
        }
        parmMap.put("servicebranch",servicebranch);
        return operaMapper.queryCondition(parmMap);
    }
    public List<Operation> queryAll() {
        return operaMapper.queryAll();
    }

    @Override
    public List<Operation> queryAll(Map<String, Object> parmMap) {

        return operaMapper.queryCondition(parmMap);
    }

    @Override
    public List<Operation> queryByDate(Map<String, Object> parmMap) {
        if(ConstantUtil.SERVICE_BRANCH.equals(parmMap.get("servicebranch"))){
            parmMap.remove("servicebranch");
        }
        return operaMapper.queryByDate(parmMap);
    }

    @Override
    public List<Map<String, Object>> queryoperatype(int date,String servicebranch) {
        Map<String,Object> parmMap=new HashMap<String,Object>();
        parmMap.put("date",String.valueOf(date));
        if(ConstantUtil.SERVICE_BRANCH.equals(servicebranch)){
            return operaMapper.queryoperatype(parmMap);
        }
        parmMap.put("servicebranch",servicebranch);
        return operaMapper.queryoperatype(parmMap);
    }

    @Override
    public List<Map<String, Object>> queryoperacount(int date,String servicebranch) {
        Map<String,Object> parmMap=new HashMap<String,Object>();
        parmMap.put("date",String.valueOf(date));

        if(ConstantUtil.SERVICE_BRANCH.equals(servicebranch)){
            return operaMapper.queryoperacount(parmMap);
        }
        parmMap.put("servicebranch",servicebranch);
        return operaMapper.queryoperacount(parmMap);
    }


    public String queryScope1(Map<String, Object> parmMap) {
        List<Operation> operationList=operaMapper.queryScope(parmMap);
        int count=operaMapper.queryScopeCount(parmMap);
        List<Operation> inUSD=null;
        List<Operation> outUSD=null;
        String type=parmMap.get("type").toString();
        if(ConstantUtil.MONEY_USD.equals(type)){
            if(StringUtil.checkStrs(parmMap.get("servicebranch").toString())){
                inUSD=operationList.stream().filter((e)->ConstantUtil.USD_LIB.equals(e.getCountid())&&e.getNum()>0&&e.getServicebranch().equals(parmMap.get("servicebranch"))).collect(Collectors.toList());
                outUSD=operationList.stream().filter((e)->ConstantUtil.USD_LIB.equals(e.getCountid())&&e.getNum()<0&&e.getServicebranch().equals(parmMap.get("servicebranch"))).collect(Collectors.toList());
            }else{
                inUSD=operationList.stream().filter((e)->ConstantUtil.USD_LIB.equals(e.getCountid())&&e.getNum()>0).collect(Collectors.toList());
                outUSD=operationList.stream().filter((e)->ConstantUtil.USD_LIB.equals(e.getCountid())&&e.getNum()<0).collect(Collectors.toList());
            }

        }else if(ConstantUtil.MONEY_CNY.equals(type)){
            if(StringUtil.checkStrs(parmMap.get("servicebranch").toString())){
                inUSD=operationList.stream().filter((e)->ConstantUtil.CNY_LIB.equals(e.getCountid())&&e.getNum()>0&&e.getServicebranch().equals(parmMap.get("servicebranch"))).collect(Collectors.toList());
                outUSD=operationList.stream().filter((e)->ConstantUtil.CNY_LIB.equals(e.getCountid())&&e.getNum()<0&&e.getServicebranch().equals(parmMap.get("servicebranch"))).collect(Collectors.toList());
            }else{
                inUSD=operationList.stream().filter((e)->ConstantUtil.CNY_LIB.equals(e.getCountid())&&e.getNum()>0).collect(Collectors.toList());
                outUSD=operationList.stream().filter((e)->ConstantUtil.CNY_LIB.equals(e.getCountid())&&e.getNum()<0).collect(Collectors.toList());
            }

        }
        double inNum=0;
        for(Operation operation:inUSD){
           inNum+=operation.getNum();
        }
        double outNum=0;
        for(Operation operation:outUSD){
            outNum+=operation.getNum();
        }
        StringBuffer sb=new StringBuffer();
        sb.append("{'inNum':'").append(inNum).append("','outNum':'").append(outNum).append("'}");

        StringBuffer rs=new StringBuffer();
        rs.append("{'region':").append(JSONObject.fromObject(sb.toString())).append(",'regionlist':");
        if(ConstantUtil.MONEY_CNY.equals(type)){
            if(StringUtil.checkStrs(parmMap.get("servicebranch").toString())){
                operationList=operationList.stream().filter((e)->ConstantUtil.CNY_LIB.equals(e.getCountid())&&e.getServicebranch().equals(parmMap.get("servicebranch"))).collect(Collectors.toList());
            }else{
                operationList=operationList.stream().filter((e)->ConstantUtil.CNY_LIB.equals(e.getCountid())).collect(Collectors.toList());
            }

            rs.append(JSONArray.fromObject(operationList)).append("}");
        }else if(ConstantUtil.MONEY_USD.equals(type)){
            if(StringUtil.checkStrs(parmMap.get("servicebranch").toString())){
                operationList=operationList.stream().filter((e)->ConstantUtil.USD_LIB.equals(e.getCountid())&&e.getServicebranch().equals(parmMap.get("servicebranch"))).collect(Collectors.toList());
            }else{
                operationList=operationList.stream().filter((e)->ConstantUtil.USD_LIB.equals(e.getCountid())).collect(Collectors.toList());
            }
            rs.append(JSONArray.fromObject(operationList)).append("}");
        }

        return JSONObject.fromObject(rs.toString()).toString();
    }
    @Override
    public String queryScope(Map<String, Object> parmMap) {
        List<Operation> operationList=operaMapper.queryScope(parmMap);
        int count=queryScopeCount(parmMap);
        List<Operation> inOperaList=operationList.stream().filter((e)->e.getNum()>0).collect(Collectors.toList());;
        List<Operation> outOperaList=operationList.stream().filter((e)->e.getNum()<0).collect(Collectors.toList());;;

        double inNum=0;
        for(Operation operation:inOperaList){
            inNum+=operation.getNum();
        }
        double outNum=0;
        for(Operation operation:outOperaList){
            outNum+=operation.getNum();
        }
        StringBuffer sb=new StringBuffer();
        sb.append("{'inNum':'").append(inNum).append("','outNum':'").append(outNum).append("'}");

        StringBuffer rs=new StringBuffer();
        rs.append("{'region':").append(JSONObject.fromObject(sb.toString())).append(",'regionlist':");

        rs.append(JSONArray.fromObject(operationList)).append("}");
        JSONObject jsonObject=new JSONObject();
        jsonObject.element("inNum",inNum);
        jsonObject.element("outNum",outNum);
        jsonObject.element("count",count);
        jsonObject.element("data",operationList);
        return jsonObject.toString();
    }

    public String queryProfit(Map<String, Object> parmMap) {

        List<Operation> operationList=operaMapper.queryScope(parmMap);
        int count=queryScopeCount(parmMap);
        parmMap.put("counttype","USD");
        List<Operation> newoperationList=operaMapper.queryScope(parmMap);
        double profit=0.0;
        for(Operation operation:newoperationList){
            if(operation.getNum()>0){
                    //买美金
                    profit+=operation.getNum()*0.02;
            }else if(operation.getNum()<0){
                    //卖美金
                    profit+=Math.abs(operation.getNum()*0.01);
            }
        }

        JSONObject jsonObject=new JSONObject();
        jsonObject.element("inNum",profit);
        jsonObject.element("count",count);
        jsonObject.element("data",operationList);
        return jsonObject.toString();
    }


    @Override
    public int datarecover(String snumber) {
        List<CashPool> cashPools=cashPoolMapper.queryAll();
        List<Operation> operationList=operaMapper.queryByOrderNum(snumber);
        if(operationList.isEmpty()){
            return 1;
        }
        for(Operation operation:operationList){
            switch (operation.getCountType()){
                case ConstantUtil.MONEY_USD:
                  /*  CashPool temp=null;
                    for(CashPool cashPool:cashPools){
                        if(cashPool.getCounttype().equals(ConstantUtil.MONEY_USD)&&cashPool.getServicebranch().equals(operation.getServicebranch())){
                            temp=cashPool;
                            break;
                        }
                    }
                    if(temp==null){
                        return 1;
                    }*/
                    if(operation.getNum()>0){
                        if(operation.getSettle()==1){
                            //temp.setBlance(temp.getBlance()-operation.getNum());
                            if(!operation.getServicebranch().equals(ConstantUtil.SERVICE_BRANCH)){
                                CashPool temp1=null;
                                for(CashPool cashPool:cashPools){
                                    if(cashPool.getCounttype().equals(ConstantUtil.MONEY_USD)&&cashPool.getServicebranch().equals(ConstantUtil.SERVICE_BRANCH)){
                                        temp1=cashPool;
                                        break;
                                    }
                                }
                                if(operation.getSettle()==1){
                                    temp1.setBlance(temp1.getBlance()-operation.getNum());
                                    cashPoolMapper.update(temp1);
                                }

                            }
                        }

                    }else{
                        if(operation.getSettle()==1){
                           // temp.setBlance(temp.getBlance()+(-operation.getNum()));
                            if(!operation.getServicebranch().equals(ConstantUtil.SERVICE_BRANCH)){
                                CashPool temp1=null;
                                for(CashPool cashPool:cashPools){
                                    if(cashPool.getCounttype().equals(ConstantUtil.MONEY_USD)&&cashPool.getServicebranch().equals(ConstantUtil.SERVICE_BRANCH)){
                                        temp1=cashPool;
                                        break;
                                    }
                                }
                                if(operation.getSettle()==1){
                                    temp1.setBlance(temp1.getBlance()+(-operation.getNum()));
                                    cashPoolMapper.update(temp1);
                                }

                            }
                        }

                    }
                   // cashPoolMapper.update(temp);

                    break;
                case ConstantUtil.MONEY_CNY:
                    CashPool temp1=null;
                    for(CashPool cashPool:cashPools){
                        if(cashPool.getCounttype().equals(ConstantUtil.MONEY_CNY)&&cashPool.getServicebranch().equals(operation.getServicebranch())){
                            temp1=cashPool;
                            break;
                        }
                    }
                    if(temp1==null){
                        return 1;
                    }
                    if(operation.getNum()>0){
                        for(Operation opt:operationList){
                            if(opt.getCountType().equals("USD")){
                                temp1.setBlance(temp1.getBlance()-Math.abs(opt.getNum())*0.02);//卖美金是1%
                            }
                        }

                        if(operation.getSettle()==1){
                            if(!operation.getServicebranch().equals(ConstantUtil.SERVICE_BRANCH)){
                                CashPool temp2=null;
                                for(CashPool cashPool:cashPools){
                                    if(cashPool.getCounttype().equals(ConstantUtil.MONEY_CNY)&&cashPool.getServicebranch().equals(ConstantUtil.SERVICE_BRANCH)){
                                        temp2=cashPool;
                                        break;
                                    }
                                }
                                temp2.setBlance(temp2.getBlance()-operation.getNum());
                                cashPoolMapper.update(temp2);
                            }
                        }

                    }else{
                        for(Operation opt:operationList){
                            if(opt.getCountType().equals("USD")){
                                temp1.setBlance(temp1.getBlance()+Math.abs(opt.getNum())*0.01);//买美金是2%
                            }
                        }
                        if(operation.getSettle()==1){

                            if(!operation.getServicebranch().equals(ConstantUtil.SERVICE_BRANCH)){
                                CashPool temp2=null;
                                for(CashPool cashPool:cashPools){
                                    if(cashPool.getCounttype().equals(ConstantUtil.MONEY_CNY)&&cashPool.getServicebranch().equals(ConstantUtil.SERVICE_BRANCH)){
                                        temp2=cashPool;
                                        break;
                                    }
                                }
                                temp2.setBlance(temp2.getBlance()+(-operation.getNum()));
                                cashPoolMapper.update(temp2);
                            }
                        }

                    }
                    cashPoolMapper.update(temp1);

                    break;
            }
        }
        operaMapper.delete(snumber);
        taskMapper.deleteBysnum(snumber);
        return 0;
    }

    @Override
    public int queryScopeCount(Map<String, Object> parmMap) {
       /* if(ConstantUtil.SERVICE_BRANCH.equals(servicebranch)){
            return operaMapper.queryScopCount(parmMap);
        }
        parmMap.put("servicebranch",servicebranch);*/
        return operaMapper.queryScopeCount(parmMap);
    }

    @Override
    public int queryConditionCount(Map<String, Object> parmMap,String servicebranch) {
        if(ConstantUtil.SERVICE_BRANCH.equals(servicebranch)){
            return operaMapper.queryConditionCount(parmMap);
        }
        parmMap.put("servicebranch",servicebranch);
        return operaMapper.queryConditionCount(parmMap);
    }

    @Override
    public Operation queryById(int id) {
        return operaMapper.queryById(id);
    }

    @Override
    public void update(Operation operation) {
        operaMapper.update(operation);
    }


}

