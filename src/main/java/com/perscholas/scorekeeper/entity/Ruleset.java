package com.perscholas.scorekeeper.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "ruleset")
public class Ruleset extends GameAbstract{
	private String imageUrl;
	private String setName;
	private String description;

	// This was ultimately unnecessary, saving it in case I change my mind at some point.
	/*public Game createGame(){
		Game game = new Game();
		GameAbstract rules = this;
		Field[] fields = rules.getClass().getFields();
		for(Field f: fields){
			try {
				ReflectionUtils.setField(f, game, f.get(this));
			}
			catch(IllegalAccessException e){}
		}

		return game;
	}*/
}
