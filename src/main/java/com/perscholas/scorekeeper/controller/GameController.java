package com.perscholas.scorekeeper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GameController {
	@GetMapping("create-game")
	public ModelAndView createGame(){
		ModelAndView response = new ModelAndView();
		return response;
	}

	@GetMapping("game")
	public ModelAndView game(){
		ModelAndView response = new ModelAndView();
		return response;
	}
}
