package cn.com.caogen.service;

import cn.com.caogen.entity.CashPool;
import cn.com.caogen.entity.Gain;
import cn.com.caogen.entity.Loss;
import cn.com.caogen.mapper.CashPoolMapper;
import cn.com.caogen.mapper.GainMapper;
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
import java.util.Map;

/**
 * author:huyanqing
 * Date:2018/7/24
 */
@Service
public class GainServiceImpl implements IGainService{
    @Autowired
    private GainMapper gainMapper;

    @Autowired
    private DataSourceTransactionManager transactionManager;


    @Override
    @Transactional
    public void add(Gain gain) {
        gainMapper.add(gain);
    }

    @Override
    public List<Gain> queryGains(Map<String, String> parmMap) {
        return gainMapper.queryGains(parmMap);
    }
}
