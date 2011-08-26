package no.anksoft.carddrawer;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dom4j.DocumentHelper;
import org.junit.Before;
import org.junit.Test;

public class CardDrawerServletTest {

	private CardDrawerServlet servlet = new CardDrawerServlet();
	private HttpServletRequest req = mock(HttpServletRequest.class);
	private HttpServletResponse resp = mock(HttpServletResponse.class);
	private HttpSession session = mock(HttpSession.class);
	private StringWriter htmlSource = new StringWriter();
	private Player player = Player.withName("Darth");
	private CardDrawerDao cardDrawerDao = mock(CardDrawerDao.class);

	@Test
	public void shouldDisplayLoginScreen() throws Exception {
		createGetCrequest("/login.html");

		servlet.service(req, resp);

		verify(resp).setContentType("text/html");

		assertThat(htmlSource.toString())
				//
				.contains("<form action='login.html' method='POST'")
				//
				.contains("<input type='text' name='player_name' value=''")
				//
				.contains(
						"<input type='submit' name='loginPlayer' value='Login'") //
		;
		DocumentHelper.parseText(htmlSource.toString());
	}

	private void createGetCrequest(String path) {
		when(req.getMethod()).thenReturn("GET");
		when(req.getPathInfo()).thenReturn(path);
	}

	@Test
	public void shouldDisplayStatusScreen() throws Exception {
		createGetCrequest("/status.html");
		PlayerStatus playerStatus = mock(PlayerStatus.class);
		when(cardDrawerDao.getStatus(player)).thenReturn(playerStatus);

		when(playerStatus.cardsInDrawpile()).thenReturn(5);
		when(playerStatus.playerCards()).thenReturn(Arrays.asList(2, 3, 4));
		when(playerStatus.discardedCards()).thenReturn(Arrays.asList(6, 7, 8));
		when(playerStatus.outOfPlayCards()).thenReturn(Arrays.asList(9, 10));

		servlet.service(req, resp);

		verify(resp).setContentType("text/html");

		assertThat(htmlSource.toString()) //
				.contains("Player: Darth<br/>") //
				.contains("Your cards: 2,3,4<br/>") //
				.contains("Cards left in deck: 5<br/>") //
				.contains("Discarded cards: 6,7,8<br/>") //
				.contains("Out of play cards: 9,10<br/>") //
		;
		DocumentHelper.parseText(htmlSource.toString());
	}

	@Test
	public void shouldLoginPlayer() throws Exception {
		when(req.getMethod()).thenReturn("POST");
		when(req.getParameter("player_name")).thenReturn("Darth");

		servlet.service(req, resp);

		verify(cardDrawerDao).login(player);
		verify(resp).sendRedirect("cardDrawer/status.html");
		verify(session).setAttribute("player", player);
	}

	@Test
	public void shouldDisplayDrawnCard() throws Exception {
		createGetCrequest("/drawCard.html");
		when(cardDrawerDao.drawCard(player)).thenReturn(7);

		servlet.service(req, resp);

		verify(resp).setContentType("text/html");

		assertThat(htmlSource.toString()) //
				.contains("You drew card 7<br/>") //
				.contains("<a href='status.html'>Status screen</a>") //
		;
		DocumentHelper.parseText(htmlSource.toString());
	}

	@Before
	public void setup() throws IOException {
		when(req.getSession()).thenReturn(session);
		when(resp.getWriter()).thenReturn(new PrintWriter(htmlSource));
		when(session.getAttribute("player")).thenReturn(player);
		servlet.setCardDrawerDao(cardDrawerDao);
	}
}
