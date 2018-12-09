package com.test.junit;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ModelQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

public class HelloWorldTest {

	@Test
	public void startProcess() throws Exception{
		// 创建一个使用H2内存数据库的流程处理引擎，默认连接url为jdbc:h2:mem:activiti
		ProcessEngine processEngine = ProcessEngineConfiguration
				.createStandaloneInMemProcessEngineConfiguration()
				.buildProcessEngine();
		// ProcessEngine一共有7个XxxService对象，
		RepositoryService repositoryService = processEngine.getRepositoryService();
		repositoryService.createDeployment().addClasspathResource("diagrams/helloworld.bpmn").deploy();
		
		//XxxService.createXxxQuery()方法创建查询对象，查询对应的<process>标签定义
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
				.singleResult();
		// id 是process标签的id属性值加上1:4这个版本号
		System.out.println("=====key:"+processDefinition.getKey() + "====name:" +processDefinition.getName()+"=====id:"+processDefinition.getId());
		RuntimeService runtimeService = processEngine.getRuntimeService();
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("helloworld-id");
		System.out.println("=====activitiID:"+processInstance.getActivityId() + "====definitionID:" + processInstance.getProcessDefinitionId());
				
	}
	
	
	@Test
	public void startProcess2() throws Exception{
		// 创建一个使用H2内存数据库的流程处理引擎，默认连接url为jdbc:h2:mem:activiti
		ProcessEngine processEngine = ProcessEngineConfiguration
				.createStandaloneInMemProcessEngineConfiguration()
				.buildProcessEngine();
		// ProcessEngine一共有7个XxxService对象，
		RepositoryService repositoryService = processEngine.getRepositoryService();
		repositoryService.createDeployment().addClasspathResource("diagrams/helloworld.bpmn").deploy();
		
		//XxxService.createXxxQuery()方法创建查询对象，查询对应的<process>标签定义
		ModelQuery modelQuery = repositoryService.createModelQuery();
		System.out.println(modelQuery.desc());
		// IdentityService跟用户认证有关的接口
		processEngine.getIdentityService().createUserQuery();
	}
	
}
