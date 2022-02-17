package com.perscholas.scorekeeper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DashboardController {
	// TODO: Use POST
	@GetMapping("dashboard")
	public ModelAndView dashboard(){
		ModelAndView response = new ModelAndView("dashboard");
		return response;
	}
}
