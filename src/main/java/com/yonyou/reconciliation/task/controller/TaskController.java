package com.yonyou.reconciliation.task.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.reconciliation.task.dto.TaskDto;
import com.yonyou.reconciliation.task.entity.Task;
import com.yonyou.reconciliation.task.entity.TaskStatus;
import com.yonyou.reconciliation.task.repository.TaskRepository;
import com.yonyou.reconciliation.task.service.TaskService;
import com.yonyou.reconciliation.task.validator.TaskDtoValidator;
import com.yonyou.reconciliation.user.dto.UserDto;
import com.yonyou.reconciliation.web.convert.ConvertUtils;
import com.yonyou.reconciliation.web.validator.field.FieldValidationResult;

@Controller
public class TaskController {

	@Autowired
	private TaskService taskService;

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private TaskDtoValidator taskDtoValidator;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(this.taskDtoValidator);
	}

	@RequestMapping(value = "/task/page", method = RequestMethod.GET)
	public String renderPage() {
		return "task/task";
	}

	@RequestMapping(value = "/task/validation", method = RequestMethod.GET)
	@ResponseBody
	public FieldValidationResult validateField(TaskDto taskDto) {
		FieldValidationResult result = new FieldValidationResult();
		// result.setValid(ValidatorUtils.evaluateUnique(taskDto, Task.class, "id",
		// "username"));
		return result;
	}

	@RequestMapping(value = "/task", method = RequestMethod.POST)
	@ResponseBody
	public TaskDto create(@RequestBody @Validated TaskDto taskDto) {
		Task task = taskDto.createEntity();

		this.taskService.save(task);
		return ConvertUtils.convert(task, input -> {
			TaskDto result = new TaskDto();
			BeanUtils.copyProperties(input, result);
			return result;
		});
	}

	@RequestMapping(value = "/task/{id}", method = RequestMethod.GET)
	@ResponseBody
	public TaskDto read(@PathVariable Long id) {
		Task task = this.taskRepository.findOne(id);
		return ConvertUtils.convert(task, input -> {
			TaskDto result = new TaskDto();
			BeanUtils.copyProperties(input, result);
			return result;
		});
	}

	@RequestMapping(value = "/task/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public TaskDto update(@PathVariable("id") Long id, @RequestBody @Validated UserDto target) {
		Task task = this.taskRepository.findOne(id);
		BeanUtils.copyProperties(target, task);

		this.taskService.update(task);
		return ConvertUtils.convert(task, input -> {
			TaskDto result = new TaskDto();
			BeanUtils.copyProperties(input, result);
			return result;
		});
	}

	@RequestMapping(value = "/task/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void delete(@PathVariable("id") Long id) {
		this.taskService.delete(id);
	}

	@RequestMapping(value = "/tasks", method = RequestMethod.GET)
	@ResponseBody
	public Page<TaskDto> find(@RequestParam(required = false) String cron, @RequestParam(required = false) TaskStatus taskStatus, @RequestParam(required = false) String taskName,
			@RequestParam(required = false) String taskGroup, Pageable pageable) {
		Page<Task> result = this.taskService.find(cron, taskStatus, taskName, taskGroup, pageable);
		return result.map(input -> {
			TaskDto taskDto = new TaskDto();
			BeanUtils.copyProperties(input, taskDto);
			return taskDto;
		});
	}

}
