package cn.com.caogen.controller;


import cn.com.caogen.service.PrintServiceImp;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/print")
public class PrintController {
    private static Logger logger = LoggerFactory.getLogger( PrintController.class);

    @RequestMapping("/prin")
    public void print(@RequestParam("print")String print, HttpServletResponse response, HttpServletRequest request){
        String msg="";
        String msg1="";
        try {
            JSONObject jsonObject = JSONObject.fromObject(print);
            String username=jsonObject.getString("username");
            String srccounttype=jsonObject.getString("srccounttype");
            String srcnum=jsonObject.getString("srcnum");
            String destcounttype=jsonObject.getString("destcounttype");
            String destnum=jsonObject.getString("destnum");
            String servicebranch=jsonObject.getString("servicebranch");
            String thisrate=jsonObject.getString("thisrate");
//            String huilu=jsonObject.getString("huilu");
//            String bizhong=jsonObject.getString("bizhong");
//            String shouxufei=jsonObject.getString("shouxufei");
//            String palace=jsonObject.getString("palace");
            Map<String,Object> map=new HashMap<String, Object>();
            map.put("username",username);
//            map.put("destcounttype",destcounttype);
//            map.put("destnum",destnum);
            map.put("servicebranch",servicebranch);
            map.put("thisrate",thisrate);
            msg=PrintServiceImp.printmenu("E:\\1.pdf","1.pdf",map,destcounttype,destnum);
            //Thread.sleep(1000);

            msg1=PrintServiceImp.printmenu("E:\\1.pdf","1.pdf",map,srccounttype,srcnum);
        }catch (Exception e){
            logger.info("print fail");
        }
    }
}
