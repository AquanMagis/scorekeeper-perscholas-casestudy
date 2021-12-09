package com.perscholas.scorekeeper;

import java.util.Map;

/*
	Defines a game hand (named round to avoid confusion with a scored hand.)
	Current issues:
		Currently represents a finished hand instead of a hand in-progress.
 */
public class Round {
	public static enum Result {TSUMO, RON, DRAW};

	private Map<Player, Hand> winners;
	private Player loser;
	private Player[] tenpai;
	private Player[] inRiichi;
	private Result winType;


	// Generic riichi handling
	private Round(Player[] playersInRiichi){
		inRiichi = playersInRiichi == null ? new Player[]{} : playersInRiichi;
	}

	// Ron
	public Round(Map<Player, Hand> winningPlayers, Player losingPlayer, Player[] playersInRiichi){
		this(playersInRiichi);
		this.winners = winningPlayers;
		this.loser = losingPlayer;
		winType = Result.RON;
	}

	// Tsumo
	public Round(Map<Player, Hand> winningPlayer, Player[] playersInRiichi){
		this(playersInRiichi);
		winners = winningPlayer;
		winType = Result.TSUMO;
	}

	// Ryuukyoku (Internally, aborts can be counted as all 4 players in tenpai.)
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