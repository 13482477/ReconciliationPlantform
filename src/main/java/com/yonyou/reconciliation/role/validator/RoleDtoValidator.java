package com.yonyou.reconciliation.role.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.yonyou.reconciliation.role.dto.RoleDto;
import com.yonyou.reconciliation.role.entity.Role;
import com.yonyou.reconciliation.valitation.utils.ValidatorUtils;

@Component
public class RoleDtoValidator implements Validator{
	
	@Override
	public boolean supports(Class<?> clazz) {
		return RoleDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidatorUtils.validateEmpty(errors, target, "roleName", "roleCode");
		ValidatorUtils.validateUnique(errors, target, Role.class, "id", "roleCode");
	}

}
