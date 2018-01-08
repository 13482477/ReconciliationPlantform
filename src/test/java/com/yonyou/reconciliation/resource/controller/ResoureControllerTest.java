package com.yonyou.reconciliation.resource.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.yonyou.reconciliation.Application;
import com.yonyou.reconciliation.resource.controller.ResourceController;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ResoureControllerTest {
	
	private static Log LOGGER = LogFactory.getLog(ResourceController.class);

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mvc;

	@Before
	public void setup() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	@WithUserDetails(value = "admin", userDetailsServiceBeanName="userDetailsService")
	public void loadResourceNodesTest() throws Exception {
		String responseString = this.mvc
			.perform(
					get("/resource")
					.param("parentId", "")
					.accept(MediaType.APPLICATION_JSON_UTF8)
					)
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andDo(print())
			.andReturn().getResponse().getContentAsString();
		
		LOGGER.info(responseString);
	}

}
