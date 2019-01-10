package cn.com.caogen.cron;


import cn.com.caogen.entity.Borrow;
import cn.com.caogen.entity.Task;
import cn.com.caogen.externIsystem.service.MessageService;
import cn.com.caogen.service.BorrowServiceImpl;
import cn.com.caogen.service.TaskServiceImpl;
import cn.com.caogen.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * author:huyanqing
 * Date:2018/6/8
 */
@Component
public class BorrowTask {

    private static Logger logger = LoggerFactory.getLogger(BorrowTask.class);
    @Autowired
    private BorrowServiceImpl borrowService;
    @Autowired
    private TaskServiceImpl taskService;


    /**
     * 0 0/1 * * * ? 测试定时
     * 1 0 12 * * ?  每天中午12点查看借款期限
     *
     */
    @Scheduled(cron = "1 0 12 * * ? ")
    public void checkBorrow(){


        logger.info("checkBorrow start:" + DateUtil.getTime());
        String todaydate=DateUtil.getDate().split("-")[2];
        Map<String,Object> parmMap=new HashMap<String,Object>();
        parmMap.put("status",0);
        List<Borrow> borrowList=borrowService.queryAll(parmMap);

        String temp="";
        for(Borrow borrow:borrowList){
            String date=borrow.getCreatetime().split(" ")[0].split("-")[2];
            int num=Integer.parseInt(todaydate)-Integer.parseInt(date);
            if(borrow.getTerm()-num==1){
                temp=borrow.getSnumber()+"即将到期";
            }else if(borrow.getTerm()-num<1){
                temp=borrow.getSnumber()+"已经到期";
                String msg="平台账号"+borrow.getBorrowerphone()+"于"+borrow.getCreatetime()+"预支的"+borrow.getNum()+"("+borrow.getMoneytype()+")，期限"+borrow.getTerm()+"天，现在已逾期，请尽快收回借款。";
                logger.info("=================="+msg);
                addTask(msg);
            }
            String msg="尊敬的客户您好，您的预支订单"+temp+",请您尽快前往南方国际的任何一个网点进行处理。祝您生活愉快，谢谢。";
            String telphone=borrow.getBorrowerphone();
            int c=MessageService.sendMessage(telphone,msg);

        }
    }

    public void addTask(String msg){
        Task task=new Task();
        task.setTaskcontent(msg);
        task.setTaskname("还款提醒");
        task.setCreatetime(DateUtil.getTime());
        task.setOperauser("平台");
        taskService.addTask(task);
    }

}

