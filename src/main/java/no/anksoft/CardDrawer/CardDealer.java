package no.anksoft.CardDrawer;

import java.util.Random;


public class CardDealer {

	private Random random;
	private int cardsLeft;
	private final CardStatus cardStatus[];
	private final int highest;

	public CardDealer(int highest) {
		this.highest = highest;
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
			putDiscardedCardsBackInDeck();
			if (cardsLeft == 0) {
				throw new IllegalStateException("There are no cards left");
			}
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

	private void putDiscardedCardsBackInDeck() {
		for (int i=0;i<highest-1;i++) {
			if (cardStatus[i] == CardStatus.DISCARDED) {
				cardStatus[i] = CardStatus.IN_DRAW_DECK;
				cardsLeft++;
			}
		}
	}

	public void discardCard(int cardNumber) {
		if ((cardNumber < 1) || (cardNumber > highest)) {
			throw new IllegalArgumentException("Card number must between 1 and " + highest);
		}
		int cardIndex = cardNumber-1;
		CardStatus oldStatus = cardStatus[cardIndex];
		cardStatus[cardIndex] = CardStatus.DISCARDED;
		if (oldStatus == CardStatus.IN_DRAW_DECK) {
			cardsLeft--;
		}
	}


}
