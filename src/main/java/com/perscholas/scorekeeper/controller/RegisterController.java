package com.perscholas.scorekeeper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegisterController {
	@GetMapping("register")
	public ModelAndView register(){
		ModelAndView response = new ModelAndView();
		return response;
	}
}
