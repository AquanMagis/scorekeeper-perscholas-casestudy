package com.perscholas.scorekeeper.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Player {
	int id;
	String username;
	String password;
	String email;
	String firstName;
	String lastName;
	List<Game> gamesList;

	public Player(String username){
		this.username = username;
	}
}
