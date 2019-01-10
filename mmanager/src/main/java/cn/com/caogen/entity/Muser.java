package cn.com.caogen.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author:huyanqing
 * Date:2018/5/3
 */
@Setter
@Getter
public class Muser {
    private int id;
    private String username;
    private String password;
    private String servicebranch;
    private List<Integer> idlist=new ArrayList<Integer>();
    private List<String> namelist=new ArrayList<String>();
    private List<String> auths=new ArrayList<String>();
}
