package com.perscholas.scorekeeper.controller;

import com.perscholas.scorekeeper.dao.RulesetDAO;
import com.perscholas.scorekeeper.entity.Ruleset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class GameController {
	@Autowired
	RulesetDAO rulesetDAO;

	@GetMapping("create-game")
	public ModelAndView createGame(){
		ModelAndView response = new ModelAndView();
		List<Ruleset> rulesets = rulesetDAO.findAll();
		response.addObject("rulesetList", rulesets);
		return response;
	}

	@GetMapping("create-game/submit")
	public ModelAndView createGameSubmit(){
		ModelAndView response = new ModelAndView();
		return response;
	}

	@GetMapping("game")
	public ModelAndView game(){
		ModelAndView response = new ModelAndView();
		return response;
	}
}
