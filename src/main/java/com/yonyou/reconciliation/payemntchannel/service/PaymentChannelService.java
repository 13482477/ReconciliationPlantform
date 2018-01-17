package com.yonyou.reconciliation.payemntchannel.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.yonyou.reconciliation.payemntchannel.entity.PaymentChannel;
import com.yonyou.reconciliation.payemntchannel.repository.PaymentChannelRepository;

@Service
public class PaymentChannelService {

	@Autowired
	private PaymentChannelRepository paymentChannelRepository;

	@Transactional
	public void save(PaymentChannel paymentChannel) {
		this.paymentChannelRepository.save(paymentChannel);
	}

	@Transactional
	public void update(PaymentChannel paymentChannel) {
		this.paymentChannelRepository.saveAndFlush(paymentChannel);
	}

	@Transactional
	public void delete(long id) {
		this.paymentChannelRepository.delete(id);
	}

	public Page<PaymentChannel> find(String channelName, String channelCode, Pageable pageable) {
		return this.paymentChannelRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<Predicate>();

			if (StringUtils.isNotBlank(channelName)) {
				predicates.add(criteriaBuilder.equal(root.get("channelName"), channelName));
			}

			if (StringUtils.isNotBlank(channelCode)) {
				predicates.add(criteriaBuilder.equal(root.get("channelCode"), channelCode));
			}
			return criteriaBuilder.and(predicates.toArray(new Predicate[] {}));
		}, pageable);
	}

}
