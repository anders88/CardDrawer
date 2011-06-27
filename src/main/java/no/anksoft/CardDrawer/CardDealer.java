package no.anksoft.CardDrawer;


public class CardDealer {

	private final int highest;

	public CardDealer(int highest) {
		this.highest = highest;
	}

	public int numberOfCardsInDrawpile() {
		return highest;
	}

}
