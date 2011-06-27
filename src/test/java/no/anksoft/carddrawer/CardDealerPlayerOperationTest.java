package no.anksoft.carddrawer;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class CardDealerPlayerOperationTest {
	private CardDealer cardDealer = new CardDealer(10);
	private Random random = mock(Random.class);
	private CardDealerLogger cardDealerLogger = mock(CardDealerLogger.class);
	
	@Test
	public void shouldKeepTrackOfDrawnCards() throws Exception {
		Player playerOne = new Player("PlayerOne");
		Player playerTwo = new Player("PlayerTwo");
		when(random.nextInt(anyInt())).thenReturn(0);
		
		cardDealer.drawCard(playerOne);
		cardDealer.drawCard(playerTwo);
		cardDealer.drawCard(playerOne);

		assertThat(cardDealer.playerCards(playerOne)).containsOnly(1,3);
		assertThat(cardDealer.playerCards(playerTwo)).containsOnly(2);
	}
	
	@Before
	public void setup() {
		cardDealer.setRandom(random);
		cardDealer.setCardDealerLogger(cardDealerLogger);
	}

}
