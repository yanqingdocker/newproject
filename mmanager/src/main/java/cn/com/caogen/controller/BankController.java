package cn.com.caogen.controller;

import cn.com.caogen.entity.BankCard;
import cn.com.caogen.entity.Muser;
import cn.com.caogen.externIsystem.service.BackCardService;
import cn.com.caogen.service.IBankCardService;
import cn.com.caogen.util.ConstantUtil;
import cn.com.caogen.util.FilterAuthUtil;
import cn.com.caogen.util.ResponseMessage;
import cn.com.caogen.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author:huyanqing
 * Date:2018/4/23
 */
@RestController
@RequestMapping("/bank")
public class BankController {

    private static Logger logger = LoggerFactory.getLogger(BankController.class);

    @Autowired
    private IBankCardService bankCardService;


    /**
     * 查询所有的银行卡
     */
    @RequestMapping(path = "/queryAll", method = RequestMethod.GET)
    public String queryAll(HttpServletRequest request) {
        if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }
        logger.info("query start");
        Muser currentUser=(Muser) request.getSession().getAttribute("currentUser");
        logger.info("user=:"+currentUser.getUsername());
        Map<String, Object> parmMap = new HashMap<String, Object>();
        List<BankCard> bankCardList = bankCardService.queryCondition(parmMap);
        return JSONArray.fromObject(bankCardList).toString();
    }
}
