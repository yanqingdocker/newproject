package cn.com.caogen.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * author:huyanqing
 * Date:2019/1/5
 */
@Setter
@Getter
public class Redis {
    private int id;
    private String vkey;
    private String kvalue;
}
