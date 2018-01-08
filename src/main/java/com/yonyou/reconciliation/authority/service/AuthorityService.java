package com.yonyou.reconciliation.authority.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.reconciliation.authority.entity.Authority;
import com.yonyou.reconciliation.authority.repository.AuthorityRepository;
import com.yonyou.reconciliation.resource.entity.Resource;
import com.yonyou.reconciliation.resource.repository.ResourceRepository;

@Service
public class AuthorityService {
	
	@Autowired
	private AuthorityRepository authorityRepository;
	
	@Autowired
	private ResourceRepository resourceRepository;
	

}
