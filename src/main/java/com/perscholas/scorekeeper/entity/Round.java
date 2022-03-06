package com.perscholas.scorekeeper.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

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
	private long id;

	//private Map<Player, Hand> winners;
	@OneToMany(targetEntity = Hand.class)
	private List<Hand> hands;
	@ManyToOne
	private Player loser;
	@OrderColumn(name = "in_tenpai")
	@ManyToMany(targetEntity = Player.class)
	private List<Player> tenpai;
	@OrderColumn(name = "in_riichi")
	@ManyToMany(targetEntity = Player.class)
	private List<Player> inRiichi;
	@Column(name = "win_type")
	private Result winType;

	/**
	 * Basic constructor for bean definition.
	 */
	public Round(){}

	/**
	 * Default constructor to handle things that occur every round (only riichi bets).
	 *
	 * @param playersInRiichi	Array of players who declared riichi this round.
	 */
	public Round(Player[] playersInRiichi){
		inRiichi = playersInRiichi == null ? new ArrayList<>() : Arrays.asList(playersInRiichi);
	}

	/**
	 * Create a hand in which one or more players won off another.
	 *
	 * @param winningHands		List of hands that won.
	 * @param losingPlayer		Player that lost.
	 * @param playersInRiichi	Array of Players who declared riichi this round.
	 */
	public Round(List<Hand> winningHands, Player losingPlayer, Player[] playersInRiichi){
		this(playersInRiichi);
		this.hands = winningHands;
		this.loser = losingPlayer;
		winType = Result.RON;
	}

	/**
	 * Create a hand in which one player self-drew their own winning tile.
	 *
	 * @param winningHand		List containing only the hand that won.
	 *                          Throws IllegalArgumentException if the Map contains more than one key
	 * @param playersInRiichi	Array of Players who declared riichi this round
	 */
	public Round(List<Hand> winningHand, Player[] playersInRiichi) throws IllegalArgumentException{
		this(playersInRiichi);
		if(winningHand.size() > 1) throw new IllegalArgumentException("Only one player can win by tsumo.");
		hands = winningHand;
		winType = Result.TSUMO;
	}

	public Round(Hand winningHand, Player[] playersInRiichi) throws IllegalArgumentException{
		this(List.of(winningHand), playersInRiichi);
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
		tenpai = Arrays.asList(tenpaiPlayers);
		winType = Result.DRAW;
	}

	public Map<Player, Hand> getWinners(){
		HashMap<Player, Hand> ret = new HashMap<>();
		for(Hand h: hands)
			ret.put(h.getWinner(), h);
		return ret;
	}

	public void changePlayer(Player oldPlayer, Player newPlayer){
		for(Hand h: hands){
			if(h.getWinner().getId() == oldPlayer.getId())
				h.setWinner(newPlayer);
		}
		int tenpaiIndex = tenpai.indexOf(oldPlayer);
		if(tenpaiIndex > -1){
			tenpai.set(tenpaiIndex, newPlayer);
		}
		int riichiIndex = inRiichi.indexOf(oldPlayer);
		if(riichiIndex > -1){
			tenpai.set(riichiIndex, newPlayer);
		}
		if(loser != null && loser.getId() == oldPlayer.getId())
			loser = newPlayer;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Round round = (Round) o;
		return id == round.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}