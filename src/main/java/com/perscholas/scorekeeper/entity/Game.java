package com.perscholas.scorekeeper.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "game")
public class Game extends GameAbstract{
	private static final int DEFAULT_STARTING_SCORE = 25000;
	private static final int DEFAULT_ENDING_SCORE = 30000;
	private static final int DEFAULT_REPEAT_VALUE = 100;
	private static final int DEFAULT_RIICHI_VALUE = 1000;
	private static final int DEFAULT_TENPAI_PAYMENT = 3000;
	private static final int DEFAULT_NUMBER_OF_PLAYERS = 4;

	private int wind;
	private int currentDealer;
	private int repeats;
	@ManyToMany(targetEntity = Player.class)
	@OrderColumn(name = "starting_seat")
	@JoinTable(
			joinColumns = {@JoinColumn(name = "game_id")},
			inverseJoinColumns = {@JoinColumn(name = "player_id")},
			uniqueConstraints = {@UniqueConstraint(columnNames = {"game_id", "player_id"})}
	)
	private List<Player> players;
	@OneToMany(targetEntity = Round.class)
	@OrderColumn(name = "round_number")
	@JoinTable(
			joinColumns = {@JoinColumn(name = "game_id")},
			inverseJoinColumns = {@JoinColumn(name = "round_id")},
			uniqueConstraints = {@UniqueConstraint(columnNames = {"game_id", "round_id"})}
	)
	private List<Round> rounds;
	@ElementCollection
	@CollectionTable(
			name = "game_score",
			joinColumns = {@JoinColumn(name = "game_id")}
	)
	@MapKeyColumn(name = "player_id")
	@Column(name = "score")
	private Map<Player, Integer> score;
	private int currentRiichis;

	@OrderColumn(name = "player_seat")
	@ElementCollection(targetClass = String.class)
	private List<String> displayNames;

	public Game(){
		numPlayers = DEFAULT_NUMBER_OF_PLAYERS;
		wind = EAST;
		currentDealer = EAST;
		repeats = 0;
		currentRiichis = 0;
		displayNames = new ArrayList<>(numPlayers);
		for(int i = 0; i < numPlayers; i++) displayNames.add(null);
		score = new HashMap<>();
		rounds = new LinkedList<>();
	}

	public Game(Player p1, Player p2, Player p3, Player p4){
		this(p1, p2, p3, p4, DEFAULT_STARTING_SCORE);
	}

	public Game(Player p1, Player p2, Player p3, Player p4, int startingScore){
		this();
		//players = List.of(p1, p2, p3, p4);
		this.startingScore = startingScore;
		this.endingScore = DEFAULT_ENDING_SCORE;
		this.riichiValue = DEFAULT_RIICHI_VALUE;
		this.repeatValue = DEFAULT_REPEAT_VALUE;
		this.tenpaiPayment = DEFAULT_TENPAI_PAYMENT;
		/*for(Player p: players){
			score.put(p, startingScore);
		}*/
		initializePlayerList(Arrays.asList(p1, p2, p3, p4));
	}

	public void addRound(Round round) {
		rounds.add(round);
		round.setWind(wind);
		round.setCurrentDealer(currentDealer);
		round.setRepeats(repeats);

		Map<Player, Integer> scoreDifference = new HashMap<>();
		for(Player p: score.keySet())
			scoreDifference.put(p, score.get(p));

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

		for(Player p: score.keySet()){
			int difference = score.get(p) - scoreDifference.get(p);
			if(difference == 0) scoreDifference.remove(p);
			else scoreDifference.put(p, difference);
		}

		round.setScoreChange(scoreDifference);
	}

	private void handleRepeats(boolean dealerSuccess, boolean draw){
		if(dealerSuccess || draw) repeats += 1;
		else repeats = 0;
		if(!dealerSuccess) {
			currentDealer = (currentDealer + 1) % numPlayers;
			if(currentDealer == EAST) wind = (wind + 1) % numPlayers;
		}
	}

	public Round removeRound(long roundId) {
		Round lastRound = rounds.get(rounds.size() - 1);
		if(roundId != lastRound.getId()) return null;

		rounds.remove(rounds.size() - 1);
		initializePlayerList(players);
		List<Round> oldRounds = rounds;
		rounds = new LinkedList<>();
		currentRiichis = 0;
		wind = EAST;
		currentDealer = EAST;
		repeats = 0;

		for(Round r: oldRounds) addRound(r);

		return lastRound;
	}

	private void handleRiichis(Player bigWinner, List<Player> riichis){
		currentRiichis += riichis.size();
		for(Player p: riichis)
			score.put(p, score.get(p) - riichiValue);
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
		for(; players.get(loserIndex) != loser; loserIndex++);
		Player bigWinner = null;
		for(int i = loserIndex; i < numPlayers + loserIndex; i++){
			Player winner = players.get(i % numPlayers);
			if(round.getWinners().containsKey(winner)){
				int handScore = round.getWinners().get(winner).getRonScore(isDealer(winner));
				if(bigWinner == null) {
					bigWinner = winner;
					handScore += repeats * repeatValue * (numPlayers - 1);
				}
				exchangePoints(winner, loser, handScore);
			}
		}
		handleRepeats(round.getWinners().containsKey(players.get(currentDealer)), false);
		handleRiichis(bigWinner, round.getInRiichi());
	}

	private void handleDraw(Round round) throws IllegalArgumentException{
		if(round.getWinType() != Round.Result.DRAW)
			throw new IllegalArgumentException("Round ended by " + round.getWinType() + "; draw expected.");
		List<Player> inTenpai = round.getTenpai();
		handleRepeats(inTenpai.contains(players.get(currentDealer)), true);
		handleRiichis(null, round.getInRiichi());
		if(inTenpai.size() == 0 || inTenpai.size() == players.size()) return;
		Player[] noten = players.stream()
				.filter(p -> !inTenpai.contains(p)).toArray(Player[]::new);
		for(Player p: inTenpai){
			score.put(p, score.get(p) + tenpaiPayment / inTenpai.size());
		}
		for(Player p: noten){
			score.put(p, score.get(p) - tenpaiPayment / noten.length);
		}
	}

	private boolean isDealer(Player p){
		return p == players.get(currentDealer);
	}

	private void changePlayer(Player player, int seat){
		Player prevPlayer = players.get(seat);
		players.set(seat, player);
		for(Round r: rounds){
			r.changePlayer(prevPlayer, player);
		}
		score.put(player, score.get(prevPlayer));
		score.remove(prevPlayer);
	}

	public String getDisplayName(int index){
		if(displayNames == null || displayNames.size() < players.size()) return "ERROR";
		return displayNames.get(index);
	}

	public String getDisplayName(Player p){
		if(displayNames == null || displayNames.size() < players.size()) return p.getUsername();
		int index = players.indexOf(p);
		return getDisplayName(index);
	}

	public void setDisplayName(int index, String newName){
		if(displayNames == null || displayNames.size() != numPlayers)
			displayNames = new ArrayList<>(numPlayers);
		displayNames.set(index, newName);
	}

	public void setDisplayName(Player p, String newName){
		int index = players.indexOf(p);
		setDisplayName(index, newName);
	}

	public void initializePlayerList(List<Player> players){
		setPlayers(players);
		for (Player player : players) {
			score.put(player, startingScore);
			if(getDisplayName(player) == null || getDisplayName(player).isEmpty()) {
				String displayName = player.getDisplayName();
				setDisplayName(player, (displayName == null || displayName.isEmpty()) ? player.getUsername() : displayName);
			}
		}
	}

	public int getPlayerStartingSeat(Player player){
		return players.indexOf(player);
	}

	public int getPlayerCurrentSeat(Player player){
		return (getPlayerStartingSeat(player) + numPlayers - currentDealer) % numPlayers;
	}

	public List<Player> getPlayersFromPerspective(Player player){
		int splitIndex = getPlayerStartingSeat(player);
		List<Player> ret = players.subList(splitIndex, numPlayers);
		ret.addAll(players.subList(0, splitIndex));

		return ret;
	}

	public List<Player> getPlayersFromPerspective(long playerId){
		for(Player p: players)
			if(p.getId() == playerId) return getPlayersFromPerspective(p);
		return null;
	}

	public Player getPlayerById(long id){
		for(Player p: players)
			if(p.getId() == id) return p;
		return null;
	}

	public static String getWindAsString(int wind){
		switch(wind){
			case EAST: return "East";
			case SOUTH: return "South";
			case WEST: return "West";
			case NORTH: return "North";
			default: return "ERR";
		}
	}

	// TODO: Either fix this stuff up or make proper tests.
	/*public static void main(String[] args) {
		Player p1 = new Player("Aaron");
		Player p2 = new Player("Alice");
		Player p3 = new Player("Emerald");
		Player p4 = new Player("Crystal");
		Game game = new Game(p1, p2, p3, p4);

		Hand winningHand = new Hand(p2, 4, 30);
		//winners.put(p2, winningHand);
		Round r1 = new Round(winningHand, new Player[]{});
		StringJoiner joiner = new StringJoiner("/ /", "/", "/");
		joiner.add("Dealer:" + game.players[game.currentDealer].getUsername());
		//TODO: Make an actual function that returns the dealer
		game.handleTsumo(r1);
		for(Player p: game.players){
			joiner.add(String.format("%s: %d", p.getUsername(), game.score.get(p)));
		}
		System.out.println(joiner);

		winningHand = new Hand(p2, 2, 30);
		Round r2 = new Round(winningHand, new Player[]{p1});
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
	}*/
}
