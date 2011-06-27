package no.anksoft.CardDrawer;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

public class CardDrawerTest {
	@Test
	public void shouldHaveAllCardsInDrawPileFromTheStart() throws Exception {
		CardDealer cardDealer = new CardDealer(10);
		assertThat(cardDealer.numberOfCardsInDrawpile()).isEqualTo(10);
	}
}
