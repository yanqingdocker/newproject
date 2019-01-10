package cn.com.caogen.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * author:huyanqing
 * Date:2018/12/14
 */
@Setter
@Getter
public class Gaine {
    /**
     * id
     */
    private int id;
    /**
     * 结算金额
     */
    private Double num;
    /**
     * 币种类型
     */
    private String couttype;
    /**
     * 结算人
     */
    private String operaor;
    /**
     * 结算时间
     */
    private String creatime;
    /**
     * 结算单号
     */
    private String snumber;

}
