package com.perscholas.scorekeeper.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@Getter
@Setter
@MappedSuperclass
public abstract class GameAbstract {
	public enum EndgameRiichis{WINNER, LOST, RETURNED}
	public static final int EAST = 0, SOUTH = 1, WEST = 2, NORTH = 3;

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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		GameAbstract that = (GameAbstract) o;
		return id == that.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
