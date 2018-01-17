package com.yonyou.reconciliation.payemntchannel.controller;

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

import com.yonyou.reconciliation.payemntchannel.dto.PaymentChannelDto;
import com.yonyou.reconciliation.payemntchannel.entity.PaymentChannel;
import com.yonyou.reconciliation.payemntchannel.repository.PaymentChannelRepository;
import com.yonyou.reconciliation.payemntchannel.service.PaymentChannelService;
import com.yonyou.reconciliation.payemntchannel.validator.PaymentChannelDtoValidator;
import com.yonyou.reconciliation.user.entity.User;
import com.yonyou.reconciliation.valitation.utils.ValidatorUtils;
import com.yonyou.reconciliation.web.convert.ConvertUtils;
import com.yonyou.reconciliation.web.validator.field.FieldValidationResult;

@Controller
public class PaymentChannelController {

	@Autowired
	private PaymentChannelService paymentChannelService;

	@Autowired
	private PaymentChannelRepository paymentChannelRepository;

	@Autowired
	private PaymentChannelDtoValidator paymentChannelDtoValidator;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(this.paymentChannelDtoValidator);
	}

	@RequestMapping(value = "/paymentChannel/page", method = RequestMethod.GET)
	public String renderPage() {
		return "paymentChannel/paymentChannel";
	}

	@RequestMapping(value = "/paymentChannel/validation", method = RequestMethod.GET)
	@ResponseBody
	public FieldValidationResult validateField(PaymentChannelDto paymentChannelDto) {
		FieldValidationResult result = new FieldValidationResult();
		result.setValid(ValidatorUtils.evaluateUnique(paymentChannelDto, User.class, "id", "channelCode"));
		return result;
	}

	@RequestMapping(value = "/paymentChannel", method = RequestMethod.POST)
	@ResponseBody
	public PaymentChannelDto create(@RequestBody @Validated PaymentChannelDto paymentChannelDto) {
		PaymentChannel paymentChannel = paymentChannelDto.createEntity();

		this.paymentChannelService.save(paymentChannel);
		return ConvertUtils.convert(paymentChannel, input -> {
			PaymentChannelDto result = new PaymentChannelDto();
			return result;
		});
	}

	@RequestMapping(value = "/paymentChannel/{id}", method = RequestMethod.GET)
	@ResponseBody
	public PaymentChannelDto read(@PathVariable Long id) {
		PaymentChannel paymentChannel = this.paymentChannelRepository.findOne(id);
		return ConvertUtils.convert(paymentChannel, input -> {
			PaymentChannelDto result = new PaymentChannelDto();
			BeanUtils.copyProperties(input, result);
			return result;
		});
	}

	@RequestMapping(value = "/paymentChannel/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public PaymentChannelDto update(@PathVariable("id") Long id, @RequestBody @Validated PaymentChannelDto target) {
		PaymentChannel paymentChannel = this.paymentChannelRepository.findOne(id);
		return ConvertUtils.convert(paymentChannel, input -> {
			PaymentChannelDto result = new PaymentChannelDto();
			return result;
		});
	}

	@RequestMapping(value = "/paymentChannel/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void delete(@PathVariable("id") Long id) {
		this.paymentChannelService.delete(id);
	}

	@RequestMapping(value = "/paymentChannels", method = RequestMethod.GET)
	@ResponseBody
	public Page<PaymentChannelDto> find(@RequestParam(required = false) String channelName, @RequestParam(required = false) String channelCode, Pageable pageable) {
		Page<PaymentChannel> result = this.paymentChannelService.find(channelName, channelCode, pageable);
		return result.map(input -> {
			PaymentChannelDto paymentChannelDto = new PaymentChannelDto();
			BeanUtils.copyProperties(input, paymentChannelDto);
			return paymentChannelDto;
		});
	}

}
