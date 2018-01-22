package com.yonyou.reconciliation.task.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.yonyou.reconciliation.task.dto.TaskDto;

@Component
public class TaskDtoValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return TaskDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
	}

}
