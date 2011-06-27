package no.anksoft.CardDrawer;

import java.util.Random;


public class CardDealer {

	private Random random;
	private int cardsLeft;
	private final CardStatus cardStatus[];

	public CardDealer(int highest) {
		this.cardsLeft = highest;
		this.cardStatus = initCardStatus(highest);
	}

	private CardStatus[] initCardStatus(int highest) {
		CardStatus[] result = new CardStatus[highest];
		for (int i=0;i<highest;i++) {
			result[i] = CardStatus.IN_DRAW_DECK;
		}
		return result;
	}

	public int numberOfCardsInDrawpile() {
		return cardsLeft;
	}

	public void setRandom(Random random) {
		this.random = random;
		
	}

	public int drawCard() {
		if (cardsLeft == 0) {
			throw new IllegalStateException("There are no cards left");
		}
		int randomSeed = random.nextInt(cardsLeft);
		int draw = -1;
		for (int pos=0;pos<=randomSeed;pos++) {
			draw++;
			while (cardStatus[draw] != CardStatus.IN_DRAW_DECK) {
				draw++;
			}
		} 
		cardsLeft--;
		cardStatus[draw] = CardStatus.DRAWN;
		return draw+1;
	}


}
