package cn.com.caogen.controller;

import cn.com.caogen.entity.Muser;
import cn.com.caogen.entity.Task;
import cn.com.caogen.service.ITaskService;
import cn.com.caogen.service.TaskServiceImpl;
import cn.com.caogen.util.ConstantUtil;
import cn.com.caogen.util.DateUtil;
import cn.com.caogen.util.FilterAuthUtil;
import cn.com.caogen.util.ResponseMessage;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.HTML;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * author:huyanqing
 * Date:2018/5/18
 */
@RestController
@RequestMapping("/task")
public class TaskController {
    private static Logger logger = LoggerFactory.getLogger(TaskController.class);
    @Autowired
    private TaskServiceImpl taskService;

    /**
     * 查询未处理的任务
     * @return
     */
    @RequestMapping("queryUndo")
    public String queryUndo(HttpServletRequest request){
        if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }

        Stream<Task> stream=taskService.queryByState(ConstantUtil.TASK_UNDO).stream();
        if(stream==null){
            return null;
        }
        Muser muser=(Muser)request.getSession().getAttribute("currentUser");
        List<Task> taskList=stream.filter((e)->!e.getTaskname().equals(ConstantUtil.VIP)).collect(Collectors.toList());
        if(ConstantUtil.SERVICE_BRANCH.equals(muser.getServicebranch())){
            return JSONArray.fromObject(taskList).toString();
        }

        taskList= taskList.stream().filter((e)->e.getTaskcontent().split("网点")[0].equals(muser.getServicebranch())).collect(Collectors.toList());
        return JSONArray.fromObject(taskList).toString();
    }
    /**
     * 查询处理中的任务
     * @return
     */
    @RequestMapping("queryMarkdo")
    public String queryMarkdo(HttpServletRequest request){
        if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }
        Muser muser=(Muser)request.getSession().getAttribute("currentUser");
        Stream<Task> stream=taskService.queryByState(ConstantUtil.TASK_DOING).stream();
        List<Task> taskList=stream.filter((e)->!e.getTaskname().equals(ConstantUtil.VIP)).collect(Collectors.toList());

        if(ConstantUtil.SERVICE_BRANCH.equals(muser.getServicebranch())){
            return JSONArray.fromObject(taskList).toString();
        }
        taskList= taskList.stream().filter((e)->e.getTaskcontent().split("网点")[0].equals(muser.getServicebranch())).collect(Collectors.toList());
        return JSONArray.fromObject(taskList).toString();

    }

    /**
     * 查询已处理的任务
     * @return
     */
    @RequestMapping("queryDone")
    public String queryDone(HttpServletRequest request){
        if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }

        Muser muser=(Muser)request.getSession().getAttribute("currentUser");
        Stream<Task> stream=taskService.queryByState(ConstantUtil.TASK_DONE).stream();
        List<Task> taskList=stream.filter((e)->!e.getTaskname().equals(ConstantUtil.VIP)).collect(Collectors.toList());
        if(ConstantUtil.SERVICE_BRANCH.equals(muser.getServicebranch())){
            return JSONArray.fromObject(taskList).toString();
        }
        taskList= taskList.stream().filter((e)->e.getTaskcontent().split("网点")[0].equals(muser.getServicebranch())).collect(Collectors.toList());
        return JSONArray.fromObject(taskList).toString();

    }
    @RequestMapping("dotask")
    public String doTask(@RequestParam("id") int id, HttpServletRequest request){
        logger.info("dotask start: id="+id);
        if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }
        Muser currentUser=(Muser)request.getSession().getAttribute("currentUser");
        logger.info("user=:"+currentUser.getUsername());

        Muser user=(Muser)request.getSession().getAttribute("currentUser");
        Map<String,Object> parmMap=new HashMap<String,Object>();
        parmMap.put("id",id);
        parmMap.put("state",ConstantUtil.TASK_DONE);
        parmMap.put("endtime",DateUtil.getTime());
        String douser="操作员-"+user.getUsername();
        parmMap.put("douser",douser);
        parmMap.put("servicebranch",user.getServicebranch());
        taskService.updateTask(parmMap);
        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
    }

    @RequestMapping("marktask")
    public String markTask(@RequestParam("id") int id, HttpServletRequest request){
        logger.info("dotask start: id="+id);
        if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }
        Muser currentUser=(Muser)request.getSession().getAttribute("currentUser");
        logger.info("user=:"+currentUser.getUsername());

        Muser user=(Muser)request.getSession().getAttribute("currentUser");
        Map<String,Object> parmMap=new HashMap<String,Object>();
        parmMap.put("id",id);
        parmMap.put("state",ConstantUtil.TASK_DOING);
        parmMap.put("endtime",DateUtil.getTime());
        String douser="操作员-"+user.getUsername();
        parmMap.put("douser",douser);
        parmMap.put("servicebranch",user.getServicebranch());
        taskService.updateTask(parmMap);
        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
    }

    @RequestMapping("queryAll")
    public String queryAll(HttpServletRequest request){
        logger.info("queryAll start:");
        if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }
        Muser currentUser=(Muser)request.getSession().getAttribute("currentUser");
        logger.info("user=:"+currentUser.getUsername());
        ;
        return JSONArray.fromObject(taskService.queryAll()).toString();
    }
}
