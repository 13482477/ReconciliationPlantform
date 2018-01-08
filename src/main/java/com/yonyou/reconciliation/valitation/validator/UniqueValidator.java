package com.yonyou.reconciliation.valitation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.yonyou.reconciliation.valitation.annotation.Unique;

public class UniqueValidator implements ConstraintValidator<Unique, String> {
	
	private Class<?> target;
	
	private String fieldName;
	
	@Override
	public void initialize(Unique constraintAnnotation) {
		
		
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return false;
	}

}
