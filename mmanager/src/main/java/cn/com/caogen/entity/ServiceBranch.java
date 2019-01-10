package cn.com.caogen.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * author:huyanqing
 * Date:2018/6/4
 */
@Setter
@Getter
public class ServiceBranch implements Serializable {
    private int id;
    private String branchname;
    private String address;
    private String servicephone;
    private String administrator;
    private String createtime;
}
