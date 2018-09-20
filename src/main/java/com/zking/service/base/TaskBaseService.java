package com.zking.service.base;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.repository.Deployment;

public abstract class TaskBaseService {
	
	private String executionId = "";
	
	/**
	 * 主流程
	 */
	public void toDo() {
		// TODO 主流程
		//初始化引擎并创建流程
		ProcessEngine processEngine = buildProcessEngine();
		
		run(processEngine);
	}
	
	/**
	 * 初始化引擎
	 */
	public ProcessEngine buildProcessEngine() {
		//表不存在的话创建表
		ProcessEngine processEngine = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource("activiti.cfg.xml")
		.buildProcessEngine();
		
		return processEngine;
	}
	
	/**
	 * 创建流程
	 */
	public void createProcess(ProcessEngine processEngine) {
		Deployment deployment = processEngine.getRepositoryService().createDeployment().name("入库操作")
				.addClasspathResource("diagrams/test.bpmn").addClasspathResource("diagrams/test.png").deploy();
	}
	
	/**
	 * 业务处理
	 * @param processEngine
	 */
	public abstract void run(ProcessEngine processEngine);

}
