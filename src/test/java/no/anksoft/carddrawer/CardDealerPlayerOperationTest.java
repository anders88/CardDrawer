package no.anksoft.carddrawer;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.MapAssert.entry;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class CardDealerPlayerOperationTest {
	private CardDealer cardDealer = new CardDealer(10);
	private Random random = mock(Random.class);
	private CardDealerLogger cardDealerLogger = mock(CardDealerLogger.class);

	@Test
	public void shouldKeepTrackOfDrawnCards() throws Exception {
		when(random.nextInt(anyInt())).thenReturn(0);
		Player playerOne = new Player("PlayerOne");
		Player playerTwo = new Player("PlayerTwo");

		cardDealer.drawCard(playerOne);
		cardDealer.drawCard(playerTwo);
		cardDealer.drawCard(playerOne);

		assertThat(cardDealer.playerCards(playerOne)).containsOnly(1, 3);
		assertThat(cardDealer.playerCards(playerTwo)).containsOnly(2);
	}

	@Test
	public void shouldHandleDiscardedCards() throws Exception {
		when(random.nextInt(anyInt())).thenReturn(0);
		Player playerOne = new Player("PlayerOne");

		cardDealer.drawCard(playerOne);
		cardDealer.drawCard(playerOne);
		cardDealer.drawCard(playerOne);

		cardDealer.discardCard(2);
		cardDealer.putCardOutOfPlay(3);

		assertThat(cardDealer.playerCards(playerOne)).containsOnly(1);

	}

	@Test
	public void shouldGiveStatusList() throws Exception {
		when(random.nextInt(anyInt())).thenReturn(0);
		Player playerOne = new Player("PlayerOne");
		Player playerTwo = new Player("PlayerTwo");

		cardDealer.drawCard(playerOne);
		cardDealer.drawCard(playerTwo);
		cardDealer.discardCard(3);
		cardDealer.putCardOutOfPlay(4);

		Map<Integer, String> statusMap = cardDealer.giveFullReport();

		assertThat(statusMap) // 
				.hasSize(10) //
				.includes( //
						entry(1, "Drawn by PlayerOne"), //
						entry(2, "Drawn by PlayerTwo"), //
						entry(2, "Discarded"), //
						entry(3, "Out of play"), //
						entry(4, "In draw pile"), //
						entry(5, "In draw pile"), //
						entry(6, "In draw pile"), //
						entry(7, "In draw pile"), //
						entry(8, "In draw pile"), //
						entry(9, "In draw pile"), //
						entry(10, "In draw pile") //
						
				);
	}

	@Before
	public void setup() {
		cardDealer.setRandom(random);
		cardDealer.setCardDealerLogger(cardDealerLogger);
	}

}
