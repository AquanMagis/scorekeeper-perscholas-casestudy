package com.perscholas.scorekeeper;

import java.util.*;

public class Game {
	public static final int EAST = 0, SOUTH = 1, WEST = 2, NORTH = 3;
	private static final int DEFAULT_STARTING_SCORE = 25000;
	private static final int DEFAULT_HONBA_VALUE = 100;
	private static final int DEFAULT_RIICHI_VALUE = 1000;

	private int round = EAST;
	private int currentDealer = EAST;
	private int repeats = 0;
	private Player[] players;
	private List<Round> hands = new LinkedList<>();
	private int startingScore = 25000;
	private Map<Player, Integer> score = new HashMap<>();
	private int currentRiichis = 0;
	private int honbaValue;
	private int riichiValue;

	public Game(Player p1, Player p2, Player p3, Player p4){
		this(p1, p2, p3, p4, DEFAULT_STARTING_SCORE);
	}

	public Game(Player p1, Player p2, Player p3, Player p4, int startingScore){
		players = new Player[] {p1, p2, p3, p4};
		this.startingScore = startingScore;
		this.riichiValue = DEFAULT_RIICHI_VALUE;
		this.honbaValue = DEFAULT_HONBA_VALUE;
		for(Player p: players){
			score.put(p, startingScore);
		}
	}

	public void addRound(Round round){
		hands.add(round);
		switch(round.getWinType()){
			case RON:
				break;
			case TSUMO:
				break;
			case DRAW:
				break;
			default:
				System.err.println("Invalid round generated.");
		}
	}

	private void handleRound(Round round, boolean won){
		if(won){
			// TODO: Finish this bit.
		}
		else{

		}
	}

	private void handleTsumo(Round round){
		Player winner = round.getWinners().keySet().iterator().next();
		Hand hand = round.getWinners().get(winner);
		int[] handScores = hand.getTsumoScores();
		for(Player p: players){
			if(round.getWinners().containsKey(p)) continue;
			int scoreChange = isDealer(p) || isDealer(winner) ? handScores[Hand.DEALER] : handScores[Hand.NONDEALER];
			score.put(p, score.get(p) - scoreChange);
			score.put(winner, score.get(winner) + scoreChange);
		}
	}

	private void handleRon(Round round){

	}

	private void handleDraw(Round round){

	}

	private boolean isDealer(Player p){
		return p == players[round];
	}

	public static void main(String[] args) {
		Player p1 = new Player("Aaron");
		Player p2 = new Player("Alice");
		Player p3 = new Player("Emerald");
		Player p4 = new Player("Crystal");
		Game game = new Game(p1, p2, p3, p4);

		Map<Player, Hand> winners = new HashMap<>();
		Hand winningHand = new Hand(4, 30);
		winners.put(p2, winningHand);
		Round r1 = new Round(winners, new Player[]{});
		game.handleTsumo(r1);
		StringJoiner joiner = new StringJoiner("/ /", "/", "/");
		for(Player p: game.players){
			joiner.add(String.format("%s: %d", p.getName(), game.score.get(p)));
		}
		System.out.println(joiner);
	}
}
