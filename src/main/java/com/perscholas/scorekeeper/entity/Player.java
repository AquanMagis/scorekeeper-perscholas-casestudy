package com.perscholas.scorekeeper.entity;

import java.util.List;

public class Player {
	String name;
	String password;
	String email;
	List<Game> gamesList;

	public Player(String name){
		this.name = name;
	}

	public String getName() { return name; }
}
