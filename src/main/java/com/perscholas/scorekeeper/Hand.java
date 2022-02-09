package com.perscholas.scorekeeper;

public class Hand {
	private int han;
	private int fu;
	private int yakuman = 0;
	public static final int NONDEALER = 0;
	public static final int DEALER = 1;
	public static final int MANGAN = 2000;
	public static final int HANEMAN = 3000;
	public static final int BAIMAN = 4000;
	public static final int SANBAIMAN = 6000;
	public static final int YAKUMAN = 8000;

	public Hand(int han, int fu){
		this.han = han;
		this.fu = fu;
	}

	public Hand(int yakuman){
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

	public static void main(String[] args) {
		Hand testHand = new Hand(3, 30);
		System.out.println(testHand.getRonScore(false));
		System.out.println(testHand.getRonScore(true));
		System.out.println(testHand.getTsumoScores()[NONDEALER]);
		System.out.println(testHand.getTsumoScores()[DEALER]);

		Hand testYakuman = new Hand(1);
		System.out.println(testYakuman.getRonScore(false));
		System.out.println(testYakuman.getRonScore(true));
		System.out.println(testYakuman.getTsumoScores()[NONDEALER]);
		System.out.println(testYakuman.getTsumoScores()[DEALER]);

		for(int h = 1; h < 13; h++){
			System.out.println("Han  Fu |    Tsumo    | Non-Dealer | Dealer");
			for(int f = 20; f < 130; f += 10){
				Hand loopHand = new Hand(h, f);
				int[] tsumo = loopHand.getTsumoScores();
				int nondealerRon = loopHand.getRonScore(false);
				int dealerRon = loopHand.getRonScore(true);
				System.out.printf("%3d %3d |%6d/%6d| %10d | %6d\n",
						h, f, tsumo[NONDEALER], tsumo[DEALER], nondealerRon, dealerRon);
			}
		}
	}
}
