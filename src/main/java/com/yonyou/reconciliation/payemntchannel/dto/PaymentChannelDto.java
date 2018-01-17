package com.yonyou.reconciliation.payemntchannel.dto;

import com.yonyou.reconciliation.payemntchannel.entity.PaymentChannel;

public class PaymentChannelDto {

	private Long id;

	private String channelName;

	private String channelCode;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public PaymentChannel createEntity() {
		PaymentChannel paymentChannel = new PaymentChannel();
		paymentChannel.setId(this.id);
		paymentChannel.setChannelName(this.channelName);
		paymentChannel.setChannelCode(this.channelCode);
		return paymentChannel;
	}

}
