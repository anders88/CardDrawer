package no.anksoft.carddrawer;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Random;

import junit.framework.Assert;

import no.anksoft.carddrawer.CardDealer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

public class CardDealerBasicOperationsTest {
	private CardDealer cardDealer = new CardDealer(10);
	private Random random = mock(Random.class);
	private Player player = new Player("PlayerOne");
	private CardDealerLogger cardDealerLogger = mock(CardDealerLogger.class);

	@Test
	public void shouldHaveAllCardsInDrawPileFromTheStart() throws Exception {
		assertThat(cardDealer.numberOfCardsInDrawpile()).isEqualTo(10);
	}
	
	@Test
	public void shouldDrawACard() throws Exception {
		when(random.nextInt(10)).thenReturn(3);
		assertThat(cardDealer.drawCard(player)).isEqualTo(4);
		assertThat(cardDealer.numberOfCardsInDrawpile()).isEqualTo(9);
		verify(cardDealerLogger).drewCard(4,player);
	}

	
	@Test
	public void shouldNotDrawTheSameCardMoreThanOnce() throws Exception {
		when(random.nextInt(anyInt())).thenReturn(3);
		
		assertThat(cardDealer.drawCard(player)).isEqualTo(4);
		Player playerTwo = new Player("PlayerTwo");
		assertThat(cardDealer.drawCard(playerTwo)).isEqualTo(5);

		assertThat(cardDealer.numberOfCardsInDrawpile()).isEqualTo(8);
		
		InOrder order = inOrder(random);
		
		order.verify(random).nextInt(10);
		order.verify(random).nextInt(9);
		verify(cardDealerLogger).drewCard(4, player);
		verify(cardDealerLogger).drewCard(5, playerTwo);
		verify(cardDealerLogger,never()).shuffledDiscardPileIntoDrawPile();
	}
	
	@Test
	public void shouldThrowIllegalStateExceptionWhenThereAreNoMoreCards() throws Exception {
		when(random.nextInt(anyInt())).thenReturn(0);
		
		drawCards(10);

		try {
			cardDealer.drawCard(player);
			Assert.fail("Expected exception");
		} catch (IllegalStateException e) {
		}
		
	}
	
	@Test
	public void shouldPutDiscardedCardsBackIntoDeckWhenCardsRunOut() throws Exception {
		when(random.nextInt(anyInt())).thenReturn(0);
		
		drawCards(10);

		cardDealer.discardCard(8);
		verify(cardDealerLogger).discardedCard(8);
		
		assertThat(cardDealer.drawCard(player)).isEqualTo(8);
		verify(cardDealerLogger).shuffledDiscardPileIntoDrawPile();
	}
	
	@Test
	public void shouldNotReshuffleCardsPutOutOfPlay() throws Exception {
		when(random.nextInt(anyInt())).thenReturn(0);
		
		drawCards(10);

		cardDealer.discardCard(8);
		cardDealer.putCardOutOfPlay(7);
		
		assertThat(cardDealer.drawCard(player)).isEqualTo(8);
		verify(cardDealerLogger).discardedCard(8);
		verify(cardDealerLogger).putCardOutOfPlay(7);
		
	}

	private void drawCards(int times) {
		for (int i=0;i<times;i++) {
			cardDealer.drawCard(player);
		}
		
	}

	@Before
	public void setup() {
		cardDealer.setRandom(random);
		cardDealer.setCardDealerLogger(cardDealerLogger);
	}
}
