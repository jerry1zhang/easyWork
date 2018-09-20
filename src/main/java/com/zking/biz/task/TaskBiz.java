package com.zking.biz.task;

import com.zking.biz.BaseBizImpl;
import com.zking.util.PageData;
import org.activiti.engine.IdentityService;
import org.activiti.engine.TaskService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public interface TaskBiz{
    PageData getTaskList(IdentityService identityService, TaskService taskService, PageData data);
}
