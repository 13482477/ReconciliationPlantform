package com.yonyou.reconciliation.taskinstance.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.reconciliation.taskinstance.dto.TaskInstanceDto;
import com.yonyou.reconciliation.taskinstance.entity.TaskInstance;
import com.yonyou.reconciliation.taskinstance.entity.TaskInstanceStatus;
import com.yonyou.reconciliation.taskinstance.service.TaskInstanceService;

@Controller
public class TaskInstanceController {

	@Autowired
	private TaskInstanceService taskInstanceService;

	@RequestMapping(value = "/taskInstance/page", method = RequestMethod.GET)
	public String renderPage() {
		return "taskInstance/taskInstance";
	}

	@RequestMapping(value = "/taskInstances", method = RequestMethod.GET)
	@ResponseBody
	public Page<TaskInstanceDto> find(@RequestParam(required = false) String taskName, @RequestParam(required = false) String taskGroup,
			@RequestParam(required = false) TaskInstanceStatus taskInstanceStatus, @RequestParam(required = false) Date createDateStart, @RequestParam(required = false) Date createDateEnd,
			@RequestParam(required = false) Date executeDateStart, @RequestParam(required = false) Date executeDateEnd, @RequestParam(required = false) Date finishedDateStart,
			@RequestParam(required = false) Date finishedDateEnd, Pageable pageable) {

		Page<TaskInstance> result = this.taskInstanceService.find(taskName, taskGroup, taskInstanceStatus, createDateStart, createDateEnd, executeDateStart, executeDateEnd, finishedDateStart,
				finishedDateEnd, pageable);

		return result.map(input -> {
			TaskInstanceDto taskInstanceDto = new TaskInstanceDto();
			taskInstanceDto.setId(input.getId());
			taskInstanceDto.setTaskName(input.getTaskName());
			taskInstanceDto.setTaskGroup(input.getTaskGroup());
			taskInstanceDto.setCreateDate(input.getCreateDate());
			taskInstanceDto.setExecuteDate(input.getExecuteDate());
			taskInstanceDto.setFinishedDate(input.getFinishedDate());
			return taskInstanceDto;
		});
	}

}
