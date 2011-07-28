package no.anksoft.carddrawer;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CardDrawerServlet extends HttpServlet {

	private static final long serialVersionUID = -100236375084824924L;
	private CardDrawerDao cardDrawerDao;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter writer = resp.getWriter();
		if ("/login.html".equals(req.getPathInfo())) {
			displayLoginScreen(writer);
		} else {
			displayStatus(writer,req.getSession());
		}
	}

	private void displayStatus(PrintWriter writer, HttpSession session) {
		Player player = (Player) session.getAttribute("player");
		writer //
			.append("<html><body>")
			.append("Player: ")
			.append(player.getName())
			.append("<br/>")
			.append("</body></html>")
		;
					
	}

	private void displayLoginScreen(PrintWriter writer) {
		writer //
				.append("<form action='login.html' method='POST'>") //
				.append("<input type='text' name='player_name' value=''/>") //
				.append("<input type='submit' name='loginPlayer' value='Login'/>") //
				.append("</form>") //
		;
	}
	
	@Override
	public void init() throws ServletException {
		super.init();
		cardDrawerDao = new CardDrawerDao() {
			@Override
			public void login(Player olayers) {
			}
		};
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Player player = Player.withName(req.getParameter("player_name"));
		cardDrawerDao.login(player);
		req.getSession().setAttribute("player", player);
		resp.sendRedirect("cardDrawer/status.html");
	}

	public void setCardDrawerDao(CardDrawerDao cardDrawerDao) {
		this.cardDrawerDao = cardDrawerDao;
	}
}
