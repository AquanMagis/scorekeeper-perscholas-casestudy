package com.perscholas.scorekeeper.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "ruleset")
public class Ruleset extends GameAbstract{
	private String imageUrl;
	private String setName;
	private String description;

	public void createGame(){
		Game game = new Game();
		game.setStartingScore(startingScore);
		game.setEndingScore(endingScore);
		game.setRepeatValue(repeatValue);
		game.setRiichiValue(riichiValue);
		game.setNumPlayers(numPlayers);
		game.setTenpaiPayment(tenpaiPayment);
	}
}
