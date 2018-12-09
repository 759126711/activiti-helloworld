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
		// ����һ��ʹ��H2�ڴ����ݿ�����̴������棬Ĭ������urlΪjdbc:h2:mem:activiti
		ProcessEngine processEngine = ProcessEngineConfiguration
				.createStandaloneInMemProcessEngineConfiguration()
				.buildProcessEngine();
		// ProcessEngineһ����7��XxxService����
		RepositoryService repositoryService = processEngine.getRepositoryService();
		repositoryService.createDeployment().addClasspathResource("diagrams/helloworld.bpmn").deploy();
		
		//XxxService.createXxxQuery()����������ѯ���󣬲�ѯ��Ӧ��<process>��ǩ����
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
				.singleResult();
		// id ��process��ǩ��id����ֵ����1:4����汾��
		System.out.println("=====key:"+processDefinition.getKey() + "====name:" +processDefinition.getName()+"=====id:"+processDefinition.getId());
		RuntimeService runtimeService = processEngine.getRuntimeService();
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("helloworld-id");
		System.out.println("=====activitiID:"+processInstance.getActivityId() + "====definitionID:" + processInstance.getProcessDefinitionId());
				
	}
	
	
	@Test
	public void startProcess2() throws Exception{
		// ����һ��ʹ��H2�ڴ����ݿ�����̴������棬Ĭ������urlΪjdbc:h2:mem:activiti
		ProcessEngine processEngine = ProcessEngineConfiguration
				.createStandaloneInMemProcessEngineConfiguration()
				.buildProcessEngine();
		// ProcessEngineһ����7��XxxService����
		RepositoryService repositoryService = processEngine.getRepositoryService();
		repositoryService.createDeployment().addClasspathResource("diagrams/helloworld.bpmn").deploy();
		
		//XxxService.createXxxQuery()����������ѯ���󣬲�ѯ��Ӧ��<process>��ǩ����
		ModelQuery modelQuery = repositoryService.createModelQuery();
		System.out.println(modelQuery.desc());
		// IdentityService���û���֤�йصĽӿ�
		processEngine.getIdentityService().createUserQuery();
	}
	
}
