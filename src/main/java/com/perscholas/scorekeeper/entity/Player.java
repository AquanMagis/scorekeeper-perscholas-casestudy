package com.perscholas.scorekeeper.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "player")
public class Player {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String username;
	private String password;
	private String email;
	private String firstName;
	private String lastName;
	private List<Game> gamesList;

	public Player() {}

	public Player(String username){
		this.username = username;
	}
}
