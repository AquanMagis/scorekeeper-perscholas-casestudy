package com.perscholas.scorekeeper.controller;

import com.perscholas.scorekeeper.dao.PlayerDAO;
import com.perscholas.scorekeeper.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RegisterController {
	@Autowired
	private PlayerDAO playerDAO;
	@Autowired
	private PasswordEncoder encoder;

	@GetMapping("register")
	public ModelAndView register(){
		ModelAndView response = new ModelAndView();
		return response;
	}

	@PostMapping("register/submit")
	public ModelAndView registerSubmit(HttpServletRequest request, @ModelAttribute("newUser")Player newUser){
		ModelAndView response = new ModelAndView();
		//System.out.println(newUser.getUsername());
		//response.setViewName("register");
		//System.out.println(playerDAO);

		//response.addObject("username", newUser.getUsername());
		//response.addObject("password", newUser.getPassword());

		String username = newUser.getUsername();
		String password = newUser.getPassword();

		newUser.setPassword(encoder.encode(password));
		playerDAO.save(newUser);

		try {
			request.login(username, password);
			System.out.println("Authenticating as " + username);
		}
		catch(Exception e){
			System.out.println("Authentication failed.");
		}

		response.setViewName("redirect:/game/create");
		return response;
	}

	@Bean
	public PasswordEncoder encoder(){
		return new BCryptPasswordEncoder();
	}
}