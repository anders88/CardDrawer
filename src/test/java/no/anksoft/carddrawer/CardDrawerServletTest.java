package no.anksoft.carddrawer;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentHelper;
import org.junit.Test;

public class CardDrawerServletTest {

	private CardDrawerServlet servlet = new CardDrawerServlet();
	private HttpServletRequest req = mock(HttpServletRequest.class);
	private HttpServletResponse resp = mock(HttpServletResponse.class);

	@Test
	public void shouldDisplayLoginScreen() throws Exception {
		StringWriter htmlSource = new StringWriter();
		when(resp.getWriter()).thenReturn(new PrintWriter(htmlSource));
		when(req.getMethod()).thenReturn("GET");
		
		servlet.service(req, resp);
		
		verify(resp).setContentType("text/html");
		
		assertThat(htmlSource.toString()) //
			.contains("<form action='login.html' method='POST'") //
			.contains("<input type='text' name='player_name' value=''") //
			.contains("<input type='submit' name='loginPlayer' value='Login'") //
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
	
		verify(cardDrawerDao).login(Player.withName("Darth"));
	}
}
