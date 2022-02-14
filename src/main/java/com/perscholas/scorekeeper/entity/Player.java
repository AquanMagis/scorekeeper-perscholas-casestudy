package com.perscholas.scorekeeper.entity;

import com.sun.istack.NotNull;
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
	public final static int TEXT_FIELD_LENGTH = 20;
	public final static int PASSWORD_LENGTH = 71;
	public final static int EMAIL_LENGTH = 254;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "username", unique = true, nullable = false, length = TEXT_FIELD_LENGTH)
	@NotNull
	private String username;
	@Column(name = "password", nullable = false, length = PASSWORD_LENGTH)
	@NotNull
	private String password;
	@Column(name = "email", unique = true, length = EMAIL_LENGTH)
	private String email;
	@Column(name = "first_name", length = TEXT_FIELD_LENGTH)
	private String firstName;
	@Column(name = "last_name", length = TEXT_FIELD_LENGTH)
	private String lastName;
	// Linking Players to Games is already done in Game.java.
	//private List<Game> gamesList;

	public Player() {}

	public Player(String username){
		this.username = username;
	}
}
