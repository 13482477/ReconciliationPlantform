package com.yonyou.reconciliation.task.listener;

import java.util.Date;
import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yonyou.reconciliation.task.entity.Task;
import com.yonyou.reconciliation.task.entity.TaskAttribute;
import com.yonyou.reconciliation.task.entity.TaskStatus;
import com.yonyou.reconciliation.task.service.TaskAttributeService;
import com.yonyou.reconciliation.task.service.TaskService;
import com.yonyou.reconciliation.taskinstance.entity.TaskAttributeInstance;
import com.yonyou.reconciliation.taskinstance.entity.TaskInstance;
import com.yonyou.reconciliation.taskinstance.entity.TaskInstanceStatus;
import com.yonyou.reconciliation.taskinstance.service.TaskInstanceService;

@Component
public class GlobalTaskListener implements JobListener {
	
	private static Logger LOGGER = LoggerFactory.getLogger(GlobalTaskListener.class);

	private static final String TASK_ID = "taskId";
	
	private static final String TASK_INSTANCE_ID = "taskInstanceId";

	@Autowired
	private TaskService taskService;
	
	@Autowired
	private TaskAttributeService taskAttributeService;
	
	@Autowired
	private TaskInstanceService taskInstanceService;
	
	@Override
	public String getName() {
		return "GlobalTaskListener";
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
		LOGGER.info("jobToBeExecuted");
		Long taskId = (Long) context.getJobDetail().getJobDataMap().getLong(TASK_ID);
		
		Task task = this.taskService.findOne(taskId);
		
		TaskInstance taskInstance = this.generateTaskInstance(task);
		this.taskInstanceService.save(taskInstance);
		context.put(TASK_INSTANCE_ID, taskInstance.getId());

		this.taskService.updateTaskStatusById(TaskStatus.RUNNING, taskId);
		context.put(TASK_ID, task.getId());
	}
	
	private TaskInstance generateTaskInstance(Task task) {
		TaskInstance taskInstance = new TaskInstance();
		taskInstance.setTaskName(task.getTaskName());
		taskInstance.setTaskGroup(task.getTaskGroup());
		taskInstance.setTaskInstanceStatus(TaskInstanceStatus.RUNNING);
		taskInstance.setTask(task);
		taskInstance.setCreateDate(new Date());
		taskInstance.setExecuteDate(new Date());
		
		List<TaskAttribute> taskAttributes = this.taskAttributeService.findByTask(task);
		
		for (TaskAttribute taskAttribute : taskAttributes) {
			TaskAttributeInstance taskAttributeInstance = this.generateTaskAttributeInstance(taskAttribute, taskInstance);
			taskInstance.getTaskAttributeInstances().add(taskAttributeInstance);
		}
		
		return taskInstance;
	}
	
	private TaskAttributeInstance generateTaskAttributeInstance(TaskAttribute taskAttribute, TaskInstance taskInstance) {
		TaskAttributeInstance taskAttributeInstance = new TaskAttributeInstance();
		taskAttributeInstance.setAttributeName(taskAttribute.getAttributeName());
		taskAttributeInstance.setValue(taskAttribute.getViewName());
		taskAttributeInstance.setDataType(taskAttribute.getDataType());
		taskAttributeInstance.setTaskAttribute(taskAttribute);
		taskAttributeInstance.setTaskInstance(taskInstance);
		taskAttributeInstance.setSequence(taskAttribute.getSequence());
		return taskAttributeInstance;
	}
	

	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
		LOGGER.info("jobExecutionVetoed");
	}

	@Override
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		LOGGER.info("jobWasExecuted");
		
		Long taskId = (Long) context.get(TASK_ID);
		Long taskInstanceId = (Long) context.get(TASK_INSTANCE_ID);
		
		this.taskInstanceService.updateTaskInstanceStatusById(jobException == null ? TaskInstanceStatus.FINISHED : TaskInstanceStatus.FAILED, taskInstanceId);
		this.taskInstanceService.updateFinishedDateById(jobException == null ? new Date() : null, taskInstanceId);
		
		this.taskService.updateTaskStatusById(TaskStatus.PENDDING, taskId);
	}

}
