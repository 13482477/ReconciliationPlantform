package com.yonyou.reconciliation.requestmapping.service;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.yonyou.reconciliation.Application;
import com.yonyou.reconciliation.requestmapping.dto.RequestMapping;
import com.yonyou.reconciliation.requestmapping.service.RequestMappingService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ResourceUrlServiceTest {
	
	@Autowired
	private RequestMappingService resourceUrlService;
	
	@Test
	public void extractRequestMappingsFromWebApplicationContextTest() {
		List<RequestMapping> requestMappings = this.resourceUrlService.findRequestMappings("");
		Assert.assertTrue(CollectionUtils.isNotEmpty(requestMappings));
	}

}
