package cn.com.caogen.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * author:huyanqing
 * Date:2018/4/12
 */
@Setter
@Getter
public class Operation {
    //id
    private int id;
    //交易流水号
    private String snumber;
    //账户id
    private String countid;
    //账户类型
    private String countType;
    //操作金额
    private Double num;
    //操作类型
    private String operaType;
    //操作时间
    private String operaTime;
    //操作人
    private String operaUser;


    //收入1/支出0
    private int oi;
    //操作IP
    private String operaIp;
   //网点名称
    private String servicebranch;
    //备注信息
    private String remark;
    //联系电话
    private String phone;
    //客户姓名
    private String username;

    //持卡人姓名
    private String cardUname;
    //开户行
    private String cardName;
    //卡号
    private String cardNum;
    //汇率
    private String rate;
    //是否结算
    private int settle;



}
