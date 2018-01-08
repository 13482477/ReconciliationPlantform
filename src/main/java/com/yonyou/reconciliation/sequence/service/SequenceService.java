package com.yonyou.reconciliation.sequence.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.reconciliation.sequence.entity.Sequence;
import com.yonyou.reconciliation.sequence.jpa.SequenceRepository;

@Service
public class SequenceService {
	
	@Autowired
	private SequenceRepository sequenceRepository;
	
	@Transactional
	public Sequence createSequence(String sequenceName) {
		return this.createSequence(sequenceName, null, null);
	}

	@Transactional
	public Sequence createSequence(String sequenceName, Integer step) {
		return this.createSequence(sequenceName, step, null);
	}
	
	@Transactional
	public Sequence createSequence(String sequenceName, Integer step, Long currentValue) {
		Sequence sequence = new Sequence();
		sequence.setSequenceName(sequenceName);
		sequence.setStep(step == null ? 1 : step);
		sequence.setCurrentValue(currentValue == null ? 0 : currentValue);
		return this.sequenceRepository.save(sequence);
	}
	
	@Transactional
	public Long getNextValue(String sequenceName) {
		this.sequenceRepository.updateCurrentValue(sequenceName);
		return this.sequenceRepository.getCurrentValue(sequenceName);
	}

}
