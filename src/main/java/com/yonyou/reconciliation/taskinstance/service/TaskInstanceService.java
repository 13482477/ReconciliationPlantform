package com.yonyou.reconciliation.taskinstance.service;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.reconciliation.taskinstance.entity.TaskInstance;
import com.yonyou.reconciliation.taskinstance.entity.TaskInstanceStatus;
import com.yonyou.reconciliation.taskinstance.repository.TaskInstanceRepository;

@Service
public class TaskInstanceService {

	@Autowired
	private TaskInstanceRepository taskInstanceRepository;

	@Transactional
	public void save(TaskInstance taskInstance) {
		this.taskInstanceRepository.save(taskInstance);
	}
	
	@Transactional
	public void update(TaskInstance taskInstance) {
		this.taskInstanceRepository.saveAndFlush(taskInstance);
	}
	
	@Transactional
	public TaskInstance findOne(Long id) {
		return this.taskInstanceRepository.findOne(id);
	}
	
	@Transactional
	public void updateTaskInstanceStatusById(TaskInstanceStatus taskInstanceStatus, Long id) {
		TaskInstance taskInstance = this.taskInstanceRepository.findOne(id);
		taskInstance.setTaskInstanceStatus(taskInstanceStatus);
		this.taskInstanceRepository.saveAndFlush(taskInstance);
	}
	
	@Transactional
	public void updateFinishedDateById(Date date, Long id) {
		TaskInstance taskInstance = this.taskInstanceRepository.findOne(id);
		taskInstance.setFinishedDate(date);
		this.taskInstanceRepository.saveAndFlush(taskInstance);
	}
	

}
