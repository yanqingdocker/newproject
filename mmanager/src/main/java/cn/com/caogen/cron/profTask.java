package cn.com.caogen.cron;


import cn.com.caogen.entity.CashPool;
import cn.com.caogen.entity.ServiceBranch;
import cn.com.caogen.externIsystem.service.RateService;
import cn.com.caogen.mapper.RedisMapper;
import cn.com.caogen.service.CashPoolServiceImpl;
import cn.com.caogen.service.ServiceBranchImpl;
import cn.com.caogen.util.ConstantUtil;
import cn.com.caogen.util.DataMonitor;
import cn.com.caogen.util.JedisUtil;
import cn.com.caogen.util.SerializeUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.text.SimpleDateFormat;
import java.util.*;

/**
 * author:huyanqing
 * Date:2018/4/23
 */
@Component
public class profTask {

    private static Logger logger = LoggerFactory.getLogger(profTask.class);

    @Autowired
    private CashPoolServiceImpl cashPoolService;
    @Autowired
    private ServiceBranchImpl serviceBranch;
    @Autowired
    private RedisMapper redisMapper;

    /**
     * 0 30 8 * * ?  每日8点半采集一次
     *  每天0晨12点调用一次汇率接口，并刷新redis //1 0 0 * * ?
     */
    @Scheduled(cron = "0 30 8 * * ? ")
    public void collectData(){
        logger.info("collectData start");
        List<ServiceBranch> serviceBranchList=serviceBranch.queryAll();

        List<CashPool> cashPoolList=cashPoolService.queryAll();
        logger.info(cashPoolList+"");
        if(cashPoolList!=null||!cashPoolList.isEmpty()){
            try {
                Map<String,String> map=new HashMap<String,String>();
                map.put("cash", net.sf.json.JSONArray.fromObject(cashPoolList).toString());
                redisMapper.update(map);
                JedisUtil.getJedis().set("cash".getBytes(),SerializeUtil.serialize(cashPoolList));
            }catch (NullPointerException e){
                Map<String,String> map=new HashMap<String,String>();
                map.put("cash", net.sf.json.JSONArray.fromObject(cashPoolList).toString());
                redisMapper.update(map);
            }
            logger.info("now time:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        }

    }
}
