package com.perscholas.scorekeeper.controller;

import com.perscholas.scorekeeper.dao.GameDAO;
import com.perscholas.scorekeeper.dao.PlayerDAO;
import com.perscholas.scorekeeper.entity.Game;
import com.perscholas.scorekeeper.entity.Player;
import com.perscholas.scorekeeper.form.PlayerUpdateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class DashboardController {
	@Autowired
	PlayerDAO playerDAO;
	@Autowired
	GameDAO gameDAO;

	@PostMapping("dashboard")
	public ModelAndView dashboard(){
		ModelAndView response = new ModelAndView("redirect:/dashboard");
		return response;
	}

	@GetMapping("dashboard")
	public ModelAndView dashboardGet(){
		ModelAndView response = new ModelAndView("dashboard");
		return response;
	}

	@GetMapping("user/edit")
	public ModelAndView userEdit(@RequestParam(required = false) String username){
		ModelAndView response = new ModelAndView("user-controls");

		if(username == null){
			UserDetails principal = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			username = principal.getUsername();
		}

		Player player = playerDAO.findByUsername(username);
		PlayerUpdateForm playerUpdateForm = new PlayerUpdateForm();

		response.addObject(player);
		response.addObject(playerUpdateForm);

		return response;
	}

	@PostMapping("user/edit-submit")
	public ModelAndView userEditSubmit(@ModelAttribute("playerUpdateForm") PlayerUpdateForm playerUpdateForm){
		ModelAndView response = new ModelAndView("redirect:/user/edit");

		UserDetails principal = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = principal.getUsername();
		Player player = playerDAO.findByUsername(username);

		player.setFirstName(playerUpdateForm.getFirstName());
		player.setLastName(playerUpdateForm.getLastName());
		player.setShowFirstName(playerUpdateForm.isShowFirstName());
		player.setShowLastName(playerUpdateForm.isShowLastName());
		player.setBio(playerUpdateForm.getBio());
		player.setDisplayName(playerUpdateForm.getDisplayName());

		playerDAO.save(player);

		return response;
	}

	@GetMapping("user/games")
	public ModelAndView viewUserGames(@RequestParam(value = "player", required = false) Long playerId){
		ModelAndView response = new ModelAndView("player-games");

		Player player;
		if(playerId == null){
			UserDetails principal = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String username = principal.getUsername();
			player = playerDAO.findByUsername(username);
		}
		else{
			player = playerDAO.findById((long)playerId);
		}

		List<Game> games = gameDAO.findByPlayers(player);

		/*List<Long> gameIds = gameDAO.findIdByPlayerId(player.getId());
		List<Game> games = List.of(gameIds.stream().map(id->gameDAO.findById((long)id)).toArray(Game[]::new));*/

		response.addObject("games", games);

		return response;
	}
}
