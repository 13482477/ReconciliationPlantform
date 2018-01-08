package com.yonyou.reconciliation.index.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {
	
	@RequestMapping(value = "/index/page", method = RequestMethod.GET)
	public String index(HttpServletRequest request, Model model) {
		return "index/index";
	}

}
