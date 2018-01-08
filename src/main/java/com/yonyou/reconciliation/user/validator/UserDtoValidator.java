package com.yonyou.reconciliation.user.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.yonyou.reconciliation.user.dto.UserDto;
import com.yonyou.reconciliation.user.entity.User;
import com.yonyou.reconciliation.valitation.utils.ValidatorUtils;

@Component
public class UserDtoValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return UserDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidatorUtils.validateEmpty(errors, target, "username", "email", "mobile", "nickname");
		ValidatorUtils.validateUnique(errors, target, User.class, "id", "username");
	}

}
