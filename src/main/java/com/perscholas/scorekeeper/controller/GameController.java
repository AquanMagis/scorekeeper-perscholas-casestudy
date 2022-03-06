package com.perscholas.scorekeeper.controller;

import com.google.gson.Gson;
import com.perscholas.scorekeeper.dao.*;
import com.perscholas.scorekeeper.entity.*;
import com.perscholas.scorekeeper.form.DrawForm;
import com.perscholas.scorekeeper.form.RonForm;
import com.perscholas.scorekeeper.form.TsumoForm;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Controller
public class GameController {
	public final static String SESSION_ID = "playerId";

	@Autowired
	RulesetDAO rulesetDAO;
	@Autowired
	GameDAO gameDAO;
	@Autowired
	PlayerDAO playerDAO;
	@Autowired
	RoundDAO roundDAO;
	@Autowired
	HandDAO handDAO;
	@Autowired
	Gson gson;

	@GetMapping("create-game")
	public ModelAndView createGame(HttpSession session){
		ModelAndView response = new ModelAndView();
		List<Ruleset> rulesets = rulesetDAO.findAll();

		Gson gson = new Gson();
		//System.out.println(gson.toJson(rulesets));
		List<String> rulesetJsons = Arrays.asList(rulesets.stream().map(gson::toJson).toArray(String[]::new));

		response.addObject("rulesetList", rulesets);
		response.addObject("rulesetJsons", rulesetJsons);

		//TODO: Remove this and add actual session management.
		session.setAttribute(SESSION_ID, (long)4);
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
	public ModelAndView gameWithId(@RequestParam("game") long gameId, HttpSession session) {
		Game game = gameDAO.findById(gameId);
		ModelAndView response = new ModelAndView();
		Player currentUser = game.getPlayerById((Long)session.getAttribute(SESSION_ID));
		//System.out.println(currentUser);

		if(currentUser == null) {
			return new ModelAndView("error");
		}


		//System.out.println(game.getPlayersFromPerspective(currentUser).get(0));
		for(Player p: game.getPlayers()){
			p.setPassword(null);
		}
		//String gameJson = gson.toJson(game);
		TsumoForm tsumoForm = new TsumoForm();
		RonForm ronForm = new RonForm();
		DrawForm drawForm = new DrawForm();

		response.addObject("game", game);
		//response.addObject("gameJson", gameJson);
		response.addObject("players", game.getPlayersFromPerspective(currentUser));
		response.addObject("tsumoForm", tsumoForm);
		response.addObject("ronForm", ronForm);
		response.addObject("drawForm", drawForm);
		return response;
	}

	@PostMapping("game/tsumo-submit")
	public ModelAndView submitTsumo(@ModelAttribute("tsumoForm") TsumoForm tsumoForm){
		ModelAndView response = new ModelAndView();

		List<Player> inRiichi = createPlayerListFromIds(tsumoForm.getInRiichi());
		//System.out.println(tsumoForm.getFu() + "/" + tsumoForm.getHan());

		Player winner = playerDAO.findById(tsumoForm.getWinnerId());
		int fu = tsumoForm.getFu();
		int han = tsumoForm.getHan();

		Hand hand = new Hand(winner, han, fu);
		handDAO.save(hand);

		Round round = new Round(hand, inRiichi.toArray(Player[]::new));
		roundDAO.save(round);

		long gameId = tsumoForm.getGameId();
		Game game = gameDAO.findById(gameId);
		game.addRound(round);
		gameDAO.save(game);


		response.setViewName("redirect:/game?game=" + gameId);
		return response;
	}

	@PostMapping("game/ron-submit")
	public ModelAndView submitRon(@ModelAttribute("ronForm") RonForm ronForm){
		ModelAndView response = new ModelAndView();
		long gameId = ronForm.getGameId();
		List<Player> inRiichi = createPlayerListFromIds(ronForm.getInRiichi());
		Player loser = playerDAO.getById(ronForm.getLoserId());

		/*System.out.println(ronForm.getLoserId());
		System.out.println(ronForm.getWinnerIds()[0]);
		System.out.println(ronForm.getFu()[0]);
		System.out.println(ronForm.getHan()[0]);*/

		List<Hand> handList = new LinkedList<>();

		for(int i = 0; i < RonForm.ARRAY_LENGTH; i++){
			Long playerId = ronForm.getWinnerIds()[i];
			Integer han = ronForm.getHan()[i];
			Integer fu = ronForm.getFu()[i];

			if(playerId != null && han != null && fu != null) {
				Player player = playerDAO.findById((long)playerId);

				Hand hand = new Hand(player, han, fu);
				handDAO.save(hand);
				handList.add(hand);
			}
		}

		Round round = new Round(handList, loser, inRiichi.toArray(Player[]::new));
		roundDAO.save(round);

		Game game = gameDAO.getById(gameId);
		game.addRound(round);
		gameDAO.save(game);

		response.setViewName("redirect:/game?game=" + gameId);
		return response;
	}

	@PostMapping("game/draw-submit")
	public ModelAndView submitDraw(@ModelAttribute("drawForm") DrawForm drawForm){
		ModelAndView response = new ModelAndView();
		//System.out.println(gson.toJson(drawForm));

		List<Player> inTenpai = createPlayerListFromIds(drawForm.getInTenpai());
		List<Player> inRiichi = createPlayerListFromIds(drawForm.getInRiichi());

		Round round = new Round(inTenpai.toArray(Player[]::new), inRiichi.toArray(Player[]::new));

		//TODO: Set up error checking to keep people from adding rounds with the wrong players.
		roundDAO.save(round);

		Game game = gameDAO.findById(drawForm.getGameId());
		game.addRound(round);

		gameDAO.save(game);

		response.setViewName("redirect:/game?game=" + drawForm.getGameId());
		return response;
	}

	private List<Player> createPlayerListFromIds(List<Long> ids){
		List<Player> ret = new LinkedList<>();
		if(ids == null || ids.size() == 0) return ret;
		for(long id: ids)
			ret.add(playerDAO.findById(id));
		return ret;
	}
}
