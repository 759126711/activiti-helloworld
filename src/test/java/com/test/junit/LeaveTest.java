package com.test.junit;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test; 
//extends TestCase 
public class LeaveTest {

	@Test
	public void testLeave() throws Exception {
		 
		ProcessEngine processEngine = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration()
				.buildProcessEngine();
 
		RepositoryService repositoryService = processEngine.getRepositoryService();
		repositoryService.createDeployment().addClasspathResource("diagrams/leave.bpmn").deploy();
		RuntimeService runtimeService = processEngine.getRuntimeService();
		
		Map<String, Object> var = new HashMap<>();
		var.put("applyUser", "employee01");
		var.put("day", 3);
		
		runtimeService.startProcessInstanceByKey("leave-id", var);
 
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().singleResult();
		
		System.out.println("=====activitiID:" + processInstance.getActivityId() + "====definitionID:"
				+ processInstance.getProcessDefinitionId());
 
		TaskService taskService = processEngine.getTaskService();
		Task approveTask = taskService.createTaskQuery()
				.taskCandidateGroup("deptLeader")
				.singleResult(); 
		System.out.println("formkey:" + approveTask.getFormKey());
		
		taskService.claim(approveTask.getId(), "leaderUser");
		
		//审批结果作为脚本任务的输入值
		Map<String, Object> resultData = new HashMap<>();
		resultData.put("approved", true);
		taskService.complete(approveTask.getId(), resultData);
		
		// 这里在任务执行完成后，再查询一次得到的结果会是null
		approveTask = taskService.createTaskQuery()
				.taskCandidateGroup("deptLeader")  // 这里就是对应activiti:candidateGroups属性的值
				.singleResult(); 
	
		HistoryService historyService = processEngine.getHistoryService();
		// 获取已经完成的流程的数量
		long count = historyService.createHistoricProcessInstanceQuery().count();
		System.out.println("=========count:"+count);
	 
		//ModelQuery modelQuery = repositoryService.createModelQuery();
		//System.out.println(modelQuery.desc());
		//processEngine.getIdentityService().createUserQuery();
	}
}
