package com.yonyou.reconciliation.etl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class EtlController {
	
	@RequestMapping(value = "/etl/page", method = RequestMethod.GET)
	public String renderPage() {
		return "etl/etl";
	}
	
	@RequestMapping(value = "/etl/create/page", method = RequestMethod.GET)
	public String renderEtlCreatePage() {
		return "etl/etlCreate";
	}
	

}
