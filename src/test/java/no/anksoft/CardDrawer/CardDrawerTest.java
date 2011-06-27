package no.anksoft.CardDrawer;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

public class CardDrawerTest {
	private CardDealer cardDealer = new CardDealer(10);
	private Random random = mock(Random.class);

	@Test
	public void shouldHaveAllCardsInDrawPileFromTheStart() throws Exception {
		assertThat(cardDealer.numberOfCardsInDrawpile()).isEqualTo(10);
	}
	
	@Test
	public void shouldDrawACard() throws Exception {
		when(random.nextInt(10)).thenReturn(3);
		assertThat(cardDealer.drawCard()).isEqualTo(4);
		assertThat(cardDealer.numberOfCardsInDrawpile()).isEqualTo(9);
	}

	
	@Test
	public void shouldNotDrawTheSameCardMoreThanOnce() throws Exception {
		when(random.nextInt(anyInt())).thenReturn(3);
		
		assertThat(cardDealer.drawCard()).isEqualTo(4);
		assertThat(cardDealer.drawCard()).isEqualTo(5);

		assertThat(cardDealer.numberOfCardsInDrawpile()).isEqualTo(8);
		
		InOrder order = inOrder(random);
		
		order.verify(random).nextInt(10);
		order.verify(random).nextInt(9);
	}

	@Before
	public void setup() {
		cardDealer.setRandom(random);
	}
}
