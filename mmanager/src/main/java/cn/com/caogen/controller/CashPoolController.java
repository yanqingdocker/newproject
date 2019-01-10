package cn.com.caogen.controller;

import cn.com.caogen.entity.CashPool;
import cn.com.caogen.entity.Muser;
import cn.com.caogen.entity.Operation;
import cn.com.caogen.entity.Task;
import cn.com.caogen.mapper.RedisMapper;
import cn.com.caogen.service.CashPoolServiceImpl;
import cn.com.caogen.service.CountServiceImpl;
import cn.com.caogen.service.OperaServiceImpl;
import cn.com.caogen.service.TaskServiceImpl;
import cn.com.caogen.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * author:huyanqing
 * Date:2018/5/17
 */
@RestController
@RequestMapping("/cashpool")
public class CashPoolController {
    private static Logger logger = LoggerFactory.getLogger(CashPoolController.class);
    @Autowired
    private CashPoolServiceImpl cashPoolService;
    @Autowired
    private OperaServiceImpl operaService;
    @Autowired
    private TaskServiceImpl taskService;

    @Autowired
    private DataSourceTransactionManager transactionManager;

    @Autowired
    private RedisMapper redisMapper;

    /**
     * 查询现金库
     *
     * @return
     */
    @RequestMapping(path = "queryAll", method = RequestMethod.GET)
    public String queryAll(@RequestParam("servicebranch") String servicebranch, HttpServletRequest request) {
        if (!FilterAuthUtil.checkAuth(request)) {
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH, ConstantUtil.FAIL)).toString();
        }
        logger.info("queryAll start: servicebranch=" + servicebranch);
        Muser currentUser = (Muser) request.getSession().getAttribute("currentUser");
        logger.info("user=:" + currentUser.getUsername());
        List<CashPool> cashPoolList = cashPoolService.queryByType(null, servicebranch);
        return JSONArray.fromObject(cashPoolList).toString();
    }

    /**
     * 查询盈亏情况
     *
     * @return
     */
    @RequestMapping(path = "queryProf", method = RequestMethod.GET)
    public String queryProf(HttpServletRequest request) {

        logger.info("queryProf start:");
       /* Muser currentUser = (Muser) request.getSession().getAttribute("currentUser");
        logger.info("user=:" + currentUser.getUsername());
        String rs = redisMapper.getvalueBykey(ConstantUtil.SENVEN).getKvalue();
        JSONObject jsonObject = JSONObject.fromObject(rs);
        String buyPid = jsonObject.getJSONObject("USDCNY").getString("buyPic");
        Double buy = Double.parseDouble(buyPid);


        List<CashPool> cashPools = null;
        try {
            JSONArray jsonArray=JSONArray.fromObject(redisMapper.getvalueBykey("cash").getKvalue();
            cashPools = (List) SerializeUtil.unserialize(JedisUtil.getJedis().get("cash".getBytes()));
        } catch (NullPointerException e) {
            cashPools = (List) SerializeUtil.unserialize(JedisUtil.getJedis().get("cash".getBytes()));
        }
        List<CashPool> collection = new ArrayList<CashPool>();
        for (CashPool cashPool : cashPools) {
            if (cashPool.getServicebranch().equals(currentUser.getServicebranch())) {
                collection.add(cashPool);
            }
        }
        double num1, num2;
        CashPool cashPool = collection.get(0);
        if (cashPool.getCounttype().equals(ConstantUtil.MONEY_CNY)) {
            num1 = cashPool.getBlance() / buy;
        } else {
            num1 = cashPool.getBlance();
        }

        cashPool = collection.get(1);
        if (cashPool.getCounttype().equals(ConstantUtil.MONEY_CNY)) {
            num2 = cashPool.getBlance() / buy;
        } else {
            num2 = cashPool.getBlance();
        }
        double sum = num1 + num2;


        CashPool USDPoolk = cashPoolService.queryByType(ConstantUtil.MONEY_USD, currentUser.getServicebranch()).get(0);
        CashPool CNYPoolk = cashPoolService.queryByType(ConstantUtil.MONEY_CNY, currentUser.getServicebranch()).get(0);
        double currentNum = USDPoolk.getBlance() + CNYPoolk.getBlance() / buy;
        if (currentNum - sum > 0) {
            //赚钱
            logger.info("赚取" + (currentNum - sum));
        } else if (currentNum - sum == 0) {
            //持平
            logger.info("持平");
        } else {
            //亏钱
            logger.info("亏损" + (currentNum - sum));
        }
        //插入数据库
        logger.info("queryProf end money=" + (currentNum - sum));*/
        //return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS, String.valueOf(currentNum - sum))).toString();
        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS, String.valueOf("0.0"))).toString();
    }

    /**
     * 现金库初始化
     *
     * @param type
     * @param num
     * @param request
     * @return
     */
    @RequestMapping(path = "initCashPool", method = RequestMethod.POST)
    public String initCashPool(@RequestParam("type") String type, @RequestParam("num") double num, @RequestParam("oi") int oi, @RequestParam("branchname") String branchname, HttpServletRequest request) {
        if (!FilterAuthUtil.checkAuth(request)) {
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH, ConstantUtil.FAIL)).toString();
        }
        logger.info("initCashPool start:   type=" + type + ",num=" + num + ",oi=" + oi);
        Muser currentUser = (Muser) request.getSession().getAttribute("currentUser");
        logger.info("user=:" + currentUser.getUsername());
        if (!StringUtil.checkStrs(type, String.valueOf(num), String.valueOf(oi))) {
            logger.info(ConstantUtil.ERROR_ARGS);
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL, ConstantUtil.ERROR_ARGS)).toString();
        }

        CashPool cashPool = cashPoolService.queryByType(type, branchname).get(0);
        if (oi == ConstantUtil.MONEY_OUT) {
            if (cashPool.getBlance() < num) {
                return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL, ConstantUtil.SYSTEMCOUNT_LESS)).toString();
            }
            cashPool.setBlance(cashPool.getBlance() - num);
        }
        if (oi == ConstantUtil.MONEY_IN) {
            cashPool.setBlance(cashPool.getBlance() + num);
        }
        cashPool.setLasttime(DateUtil.getTime());
        cashPoolService.update(cashPool);
        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
    }

    /**
     * 现金兑换
     *
     * @param srccounttype
     * @param destcounttype
     * @param srcnum
     * @param destnum
     * @param request
     * @return
     */
    @RequestMapping(path = "exchange", method = RequestMethod.POST)
    public String exchange(@RequestParam("serverchname") String serverchname, @RequestParam("srccounttype") String srccounttype, @RequestParam("destcounttype") String destcounttype, @RequestParam("srcnum") Double srcnum, @RequestParam("receive") int receive, @RequestParam("pay") int pay, @RequestParam("destnum") Double destnum, @RequestParam("remark") String remark, @RequestParam("phone") String phone, @RequestParam("username") String username, @RequestParam("carduname") String carduname, @RequestParam("cardName") String cardName, @RequestParam("cardNum") String cardNum, @RequestParam("rate") String rate, HttpServletRequest request) {

        if (!FilterAuthUtil.checkAuth(request)) {
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH, ConstantUtil.FAIL)).toString();
        }
        logger.info("exchange start:receive="+receive+",pay="+pay+",serverchname=" + serverchname + ", srccounttype=" + srccounttype + ",destcounttype=" + destcounttype + ",srcnum=" + srcnum + ",destnum=" + destnum + ",remark=" + remark + ",phone=" + phone + ",username=" + username + ",carduname=" + carduname + ",cardName=" + cardName + ",cardNum=" + cardNum);
        if (!StringUtil.checkStrs(srccounttype, destcounttype, String.valueOf(srcnum), String.valueOf(destnum), phone, username)) {
            logger.info(ConstantUtil.ERROR_ARGS);
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL, ConstantUtil.ERROR_ARGS)).toString();
        }

        Muser currentUser = (Muser) request.getSession().getAttribute("currentUser");
        logger.info("user=:" + currentUser.getUsername());
        //如果是其他分点自行办理的业务则必须在早上9点到下午18点之间正常办理
        if (!ConstantUtil.SERVICE_BRANCH.equals(serverchname)&&!ConstantUtil.SERVICE_BRANCH.equals(currentUser.getServicebranch())) {
            int num = Integer.parseInt(DateUtil.getTime().split(" ")[1].split(":")[0]);
            logger.info("num=" + num);
            if (9 > num || num > 18) {
                return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL, ConstantUtil.ERROR_DATE)).toString();
            }
        }

        String operuser = "操作员-" + currentUser.getUsername();
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("srccount", srccounttype);
        parmMap.put("destcount", destcounttype);
        parmMap.put("srcnum", srcnum);
        parmMap.put("destnum", destnum);
        parmMap.put("ip", IpUtil.getIpAddr(request));
        parmMap.put("operauser", operuser);
        parmMap.put("servicebranch", serverchname);
        parmMap.put("remark", remark);
        parmMap.put("phone", phone);
        parmMap.put("username", username);
        parmMap.put("snum", SerialnumberUtil.Getnum());
        parmMap.put("cardName", cardName);
        parmMap.put("carduname", carduname);
        parmMap.put("cardNum", cardNum);
        parmMap.put("rate", rate);
        parmMap.put("receive", receive);
        parmMap.put("pay", pay);
        cashPoolService.exchange1(parmMap);
        Operation srcoperation = new Operation();
        srcoperation.setSnumber(parmMap.get("snum").toString());
        srcoperation.setOperaUser((String) parmMap.get("operauser"));
        srcoperation.setOperaType(ConstantUtil.MONEY_EXCHANGE);
        srcoperation.setCountType(destcounttype + "兑换" + srccounttype);
        srcoperation.setNum(-(Double) parmMap.get("srcnum"));
        srcoperation.setServicebranch((String) parmMap.get("servicebranch"));
        srcoperation.setPhone((String) parmMap.get("phone"));
        srcoperation.setUsername((String) parmMap.get("username"));
        srcoperation.setOperaTime(DateUtil.getDate());
//        if(!StringUtil.checkStrs(cardName,carduname,cardNum)){
//            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS,JSONObject.fromObject(srcoperation).toString())).toString();
//
//        }
        Task task = new Task();
        task.setTaskname(ConstantUtil.MONEY_EXCHANGE);
        StringBuffer title = new StringBuffer();
        Muser muser = (Muser) request.getSession().getAttribute("currentUser");
        title.append(muser.getServicebranch()).append("网点发起兑换操作");
        title.append(";,币种:").append(srccounttype);
        title.append(";,开户行:").append(cardName);
        title.append(";,持卡人姓名:").append(carduname);
        title.append(";,银行卡号:").append(cardNum);
        title.append(";,金额:").append(String.valueOf(srcnum));
        title.append(";,汇率:").append(rate);
        task.setCreatetime(DateUtil.getTime());
        task.setState(ConstantUtil.TASK_UNDO);
        task.setTaskcontent(title.toString());
        task.setOperauser(muser.getUsername());
        task.setSnum(srcoperation.getSnumber());
        task.setRate(rate);
        taskService.addTask(task);

        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS, JSONObject.fromObject(srcoperation).toString())).toString();

    }

    /**
     * 单个结算
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(path = "/cashSettle",method = RequestMethod.POST)
    public String cashSettle(@RequestParam("id") int id,HttpServletRequest request){
        if (!FilterAuthUtil.checkAuth(request)) {
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH, ConstantUtil.FAIL)).toString();
        }
       Operation operation= operaService.queryById(id);
       CashPool cashPool = cashPoolService.queryByType(operation.getCountType(),ConstantUtil.SERVICE_BRANCH).get(0);
       if(operation.getOi()==1){
           //收入
           cashPool.setBlance(cashPool.getBlance()+operation.getNum());

       }else{
           //支出
           cashPool.setBlance(cashPool.getBlance()+operation.getNum());
       }
        cashPoolService.update(cashPool);
       operation.setSettle(1);
       operaService.update(operation);
        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
    }

    /**
     * 批量结算
     * @param ids
     * @param request
     * @return
     */
    @Transactional
    @RequestMapping(path = "/batchSettle",method = RequestMethod.POST)
    public String cashSettle(@RequestParam("ids") String ids,HttpServletRequest request){
        /*if (!FilterAuthUtil.checkAuth(request)) {
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH, ConstantUtil.FAIL)).toString();
        }*/
        logger.info("batchsettle start ids="+ids);
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("batchSettle");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);

        String[] strs = ids.split("，");
        strs = strs[0].split(",");

        int[] arr = new int[strs.length];

        try{
            for (int i = 0; i < strs.length; i++) {
                arr[i] = Integer.parseInt(strs[i]);
                Operation operation= operaService.queryById(arr[i]);
                CashPool cashPool = cashPoolService.queryByType(operation.getCountType(),ConstantUtil.SERVICE_BRANCH).get(0);
                if(operation.getOi()==1){
                    //收入
                    cashPool.setBlance(cashPool.getBlance()+operation.getNum());

                }else{
                    //支出
                    cashPool.setBlance(cashPool.getBlance()+operation.getNum());
                }
                cashPoolService.update(cashPool);
                operation.setSettle(1);
                operaService.update(operation);
            }
        }
        catch (Exception e) {
            //有一个不成功能则回滚事务
            transactionManager.rollback(status);

        }

        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
    }


    /**
     * 查询支出或收入美金
     *
     * @return
     */
    @RequestMapping(path = "queryScope", method = RequestMethod.GET)
    public String queryScope(@RequestParam("page") int page, @RequestParam("num") int num, @RequestParam("branchname") String servicebranch, @RequestParam("starttime") String starttime, @RequestParam("endtime") String endtime, @RequestParam("type") String type, HttpServletRequest request) {
      /*  if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }*/
        logger.info("queryScope start: branchname=" + servicebranch + ",starttime=" + starttime + ",endtime=" + endtime + ",type=" + type);
        Muser currentUser = (Muser) request.getSession().getAttribute("currentUser");
        logger.info("user=:" + currentUser.getUsername());
        if (!StringUtil.checkStrs(starttime, endtime, type)) {
            logger.info(ConstantUtil.ERROR_ARGS);
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL, ConstantUtil.ERROR_ARGS)).toString();
        }

        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("starttime", starttime);
        parmMap.put("endtime", endtime);
        parmMap.put("counttype", type);
        parmMap.put("page", page * num);
        parmMap.put("num", num);


        if (StringUtil.checkStrs(servicebranch)) {
            if (!"全部".equals(servicebranch)) {
                parmMap.put("servicebranch", servicebranch);
            }

        } else {
            parmMap.put("servicebranch", currentUser.getServicebranch());
        }
        String rs = operaService.queryScope(parmMap);
        return rs;
    }

    /**
     * 查询支出或收入美金
     *
     * @return
     */
    @RequestMapping(path = "queryProfit", method = RequestMethod.GET)
    public String queryProfit(@RequestParam("page") int page, @RequestParam("num") int num, @RequestParam("branchname") String servicebranch, @RequestParam("starttime") String starttime, @RequestParam("endtime") String endtime,  HttpServletRequest request) {
      /*  if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }*/
        logger.info("queryProfit start: branchname=" + servicebranch + ",starttime=" + starttime + ",endtime=" + endtime );
        Muser currentUser = (Muser) request.getSession().getAttribute("currentUser");
        logger.info("user=:" + currentUser.getUsername());
        if (!StringUtil.checkStrs(starttime, endtime)) {
            logger.info(ConstantUtil.ERROR_ARGS);
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL, ConstantUtil.ERROR_ARGS)).toString();
        }

        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("starttime", starttime);
        parmMap.put("endtime", endtime);
        parmMap.put("page", page * num);
        parmMap.put("num", num);


        if (StringUtil.checkStrs(servicebranch)) {
            if (!"全部".equals(servicebranch)) {
                parmMap.put("servicebranch", servicebranch);
            }

        } else {
            parmMap.put("servicebranch", currentUser.getServicebranch());
        }
        String rs = operaService.queryProfit(parmMap);
        return rs;
    }


}
