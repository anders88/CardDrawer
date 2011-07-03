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

	@Test
	public void shouldDisplayLoginScreen() throws Exception {
		CardDrawerServlet servlet = new CardDrawerServlet();
		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse resp = mock(HttpServletResponse.class);

		when(req.getMethod()).thenReturn("GET");
		StringWriter htmlSource = new StringWriter();
		when(resp.getWriter()).thenReturn(new PrintWriter(htmlSource));
		
		servlet.service(req, resp);
		
		verify(resp).setContentType("text/html");
		
		assertThat(htmlSource.toString()) //
			.contains("<form action='login.html' method='POST'") //
			.contains("<input type='text' name='player_name' value=''") //
			.contains("<input type='submit' name='loginPlayer' value='Login'") //
		;
		DocumentHelper.parseText(htmlSource.toString());
	}
}
