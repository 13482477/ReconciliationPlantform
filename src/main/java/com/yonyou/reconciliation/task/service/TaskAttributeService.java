package com.yonyou.reconciliation.task.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.reconciliation.task.entity.Task;
import com.yonyou.reconciliation.task.entity.TaskAttribute;
import com.yonyou.reconciliation.task.repository.TaskAttributeRepository;

@Service
public class TaskAttributeService {
	
	@Autowired
	private TaskAttributeRepository taskAttributeRepository;
	
	@Transactional
	public List<TaskAttribute> findByTask(Task task) {
		return taskAttributeRepository.findByTask(task);
	}

}
