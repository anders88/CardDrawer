package no.anksoft.carddrawer;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CardDrawerServlet extends HttpServlet {

	private static final long serialVersionUID = -100236375084824924L;
	private CardDrawerDao cardDrawerDao;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter writer = resp.getWriter();
		writer //
				.append("<form action='login.html' method='POST'>") //
				.append("<input type='text' name='player_name' value=''/>") //
				.append("<input type='submit' name='loginPlayer' value='Login'/>") //
				.append("</form>") //
		;
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		cardDrawerDao.login(Player.withName(req.getParameter("player_name")));
	}

	public void setCardDrawerDao(CardDrawerDao cardDrawerDao) {
		this.cardDrawerDao = cardDrawerDao;
	}
}
