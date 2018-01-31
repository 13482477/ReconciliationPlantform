package com.yonyou.reconciliation.taskinstance.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.yonyou.reconciliation.taskinstance.entity.TaskInstanceStatus;

public class TaskInstanceDto {
	
	private Long id;
	
	private String taskName;
	
	private String taskGroup;
	
	private TaskInstanceStatus taskInstanceStatus;
	
	private Date createDate;
	
	private Date executeDate;
	
	private Date finishedDate;
	
	private List<TaskAttributeInstanceDto> taskAttributeInstances = new ArrayList<TaskAttributeInstanceDto>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskGroup() {
		return taskGroup;
	}

	public void setTaskGroup(String taskGroup) {
		this.taskGroup = taskGroup;
	}

	public TaskInstanceStatus getTaskInstanceStatus() {
		return taskInstanceStatus;
	}

	public void setTaskInstanceStatus(TaskInstanceStatus taskInstanceStatus) {
		this.taskInstanceStatus = taskInstanceStatus;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getExecuteDate() {
		return executeDate;
	}

	public void setExecuteDate(Date executeDate) {
		this.executeDate = executeDate;
	}

	public Date getFinishedDate() {
		return finishedDate;
	}

	public void setFinishedDate(Date finishedDate) {
		this.finishedDate = finishedDate;
	}

	public List<TaskAttributeInstanceDto> getTaskAttributeInstances() {
		return taskAttributeInstances;
	}

	public void setTaskAttributeInstances(List<TaskAttributeInstanceDto> taskAttributeInstances) {
		this.taskAttributeInstances = taskAttributeInstances;
	}

}
