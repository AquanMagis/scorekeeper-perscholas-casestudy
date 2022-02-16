package com.perscholas.scorekeeper.controller;

import com.perscholas.scorekeeper.dao.PlayerDAO;
import com.perscholas.scorekeeper.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegisterController {
	@Autowired
	private PlayerDAO playerDAO;

	@GetMapping("register")
	public ModelAndView register(){
		ModelAndView response = new ModelAndView();
		return response;
	}

	@PostMapping("register-submit")
	public ModelAndView registerSubmit(@ModelAttribute("newUser")Player newUser){
		ModelAndView response = new ModelAndView();
		System.out.println(newUser.getUsername());
		response.setViewName("register");
		System.out.println(playerDAO);
		playerDAO.save(newUser);
		return response;
	}
}