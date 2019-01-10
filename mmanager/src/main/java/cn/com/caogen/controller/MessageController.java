package cn.com.caogen.controller;

import cn.com.caogen.entity.Muser;
import cn.com.caogen.entity.Task;
import cn.com.caogen.entity.User;
import cn.com.caogen.externIsystem.service.MessageService;
import cn.com.caogen.service.IUserService;
import cn.com.caogen.service.TaskServiceImpl;
import cn.com.caogen.util.ConstantUtil;
import cn.com.caogen.util.DateUtil;
import cn.com.caogen.util.ResponseMessage;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * author:huyanqing
 * Date:2018/6/12
 */
@RestController
@RequestMapping("/message")
public class MessageController {

    private static Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private IUserService userServiceImpl;
    @Autowired
    private TaskServiceImpl taskService;
    @RequestMapping("query")
    public String query(HttpServletRequest request){
        Stream<Task> stream=taskService.queryAll().stream();
        List<Task> taskList=stream.filter((e)->e.getTaskname().equals(ConstantUtil.VIP)).collect(Collectors.toList());
        return JSONArray.fromObject(taskList).toString();
    }

    /**
     * 同意
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(path = "accessmessage",method = RequestMethod.POST)
    public String domessage(@RequestParam("id") int id, HttpServletRequest request){
        logger.info("accessmessage start id="+id);
        Muser currentUser=(Muser)request.getSession().getAttribute("currentUser");
        logger.info("user=:"+currentUser.getUsername());
        Stream<Task> stream=taskService.queryAll().stream();
        Task task=stream.filter((e)->e.getId()==id).collect(Collectors.toList()).get(0);
        String telphone=task.getTaskcontent();
        User vipuser=getUser(telphone,null);
        vipuser.setLeavel(1);
        userServiceImpl.update(vipuser);
        String msg="尊敬的客户您好！恭喜您，您申请开通VIP经审核已经通过。";
        MessageService.sendMessage(telphone,msg);
        Muser user=(Muser)request.getSession().getAttribute("currentUser");
        MessageService.sendMessage(telphone,msg);
        Map<String,Object> parmMap=new HashMap<String,Object>();
        parmMap.put("id",id);
        parmMap.put("state",ConstantUtil.ACCESSVIP);
        parmMap.put("endtime",DateUtil.getTime());
        String douser="操作员-"+user.getUsername();
        parmMap.put("douser",douser);
        parmMap.put("servicebranch",user.getServicebranch());
        taskService.updateTask(parmMap);
        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
    }

    @RequestMapping(path = "queryaccess",method = RequestMethod.GET)
    public String queryaccess(HttpServletRequest request){
        logger.info("queryaccess start");
        Muser currentUser=(Muser)request.getSession().getAttribute("currentUser");
        logger.info("user=:"+currentUser.getUsername());
        Stream<Task> stream=taskService.queryAll().stream();
        List<Task> taskList=stream.filter((e)->e.getTaskname().equals(ConstantUtil.VIP)&&e.getState().equals(ConstantUtil.ACCESSVIP)).collect(Collectors.toList());
        return JSONArray.fromObject(taskList).toString();
    }
    @RequestMapping(path = "queryrefuse",method = RequestMethod.GET)
    public String queryrefuse(HttpServletRequest request){
        logger.info("queryrefuse start");
        Muser currentUser=(Muser)request.getSession().getAttribute("currentUser");
        logger.info("user=:"+currentUser.getUsername());
        Stream<Task> stream=taskService.queryAll().stream();
        List<Task> taskList=stream.filter((e)->e.getTaskname().equals(ConstantUtil.VIP)&&e.getState().equals(ConstantUtil.REFUSEVIP)).collect(Collectors.toList());
        return JSONArray.fromObject(taskList).toString();
    }
    @RequestMapping(path = "querydoing",method = RequestMethod.GET)
    public String querydoing(HttpServletRequest request){
        logger.info("querydoing start");
        Muser currentUser=(Muser)request.getSession().getAttribute("currentUser");
        logger.info("user=:"+currentUser.getUsername());
        Stream<Task> stream=taskService.queryAll().stream();
        List<Task> taskList=stream.filter((e)->e.getTaskname().equals(ConstantUtil.VIP)&&e.getState().equals(ConstantUtil.TASK_UNDO)).collect(Collectors.toList());
        return JSONArray.fromObject(taskList).toString();
    }

    /**
     * 驳回
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(path = "refuesemessage",method = RequestMethod.POST)
    public String refuesemessage(@RequestParam("id") int id, HttpServletRequest request){
        logger.info("refuesemessage start id="+id);
        Muser currentUser=(Muser)request.getSession().getAttribute("currentUser");
        logger.info("user=:"+currentUser.getUsername());
        Stream<Task> stream=taskService.queryAll().stream();
        Task task=stream.filter((e)->e.getId()==id).collect(Collectors.toList()).get(0);
        String telphone=task.getTaskcontent();
        String msg="尊敬的客户您好！对不起，您申请开通VIP经审核失败。";
        MessageService.sendMessage(telphone,msg);
        Muser user=(Muser)request.getSession().getAttribute("currentUser");
        Map<String,Object> parmMap=new HashMap<String,Object>();
        parmMap.put("id",id);
        parmMap.put("state",ConstantUtil.REFUSEVIP);
        parmMap.put("endtime",DateUtil.getTime());
        String douser="操作员-"+user.getUsername();
        parmMap.put("douser",douser);
        parmMap.put("servicebranch",user.getServicebranch());
        taskService.updateTask(parmMap);

        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
    }

    public User getUser(String telphone, String userid){
        logger.info("getUser start: telphone="+telphone+",userid="+userid);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("phone", telphone);
        User temp = null;
        List<User> userList=userServiceImpl.queryAll(map);
        if(userList.isEmpty()){
            return null;
        }else {
            return userList.get(0);
        }
    }
}
