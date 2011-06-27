package no.anksoft.carddrawer;

public enum CardStatus {
	IN_DRAW_DECK("In draw pile"),DRAWN("Drawn by "),DISCARDED("Discarded"),OUT_OF_PLAY("Out of play");
	
	private final String description;

	private CardStatus(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
}
