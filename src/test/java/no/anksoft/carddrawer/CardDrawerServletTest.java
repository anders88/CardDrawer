package no.anksoft.carddrawer;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

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

	@Test
	public void shouldDisplayLoginScreen() throws Exception {
		createGetCrequest("/login.html");
		
		servlet.service(req, resp);
		
		verify(resp).setContentType("text/html");
		
		assertThat(htmlSource.toString()) //
			.contains("<form action='login.html' method='POST'") //
			.contains("<input type='text' name='player_name' value=''") //
			.contains("<input type='submit' name='loginPlayer' value='Login'") //
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
		when(session.getAttribute("player")).thenReturn(Player.withName("Darth"));
		
		servlet.service(req, resp);
		
		verify(resp).setContentType("text/html");
		
		assertThat(htmlSource.toString()) //
			.contains("Player: Darth<br/>") //
		;
		DocumentHelper.parseText(htmlSource.toString());
	}
	
	@Test
	public void shouldLoginPlayer() throws Exception {
		when(req.getMethod()).thenReturn("POST");
		when(req.getParameter("player_name")).thenReturn("Darth");
		
		CardDrawerDao cardDrawerDao = mock(CardDrawerDao.class);
		servlet.setCardDrawerDao(cardDrawerDao);
		
		servlet.service(req, resp);
	
		Player darth = Player.withName("Darth");
		verify(cardDrawerDao).login(darth);
		verify(resp).sendRedirect("cardDrawer/status.html");
		verify(session).setAttribute("player", darth);
	}

	@Before
	public void setup() throws IOException {
		when(req.getSession()).thenReturn(session);
		when(resp.getWriter()).thenReturn(new PrintWriter(htmlSource));
	}
}
