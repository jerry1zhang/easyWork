package com.zking.controller.task;

import com.zking.biz.task.TaskBiz;
import com.zking.controller.base.BaseController;
import com.zking.util.PageData;
import org.activiti.engine.IdentityService;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

@Controller
@RequestMapping("/task")
public class TaskPageController extends BaseController{
    @Resource
    IdentityService identityService;
    @Resource
    TaskService taskService;

    @Resource
    TaskBiz taskBiz;

    @RequestMapping(value="/getTaskList", method= RequestMethod.GET)
    public String getTaskList(Model model){
        User user = (User)getSession().getAttribute("User");
        String username = user.getId();
        PageData sendData = new PageData();
        sendData.put("username",username);
        PageData resultData = taskBiz.getTaskList(identityService,taskService,sendData);
        model.addAttribute("tasks",resultData.get("tasks"));
        return "taskList";
    }
}
