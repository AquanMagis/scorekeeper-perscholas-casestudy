package com.perscholas.scorekeeper.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "hand")
public class Hand {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "han")
	private int han;
	@Column(name = "fu")
	private int fu;
	@Column(name = "yakuman")
	private int yakuman = 0;
	@ManyToOne
	private Player winner;

	public static final int NONDEALER = 0;
	public static final int DEALER = 1;
	public static final int MANGAN = 2000;
	public static final int HANEMAN = 3000;
	public static final int BAIMAN = 4000;
	public static final int SANBAIMAN = 6000;
	public static final int YAKUMAN = 8000;

	public Hand() {}

	public Hand(Player winner, int han, int fu){
		this.winner = winner;
		this.han = han;
		this.fu = fu;
	}

	public Hand(Player winner, int yakuman){
		this.winner = winner;
		this.yakuman = yakuman;
	}

	public int getBaseValue(){
		return yakuman == 0 ? getLimit((int)(fu * Math.pow(2, han + 2))) : YAKUMAN * yakuman;
	}

	public int getLimit(int value){
		if(value <= 2000) return value;
		switch(han){
			case 1:
			case 2:
			case 3:
			case 4:
			case 5: return MANGAN;
			case 6:
			case 7: return HANEMAN;
			case 8:
			case 9:
			case 10: return BAIMAN;
			case 11:
			case 12: return SANBAIMAN;
			default: return YAKUMAN;
		}
	}

	public int getRonScore(boolean isDealer){
		return (int)(Math.ceil(getBaseValue() * (isDealer ? 6 : 4) / 100.0) * 100);
	}

	// Non-dealer payment at index 0, dealer payment at index 1, dealer just wins dealer payment value.
	public int[] getTsumoScores(){
		int nonDealer = (int)Math.ceil(getBaseValue() / 100.0) * 100;
		int dealer = (int)Math.ceil(getBaseValue() * 2 / 100.0) * 100;
		return new int[] {nonDealer, dealer};
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Hand hand = (Hand) o;
		return id == hand.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	public static void main(String[] args) {
		Player test = new Player("Test");
		Hand testHand = new Hand(test, 3, 30);
		System.out.println(testHand.getRonScore(false));
		System.out.println(testHand.getRonScore(true));
		System.out.println(testHand.getTsumoScores()[NONDEALER]);
		System.out.println(testHand.getTsumoScores()[DEALER]);

		Hand testYakuman = new Hand(test, 1);
		System.out.println(testYakuman.getRonScore(false));
		System.out.println(testYakuman.getRonScore(true));
		System.out.println(testYakuman.getTsumoScores()[NONDEALER]);
		System.out.println(testYakuman.getTsumoScores()[DEALER]);

		for(int h = 1; h < 13; h++){
			System.out.println("Han  Fu |    Tsumo    | Non-Dealer | Dealer");
			for(int f = 20; f < 130; f += 10){
				Hand loopHand = new Hand(test, h, f);
				int[] tsumo = loopHand.getTsumoScores();
				int nondealerRon = loopHand.getRonScore(false);
				int dealerRon = loopHand.getRonScore(true);
				System.out.printf("%3d %3d |%6d/%6d| %10d | %6d\n",
						h, f, tsumo[NONDEALER], tsumo[DEALER], nondealerRon, dealerRon);
			}
		}
	}
}
