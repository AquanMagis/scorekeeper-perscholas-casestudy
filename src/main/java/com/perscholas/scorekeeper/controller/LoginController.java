package com.perscholas.scorekeeper.controller;

import com.perscholas.scorekeeper.dao.PlayerDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
	@Autowired
	PlayerDAO playerDAO;

	@GetMapping("login")
	public ModelAndView login(){
		ModelAndView response = new ModelAndView();
		return response;
	}

	// TODO: This is placeholder for now. Implement later.
	@PostMapping("login-submit")
	public ModelAndView loginSubmit(){
		ModelAndView response = new ModelAndView("login");
		return response;
	}
}
