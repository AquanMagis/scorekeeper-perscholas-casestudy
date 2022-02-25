package com.perscholas.scorekeeper.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public abstract class GameAbstract {
	public enum EndgameRiichis{WINNER, LOST, RETURNED}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;

	int startingScore;
	int endingScore;
	int repeatValue;
	int riichiValue;
	int numPlayers;
	int tenpaiPayment;
	boolean busting;
	@NotNull
	EndgameRiichis leftoverRiichis;
}
