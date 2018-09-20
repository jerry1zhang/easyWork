package com.zking.controller.test;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.zking.config.KeyConfig;
import com.zking.util.MD5Util;
import com.zking.util.TimeUtil;
import org.activiti.engine.*;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
//import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.zking.controller.base.BaseController;
import org.testng.annotations.Test;

@Controller
@RequestMapping("test")
public class TestController extends BaseController{
	
	@Resource
	RepositoryService repositoryService;
	@Resource
	TaskService taskService;
	@Resource
	IdentityService identityService;
	@Resource
	RuntimeService runtimeService;
	@Resource
	HistoryService historyService;
	
	@RequestMapping("/1")
	public String index(){
		
		return "login";
	}

	/**
	 * 新建组用户
	 * @return
	 */
	@RequestMapping("/addUser")
	public String addUser(){
		//新建用户
		if(getRequest().getParameter("username")!=null){
			User user = identityService.newUser(getRequest().getParameter("username"));
			user.setFirstName(getRequest().getParameter("firstname"));
			user.setLastName(getRequest().getParameter("lastname"));
			user.setEmail(getRequest().getParameter("email"));
			user.setPassword(MD5Util.MD5Encode(getRequest().getParameter("password")));
			identityService.saveUser(user);
			identityService.createMembership(getRequest().getParameter("username"),getRequest().getParameter("group"));
		}
		//查询现有组和用户
		List<Group> groupList = identityService.createGroupQuery().list();
		getRequest().setAttribute("grouplist",groupList);
		List<User> userList = identityService.createUserQuery().list();
		getRequest().setAttribute("userlist",userList);
		return "test/addUser";
	}

	/**
	 * 新建组
	 * @param request
	 * @return
	 */
	@RequestMapping("/addGroup")
	public String addGroup(HttpServletRequest request){
		//新建组
		if(request.getParameter("groupname")!=null){
			Group group = identityService.newGroup(request.getParameter("groupname"));
			group.setName(request.getParameter("groupname"));
			group.setType(request.getParameter("grouptype"));
			identityService.saveGroup(group);
		}
		//查询现有组
		List<Group> groupList = identityService.createGroupQuery().list();
		request.setAttribute("grouplist",groupList);
		return "test/addGroup";
	}

	@RequestMapping("/addTask")
	public String addTask(HttpServletRequest request){
		//TODO 获取已经部署列表
		//获取启动流程标号集
		if(request.getParameterValues("taskidlist")!=null){
			String[] list = request.getParameterValues("taskidlist");
			for (String e:list) {
				User user = (User)getSession().getAttribute("User");
				//两天后任务结束
				Date duedate = TimeUtil.getAddDay(KeyConfig.CAIGOUENDDAY);
				Map<String,Object> var = new HashMap<>();
				var.put("duedate",duedate);
				var.put("applyuser",user.getId());

				//创建任务
				ProcessInstance pi = runtimeService.startProcessInstanceByKey(e,user.getId(),var);
				logger.info("流程编号："+pi.getProcessDefinitionId()+"流程名："+pi.getName()+"已经启动");
			}
		}
		//流程定义查询
		List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery().list();
		request.setAttribute("processDefinitions", processDefinitions);
//		processDefinitions.get(0).getId();
//		processDefinitions.get(0).getKey();
//		processDefinitions.get(0).getName();
//		processDefinitions.get(0).getResourceName();
//		processDefinitions.get(0).getDiagramResourceName();
//		processDefinitions.get(0).getCategory();
//		processDefinitions.get(0).getDeploymentId();
//		processDefinitions.get(0).getDescription();
//		processDefinitions.get(0).getTenantId();
//		processDefinitions.get(0).getVersion();
//		processDefinitions.get(0).getEngineVersion();
		//流程执行状态
		List<ProcessInstance> processInstances =  runtimeService.createProcessInstanceQuery().list();
		request.setAttribute("processInstances",processInstances);
//		processInstances.get(0).getProcessDefinitionId()
//		processInstances.get(0).getName();
//		processInstances.get(0).getBusinessKey();
//		processInstances.get(0).getDeploymentId();
//		processInstances.get(0).getDescription();
//		processInstances.get(0).getLocalizedDescription();
//		processInstances.get(0).getLocalizedName();
//		processInstances.get(0).getProcessDefinitionKey();
//		processInstances.get(0).getProcessDefinitionId();
//		processInstances.get(0).getProcessDefinitionName();
//		processInstances.get(0).getProcessDefinitionVersion();
//		processInstances.get(0).getStartTime();
//		processInstances.get(0).getStartUserId();
//		processInstances.get(0).getTenantId();
		return "test/addTask";
	}

	@RequestMapping("/bushu")
	public String all(HttpServletRequest request){
		if (request.getAttributeNames()!=null) {
			String[] list = request.getParameterValues("bushuidlist");
			for (String e:list ) {
				//TODO 从静态部署表中提取对应的部署文件地址
				repositoryService.createDeployment().name(e)
						.addClasspathResource(e).addClasspathResource(e).deploy();
				logger.info(e + "部署成功");
				//TODO 修改部署状态
			}
		}
//		repositoryService.createDeployment().name("入库操作")
//		.addClasspathResource("diagrams/test.bpmn").addClasspathResource("diagrams/test.png").deploy();
		//TODO 查找现静态部署表上部署信息并返回前台
//		request.setAttribute("bushulist",list);
		return "test/bushulist";

	}

	@RequestMapping(value="/2" ,method=RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public Object test(HttpServletRequest req) throws UnsupportedEncodingException{
		JsonObject json = new JsonObject();
		List<Task> taskList = taskService.createTaskQuery().taskCandidateGroup("采购").list();
		logger.info("taskList="+taskList.size());
		List<Group> groupList = identityService.createGroupQuery().groupName("采购").list();
		logger.info("groupList="+groupList.size());
		json.addProperty("taskList", taskList.toString());
		json.addProperty("groupList", groupList.toString());
		return new Gson().toJson(json);
	}


	
	@Test
	public void ctables(){

		//表不存在的话创建表
		ProcessEngine processEngine = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource("activiti.cfg.xml")
		.buildProcessEngine();
	
		System.out.println("------processEngine:" + processEngine);
	
		//创建流程
		Deployment deployment = processEngine.getRepositoryService().createDeployment().name("商品入库操作")
		.addClasspathResource("diagrams/createGoodList/createGoodList.bpmn").addClasspathResource("diagrams/createGoodList/createGoodList.png").deploy();
	
		System.out.println(deployment.getId());
		System.out.println(deployment.getName());


	}
	
	//@Test  
	public void startProcessInstance(){  
	    String processInstanceKey = "myProcess";  
	    ProcessEngine processEngine = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource("activiti.cfg.xml")
		.buildProcessEngine();
	    ProcessInstance pi = processEngine.getRuntimeService()  
	                        .startProcessInstanceByKey(processInstanceKey);  
	    System.out.println("流程实例id：" + pi.getId());  //流程实例id  101  
	    System.out.println("流程定义id：" + pi.getProcessDefinitionId());   //流程定义ID helloworld:1:4  
	}  
	
	//@Test
    public void findMyTaskInfo(){  
        String assignee = "采购";  
      //表不存在的话创建表
		ProcessEngine processEngine = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource("activiti.cfg.xml")
		.buildProcessEngine();
        List<Task> listTask = processEngine.getTaskService()  
            .createTaskQuery()  
            .taskAssignee(assignee)  
            .list();  
          
        if (listTask!= null && listTask.size() >0) {  
            for (Task task : listTask) {  
            	
                System.out.println("任务ID：" + task.getId());  
                System.out.println("任务名称：" + task.getName());  
                System.out.println("任务时间：" + task.getCreateTime());  
                System.out.println("任务的班里人：" + task.getAssignee());  
                System.out.println("任务的实例ID：" + task.getProcessDefinitionId());  
                System.out.println("执行对象的ID：" + task.getExecutionId());  
                System.out.println("任务的班里人：" + task.getAssignee());  
                System.out.println("流程定义ID：" + task.getProcessInstanceId());  
            }  
        }  
              
          
    }  
    
//    @Test  
    public void createUserAndGroup() {
    	ProcessEngine processEngine = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource("activiti.cfg.xml")
		.buildProcessEngine();
    	IdentityService identityService = processEngine.getIdentityService();//认证：保存组和用户信息 
    	Group group = identityService.newGroup("采购");
    	identityService.saveGroup(group);//建立组 
//    	identityService.saveGroup(new GroupEntity("总经理"));//建立组 
    	User user = identityService.newUser("小王");
    	identityService.saveUser(user);//建立用户 
//    	identityService.saveUser(new UserEntity("小李")); //建立用户 
//    	identityService.saveUser(new UserEntity("小王")); //建立用户 
    	identityService.createMembership("小王", "采购");//建立组和用户关系 
//    	identityService.createMembership("小李", "部门经理");//建立组和用户关系 
//    	identityService.createMembership(“小王”, “总经理”);//建立组和用户关系
    	
    }
    
	@Test
    public void delAll() {
    	ProcessEngine processEngine = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource("activiti.cfg.xml")
		.buildProcessEngine();
    	RuntimeService run = processEngine.getRuntimeService();

    	List<ProcessInstance> processInstances =  run.createProcessInstanceQuery().list();
    	for (ProcessInstance e : processInstances) {
    		run.deleteProcessInstance(e.getId(), "删除测试");
		}
    	RepositoryService rep = processEngine.getRepositoryService();
    	List<ProcessDefinition> processDefinitions = rep.createProcessDefinitionQuery().list();
    	for (ProcessDefinition e : processDefinitions) {
			rep.deleteDeployment(e.getDeploymentId());
		}
    }
    @Test
    public void Task(){
		Method[] methods = this.getClass().getDeclaredMethods();
		for (Method method: methods){
			String methodName = method.getName();
			if(methodName.indexOf("set")!=-1){
				try {
//					String value = item.getClass()
//							.getDeclaredMethod("get"+methodName.replace("set",""))
//							.invoke(item).toString();
					Class parameterType = method.getParameterTypes()[0];
//					this.getClass().getMethod(methodName,parameterType).invoke(item,value);
				}catch (Exception e){

				}
			}
		}
	}
}
