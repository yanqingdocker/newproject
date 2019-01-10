package cn.com.caogen.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * author:huyanqing
 * Date:2018/7/24
 */
@Setter
@Getter
public class Profits {
    private int id;
    /**
     * 分配对象
     */
    private String destobj;
    /**
     * 金额类型
     */
    private String moneytype;
    /**
     * 金额额度
     */
    private double num;
    /**
     * 操作人
     */
    private String operauser;
    /**
     * 分配时间
     */
    private String creattime;
    /**
     * 备注
     */
    private String remark;
}
