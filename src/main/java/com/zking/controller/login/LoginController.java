package com.zking.controller.login;

import com.zking.biz.menu.MenuBiz;
import com.zking.biz.user.UserBiz;
import com.zking.config.ConfigCode;
import com.zking.enetity.UserBody;
import com.zking.util.MD5Util;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.persistence.entity.UserEntityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zking.controller.base.BaseController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("login")
public class LoginController extends BaseController{

	@Resource
	IdentityService identityService;
	@Resource
	TaskService taskService;
	@Resource
	RuntimeService runtimeService;
	@Resource
	UserBiz userBiz;
	@Resource
	MenuBiz menuBiz;

	@RequestMapping("/loginPage")
	public String loginPage(){
		return "login";
	}
	
	@RequestMapping("/doLogin")
	public String doLogin(){
		if(getSession().getAttribute("User")==null){
			User user = new UserEntityImpl();
			user.setId(getRequest().getParameter("username"));
			user.setPassword(getRequest().getParameter("password"));
			if(identityService.createUserQuery().userId(getRequest().getParameter("username")).singleResult()==null) {
				getRequest().setAttribute("error_info",ConfigCode.USER_ERROR_2.getValue());
				return "login";
			}
			UserBody userBody = userBiz.getUser(identityService,user);
			if(userBody != null) {
				if(userBody.getUser().getPassword().equals(MD5Util.MD5Encode(getRequest().getParameter("password")))) {
					getSession().setAttribute("User", userBody.getUser());
					getSession().setAttribute("Group", userBody.getGroup());
					
				}else{
					getRequest().setAttribute("error_info",ConfigCode.USER_ERROR_1.getValue());
				}
			}else{
				getRequest().setAttribute("error_info",ConfigCode.USER_ERROR_2.getValue());
				return "login";
			}

		}
		identityService.setAuthenticatedUserId(((User)getSession().getAttribute("User")).getId());
		getRequest().setAttribute("title1", ConfigCode.page_title_1.getValue());
		getRequest().setAttribute("title2",ConfigCode.page_title_2.getValue());
		getRequest().setAttribute("apple_title",ConfigCode.page_title_1.getValue()+ConfigCode.page_title_2.getValue());
		getRequest().setAttribute("menu",menuBiz.initMenu(getGroup().getId()));
		return "admin-index";
	}

	@RequestMapping("over")
	public String over(){
		getSession().removeAttribute("User");
		getSession().removeAttribute("Group");
		return "login";
	}

	@RequestMapping("/doTask")
	public String doTask(){
		if(getRequest().getParameterValues("taskidlist")!=null){
			String[] list = getRequest().getParameterValues("taskidlist");
			for (String e:list) {
				User user = (User)getSession().getAttribute("User");
				//创建任务
				Task task = taskService.createTaskQuery().taskId(e).singleResult();
				if(task!=null){
					taskService.complete(e);
					logger.info("用户id:"+user.getId()+"||任务编号:"+task.getId()+"||任务名:"+task.getName()+"||任务成功完成");
				}else{
					logger.debug("用户id:"+user.getId()+"任务编号:"+e+"不存在"+"||任务完成失败");
				}

			}
		}
		return "test/completeTask";
	}
}
