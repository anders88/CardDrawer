package no.anksoft.carddrawer;

import java.util.Random;


public class CardDealer {

	private Random random;
	private int cardsLeft;
	private final CardStatus cardStatus[];
	private final int highest;
	private CardDealerLogger cardDealerLogger;

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

	public int drawCard(Player player) {
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
		int result = draw+1;
		cardDealerLogger.drewCard(result, player);
		return result;
	}

	private void putDiscardedCardsBackInDeck() {
		cardDealerLogger.shuffledDiscardPileIntoDrawPile();
		for (int i=0;i<highest-1;i++) {
			if (cardStatus[i] == CardStatus.DISCARDED) {
				cardStatus[i] = CardStatus.IN_DRAW_DECK;
				cardsLeft++;
			}
		}
	}

	public void discardCard(int cardNumber) {
		cardDealerLogger.discardedCard(cardNumber);
		int cardIndex = calculateCardIndex(cardNumber);
		updateDiscardStatus(cardIndex, CardStatus.DISCARDED);
	}

	public void putCardOutOfPlay(int cardNumber) {
		cardDealerLogger.putCardOutOfPlay(cardNumber);
		int cardIndex = calculateCardIndex(cardNumber);
		updateDiscardStatus(cardIndex, CardStatus.OUT_OF_PLAY);
	}

	private void updateDiscardStatus(int cardIndex, CardStatus discardStatus) {
		CardStatus oldStatus = cardStatus[cardIndex];
		cardStatus[cardIndex] = discardStatus;
		if (oldStatus == CardStatus.IN_DRAW_DECK) {
			cardsLeft--;
		}
	}

	private int calculateCardIndex(int cardNumber) {
		if ((cardNumber < 1) || (cardNumber > highest)) {
			throw new IllegalArgumentException("Card number must between 1 and " + highest);
		}
		int cardIndex = cardNumber-1;
		return cardIndex;
	}

	public void setCardDealerLogger(CardDealerLogger cardDealerLogger) {
		this.cardDealerLogger = cardDealerLogger;
	}


}
