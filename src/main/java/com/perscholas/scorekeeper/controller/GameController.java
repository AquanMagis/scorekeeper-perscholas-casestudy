package com.perscholas.scorekeeper.controller;

import com.google.gson.Gson;
import com.perscholas.scorekeeper.dao.GameDAO;
import com.perscholas.scorekeeper.dao.RulesetDAO;
import com.perscholas.scorekeeper.entity.Game;
import com.perscholas.scorekeeper.entity.Ruleset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

@Controller
public class GameController {
	@Autowired
	RulesetDAO rulesetDAO;
	@Autowired
	GameDAO gameDAO;

	@GetMapping("create-game")
	public ModelAndView createGame(){
		ModelAndView response = new ModelAndView();
		List<Ruleset> rulesets = rulesetDAO.findAll();

		Gson gson = new Gson();
		System.out.println(gson.toJson(rulesets));
		List<String> rulesetJsons = Arrays.asList(rulesets.stream().map(gson::toJson).toArray(String[]::new));

		response.addObject("rulesetList", rulesets);
		response.addObject("rulesetJsons", rulesetJsons);
		return response;
	}

	@PostMapping("create-game/submit")
	public ModelAndView createGameSubmit(@ModelAttribute("game") Game game){
		ModelAndView response = new ModelAndView();
		Gson gson = new Gson();
		System.out.println(gson.toJson(game));

		response.addObject(game);
		response.setViewName("redirect:/game");
		gameDAO.save(game);
		return response;
	}

	@GetMapping("game")
	public ModelAndView game(){
		ModelAndView response = new ModelAndView();
		return response;
	}
}
