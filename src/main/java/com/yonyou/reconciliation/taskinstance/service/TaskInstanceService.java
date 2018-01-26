package com.yonyou.reconciliation.taskinstance.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.reconciliation.taskinstance.entity.TaskInstance;
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

}
