package com.perscholas.scorekeeper.entity;

import java.util.*;

public class Game {
	public static final int EAST = 0, SOUTH = 1, WEST = 2, NORTH = 3;
	private static final int DEFAULT_STARTING_SCORE = 25000;
	private static final int DEFAULT_REPEAT_VALUE = 100;
	private static final int DEFAULT_RIICHI_VALUE = 1000;
	private static final int DEFAULT_TENPAI_PAYMENT = 3000;

	private int round = EAST;
	private int currentDealer = EAST;
	private int repeats = 0;
	private Player[] players;
	private List<Round> hands = new LinkedList<>();
	private int startingScore;
	private Map<Player, Integer> score = new HashMap<>();
	private int currentRiichis = 0;
	private int repeatValue;
	private int riichiValue;
	private int numPlayers = 4;
	private int tenpaiPayment;

	public Game(Player p1, Player p2, Player p3, Player p4){
		this(p1, p2, p3, p4, DEFAULT_STARTING_SCORE);
	}

	public Game(Player p1, Player p2, Player p3, Player p4, int startingScore){
		players = new Player[] {p1, p2, p3, p4};
		this.startingScore = startingScore;
		this.riichiValue = DEFAULT_RIICHI_VALUE;
		this.repeatValue = DEFAULT_REPEAT_VALUE;
		this.tenpaiPayment = DEFAULT_TENPAI_PAYMENT;
		for(Player p: players){
			score.put(p, startingScore);
		}
	}

	public void addRound(Round round) {
		hands.add(round);
		switch (round.getWinType()) {
			case RON:
				handleRon(round);
				break;
			case TSUMO:
				handleTsumo(round);
				break;
			case DRAW:
				handleDraw(round);
				break;
			default:
				System.err.println("Invalid round generated.");
		}
	}

	private void handleRepeats(boolean dealerSuccess, boolean draw){
		if(dealerSuccess || draw) repeats += 1;
		else repeats = 0;
		if(!dealerSuccess) {
			currentDealer = (currentDealer + 1) % numPlayers;
			if(currentDealer == EAST) round = (round + 1) % numPlayers;
		}
	}

	private void handleRiichis(Player bigWinner, Player[] riichis){
		currentRiichis += riichis.length;
		for(Player p: riichis) score.put(p, score.get(p) - riichiValue);
		if(bigWinner != null){
			score.put(bigWinner, score.get(bigWinner) + currentRiichis * riichiValue);
			currentRiichis = 0;
		}
	}

	private void exchangePoints(Player winner, Player loser, int change){
		score.put(loser, score.get(loser) - change);
		score.put(winner, score.get(winner) + change);
	}

	//TODO: consider moving these handleX() methods to Round, for better coupling
	private void handleTsumo(Round round) throws IllegalArgumentException{
		if(round.getWinType() != Round.Result.TSUMO)
			throw new IllegalArgumentException("Round ended by " + round.getWinType() + "; tsumo expected.");
		Player winner = round.getWinners().keySet().iterator().next();
		Hand hand = round.getWinners().get(winner);
		int[] handScores = hand.getTsumoScores();
		for(Player p: players){
			if(round.getWinners().containsKey(p)) continue;
			int scoreChange = (isDealer(p) || isDealer(winner) ? handScores[Hand.DEALER] : handScores[Hand.NONDEALER])
					+ repeats * repeatValue;
			exchangePoints(winner, p, scoreChange);
		}
		handleRepeats(isDealer(winner), false);
		handleRiichis(winner, round.getInRiichi());
	}

	private void handleRon(Round round) throws IllegalArgumentException{
		if(round.getWinType() != Round.Result.RON)
			throw new IllegalArgumentException("Round ended by " + round.getWinType() + "; ron expected.");
		Player loser = round.getLoser();
		int loserScore = 0;
		int loserIndex = 0;
		for(; players[loserIndex] != loser; loserIndex++);
		Player bigWinner = null;
		for(int i = loserIndex; i < numPlayers + loserIndex; i++){
			Player winner = players[i % numPlayers];
			if(round.getWinners().containsKey(winner)){
				int handScore = round.getWinners().get(winner).getRonScore(isDealer(winner));
				if(bigWinner == null) {
					bigWinner = winner;
					handScore += repeats * repeatValue * (numPlayers - 1);
				}
				exchangePoints(winner, loser, handScore);
			}
		}
		handleRepeats(round.getWinners().containsKey(players[currentDealer]), false);
		handleRiichis(bigWinner, round.getInRiichi());
	}

	private void handleDraw(Round round) throws IllegalArgumentException{
		if(round.getWinType() != Round.Result.DRAW)
			throw new IllegalArgumentException("Round ended by " + round.getWinType() + "; draw expected.");
		Player[] inTenpai = round.getTenpai();
		handleRepeats(Arrays.asList(inTenpai).contains(players[currentDealer]), true);
		handleRiichis(null, round.getInRiichi());
		if(inTenpai.length == 0 || inTenpai.length == players.length) return;
		Player[] noten = Arrays.stream(players)
				.filter(p -> !Arrays.asList(inTenpai).contains(p)).toArray(Player[]::new);
		for(Player p: inTenpai){
			score.put(p, score.get(p) + tenpaiPayment / inTenpai.length);
		}
		for(Player p: noten){
			score.put(p, score.get(p) - tenpaiPayment / noten.length);
		}
	}

	private boolean isDealer(Player p){
		return p == players[currentDealer];
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
		StringJoiner joiner = new StringJoiner("/ /", "/", "/");
		joiner.add("Dealer:" + game.players[game.currentDealer].getUsername());
		//TODO: Make an actual function that returns the dealer
		game.handleTsumo(r1);
		for(Player p: game.players){
			joiner.add(String.format("%s: %d", p.getUsername(), game.score.get(p)));
		}
		System.out.println(joiner);

		winningHand = new Hand(2, 30);
		winners.clear();
		winners.put(p2, winningHand);
		Round r2 = new Round(winners, new Player[]{p1});
		joiner = new StringJoiner("/ /", "/", "/");
		joiner.add("Dealer:" + game.players[game.currentDealer].getUsername());
		game.handleTsumo(r2);
		for(Player p: game.players){
			joiner.add(String.format("%s: %d", p.getUsername(), game.score.get(p)));
		}
		System.out.println(joiner);

		winningHand = new Hand(5, 30);
		winners.clear();
		winners.put(p2, winningHand);
		Round r3 = new Round(winners, new Player[]{p1, p2, p4});
		joiner = new StringJoiner("/ /", "/", "/");
		joiner.add("Dealer:" + game.players[game.currentDealer].getUsername());
		game.handleTsumo(r3);
		for(Player p: game.players){
			joiner.add(String.format("%s: %d", p.getUsername(), game.score.get(p)));
		}
		System.out.println(joiner);

		winningHand = new Hand(1);
		winners.clear();
		winners.put(p3, winningHand);
		Round r4 = new Round(winners, p2, new Player[]{});
		joiner = new StringJoiner("/ /", "/", "/");
		joiner.add("Dealer:" + game.players[game.currentDealer].getUsername());
		game.handleRon(r4);
		for(Player p: game.players){
			joiner.add(String.format("%s: %d", p.getUsername(), game.score.get(p)));
		}
		System.out.println(joiner);

		winningHand = new Hand(2, 30);
		winners.clear();
		winners.put(p2, winningHand);
		winningHand = new Hand(2, 30);
		winners.put(p3, winningHand);
		winningHand = new Hand(2, 30);
		winners.put(p4, winningHand);
		Round r5 = new Round(winners, p1, new Player[]{p1});
		joiner = new StringJoiner("/ /", "/", "/");
		joiner.add("Dealer:" + game.players[game.currentDealer].getUsername());
		game.handleRon(r5);
		for(Player p: game.players){
			joiner.add(String.format("%s: %d", p.getUsername(), game.score.get(p)));
		}
		System.out.println(joiner);

		winningHand = new Hand(2, 30);
		winners.clear();
		winners.put(p1, winningHand);
		winningHand = new Hand(2, 30);
		winners.put(p2, winningHand);
		winningHand = new Hand(2, 30);
		winners.put(p4, winningHand);
		Round r6 = new Round(winners, p3, new Player[]{p1});
		joiner = new StringJoiner("/ /", "/", "/");
		joiner.add("Dealer:" + game.players[game.currentDealer].getUsername());
		game.handleRon(r6);
		for(Player p: game.players){
			joiner.add(String.format("%s: %d", p.getUsername(), game.score.get(p)));
		}
		System.out.println(joiner);

		Player[] inTenpai = {p1, p2, p3, p4};
		Round r7 = new Round(inTenpai, new Player[]{p1});
		joiner = new StringJoiner("/ /", "/", "/");
		joiner.add("Dealer:" + game.players[game.currentDealer].getUsername());
		game.handleDraw(r7);
		for(Player p: game.players){
			joiner.add(String.format("%s: %d", p.getUsername(), game.score.get(p)));
		}
		System.out.println(joiner);

		inTenpai = new Player[]{p2, p3, p4};
		Round r8 = new Round(inTenpai, new Player[]{});
		joiner = new StringJoiner("/ /", "/", "/");
		joiner.add("Dealer:" + game.players[game.currentDealer].getUsername());
		game.handleDraw(r8);
		for(Player p: game.players){
			joiner.add(String.format("%s: %d", p.getUsername(), game.score.get(p)));
		}
		System.out.println(joiner);

		inTenpai = new Player[]{p1, p3};
		Round r9 = new Round(inTenpai, new Player[]{});
		joiner = new StringJoiner("/ /", "/", "/");
		joiner.add("Dealer:" + game.players[game.currentDealer].getUsername());
		game.handleDraw(r9);
		for(Player p: game.players){
			joiner.add(String.format("%s: %d", p.getUsername(), game.score.get(p)));
		}
		System.out.println(joiner);

		inTenpai = new Player[]{p1, p3};
		Round r10 = new Round(inTenpai, new Player[]{});
		joiner = new StringJoiner("/ /", "/", "/");
		joiner.add("Dealer:" + game.players[game.currentDealer].getUsername());
		game.handleDraw(r10);
		for(Player p: game.players){
			joiner.add(String.format("%s: %d", p.getUsername(), game.score.get(p)));
		}
		System.out.println(joiner);

		winningHand = new Hand(3, 30);
		winners.clear();
		winners.put(p2, winningHand);
		Round r11 = new Round(winners, p1, new Player[]{p1});
		joiner = new StringJoiner("/ /", "/", "/");
		joiner.add("Dealer:" + game.players[game.currentDealer].getUsername());
		game.handleRon(r11);
		for(Player p: game.players){
			joiner.add(String.format("%s: %d", p.getUsername(), game.score.get(p)));
		}
		System.out.println(joiner);
	}
}
