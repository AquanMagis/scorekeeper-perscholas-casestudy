package com.perscholas.scorekeeper.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Player {
	private int id;
	private String username;
	private String password;
	private String email;
	private String firstName;
	private String lastName;
	private List<Game> gamesList;

	public Player(String username){
		this.username = username;
	}
}
