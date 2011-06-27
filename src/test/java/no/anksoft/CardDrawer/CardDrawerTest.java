package no.anksoft.CardDrawer;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Random;

import org.junit.Test;

public class CardDrawerTest {
	private CardDealer cardDealer = new CardDealer(10);

	@Test
	public void shouldHaveAllCardsInDrawPileFromTheStart() throws Exception {
		assertThat(cardDealer.numberOfCardsInDrawpile()).isEqualTo(10);
	}
	
	@Test
	public void shouldDrawACard() throws Exception {
		Random random = mock(Random.class);
		cardDealer.setRandom(random);
		when(random.nextInt(10)).thenReturn(3);
		assertThat(cardDealer.drawCard()).isEqualTo(4);
		assertThat(cardDealer.numberOfCardsInDrawpile()).isEqualTo(9);
	}
}
