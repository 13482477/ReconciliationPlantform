package com.yonyou.reconciliation.systemsituation.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.reconciliation.systemsituation.entity.SystemSituation;
import com.yonyou.reconciliation.systemsituation.repository.SystemSituationRepository;

@Service
public class SystemSituationService {
	
	@Autowired
	private SystemSituationRepository systemSituationRepository;
	
	@Transactional
	public void saveOrUpdate(SystemSituation entity) {
		this.systemSituationRepository.saveAndFlush(entity);
	}
	

}
