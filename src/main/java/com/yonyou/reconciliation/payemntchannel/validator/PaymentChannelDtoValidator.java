package com.yonyou.reconciliation.payemntchannel.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.yonyou.reconciliation.payemntchannel.dto.PaymentChannelDto;
import com.yonyou.reconciliation.payemntchannel.entity.PaymentChannel;
import com.yonyou.reconciliation.valitation.utils.ValidatorUtils;

@Component
public class PaymentChannelDtoValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return PaymentChannelDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidatorUtils.validateEmpty(errors, target, "channelName", "channelCode");
		ValidatorUtils.validateUnique(errors, target, PaymentChannel.class, "id", "channelCode");
	}

}
