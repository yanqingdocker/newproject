package cn.com.caogen.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * author:huyanqing
 * Date:2018/5/17
 */
@Setter
@Getter
public class CashPool implements Serializable {
    private int id;
    private String countid;
    private String counttype;
    private Double blance;
    private String lasttime;

    @Override
    public String toString() {
        return "CashPool{" +
                "id=" + id +
                ", countid='" + countid + '\'' +
                ", counttype='" + counttype + '\'' +
                ", blance=" + blance +
                ", lasttime='" + lasttime + '\'' +
                ", servicebranch='" + servicebranch + '\'' +
                '}';
    }

    private String servicebranch;
}
