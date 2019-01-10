package cn.com.caogen.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author:huyanqing
 * Date:2018/5/7
 */
@Setter
@Getter
public class Role {
    private int id;
    private String rolename;
    private String createtime;
    private List<Integer> idlist=new ArrayList<Integer>();
    private List<String> namelist=new ArrayList<String>();
}
