package com.perscholas.scorekeeper.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.perscholas.scorekeeper.dao.GameDAO;
import com.perscholas.scorekeeper.dao.PlayerDAO;
import com.perscholas.scorekeeper.dao.RulesetDAO;
import com.perscholas.scorekeeper.entity.Game;
import com.perscholas.scorekeeper.entity.Player;
import com.perscholas.scorekeeper.entity.Ruleset;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class GameController {
	@Autowired
	RulesetDAO rulesetDAO;
	@Autowired
	GameDAO gameDAO;
	@Autowired
	PlayerDAO playerDAO;
	@Autowired
	Gson gson;

	@GetMapping("create-game")
	public ModelAndView createGame(){
		ModelAndView response = new ModelAndView();
		List<Ruleset> rulesets = rulesetDAO.findAll();

		Gson gson = new Gson();
		//System.out.println(gson.toJson(rulesets));
		List<String> rulesetJsons = Arrays.asList(rulesets.stream().map(gson::toJson).toArray(String[]::new));

		response.addObject("rulesetList", rulesets);
		response.addObject("rulesetJsons", rulesetJsons);
		return response;
	}

	//TODO: This exposes passwords.  Make it not expose passwords.
	@GetMapping("create-game/submit")
	public ModelAndView createGameSubmit(@ModelAttribute("game") Game game){
		ModelAndView response = new ModelAndView();
		//Gson gson = new Gson();
		//System.out.println(gson.toJson(game));

		//Set up dummy players.
		Player p1 = playerDAO.findById(1);
		Player p2 = playerDAO.findById(2);
		Player p3 = playerDAO.findById(3);
		Player p4 = playerDAO.findById(4);

		game.initializePlayerList(Arrays.asList(p1, p2, p3, p4));

		response.setViewName("redirect:/game");
		gameDAO.save(game);

		response.addObject("game", game.getId());
		return response;
	}

	@GetMapping("game")
	public ModelAndView gameWithId(@RequestParam("game") long gameId) {
		ModelAndView response = new ModelAndView();

		Game game = Hibernate.unproxy(gameDAO.getById(gameId), Game.class);
		for(Player p: game.getPlayers()){
			p.setPassword(null);
		}
		String gameJson = gson.toJson(game);

		response.addObject("game", game);
		response.addObject("gameJson", gameJson);
		response.addObject("players", game.getPlayers());
		return response;
	}
}
