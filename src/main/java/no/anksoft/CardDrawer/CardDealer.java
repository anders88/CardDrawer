package no.anksoft.CardDrawer;

import java.util.Random;


public class CardDealer {

	private final int highest;
	private Random random;
	private int cardsLeft;

	public CardDealer(int highest) {
		this.highest = highest;
		this.cardsLeft = highest;
	}

	public int numberOfCardsInDrawpile() {
		return cardsLeft;
	}

	public void setRandom(Random random) {
		this.random = random;
		
	}

	public int drawCard() {
		cardsLeft--;
		return random.nextInt(highest)+1;
	}

}
