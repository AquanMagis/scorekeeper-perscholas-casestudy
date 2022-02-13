package com.perscholas.scorekeeper.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Map;

/*
	Defines a game hand (named round to avoid confusion with a scored hand.)
	Current issues:
		Currently represents a finished hand instead of a hand in-progress.
 */
@Getter
@Setter
@Entity
@Table(name = "round")
public class Round {
	public enum Result {TSUMO, RON, DRAW}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private Map<Player, Hand> winners;
	@ManyToOne
	private Player loser;
	private Player[] tenpai;
	private Player[] inRiichi;
	private Result winType;

	public Round(){}

	/**
	 * Default constructor to handle things that occur every round (only riichi bets).
	 *
	 * @param playersInRiichi	Array of players who declared riichi this round.
	 */
	private Round(Player[] playersInRiichi){
		inRiichi = playersInRiichi == null ? new Player[]{} : playersInRiichi;
	}

	/**
	 * Create a hand in which one or more players won off another.
	 *
	 * @param winningPlayers	Map of Player(s) that won to hands they won with.
	 * @param losingPlayer		Player that lost.
	 * @param playersInRiichi	Array of Players who declared riichi this round.
	 */
	public Round(Map<Player, Hand> winningPlayers, Player losingPlayer, Player[] playersInRiichi){
		this(playersInRiichi);
		this.winners = winningPlayers;
		this.loser = losingPlayer;
		winType = Result.RON;
	}

	/**
	 * Create a hand in which one player self-drew their own winning tile.
	 *
	 * @param winningPlayer		Map of Player who won hand to hand they won with
	 *                          Throws IllegalArgumentException if the Map contains more than one key
	 * @param playersInRiichi	Array of Players who declared riichi this round
	 */
	public Round(Map<Player, Hand> winningPlayer, Player[] playersInRiichi) throws IllegalArgumentException{
		this(playersInRiichi);
		if(winningPlayer.size() > 1) throw new IllegalArgumentException("Only one player can win by tsumo.");
		winners = winningPlayer;
		winType = Result.TSUMO;
	}

	/**
	 * Create a hand that made it to draw.
	 * Aborts should be handled as though all four players made it to tenpai.
	 *
	 * @param tenpaiPlayers		Array of Players who achieved tenpai
	 * @param playersInRiichi	Array of Players who declared riichi this round
	 */
	public Round(Player[] tenpaiPlayers, Player[] playersInRiichi){
		this(playersInRiichi);
		tenpai = tenpaiPlayers;
		winType = Result.DRAW;
	}

	public Map<Player, Hand> getWinners() {
		return winners;
	}

	public void setWinners(Map<Player, Hand> winners) {
		this.winners = winners;
	}

	public Player getLoser() {
		return loser;
	}

	public void setLoser(Player loser) {
		this.loser = loser;
	}

	public Player[] getTenpai() {
		return tenpai;
	}

	public void setTenpai(Player[] tenpai) {
		this.tenpai = tenpai;
	}

	public Player[] getInRiichi() {
		return inRiichi;
	}

	public void setInRiichi(Player[] inRiichi) {
		this.inRiichi = inRiichi;
	}

	public Result getWinType() {
		return winType;
	}
}