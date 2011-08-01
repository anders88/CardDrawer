package no.anksoft.carddrawer;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

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
		PlayerStatus status = cardDrawerDao.getStatus(player);
		writer //
			.append("<html><body>")
		;
		displayValue(writer, "Player", player.getName());
		displayValue(writer, "Cards left in deck", "" + status.cardsInDrawpile());
		displayValue(writer, "Your cards", commaSeparated(status.playerCards()));
		displayValue(writer, "Discarded cards", commaSeparated(status.discardedCards()));
		displayValue(writer, "Out of play cards", commaSeparated(status.outOfPlayCards()));
		writer
			.append("</body></html>")
		;
					
	}

	private String commaSeparated(Collection<Integer> collection) {
		if ((collection == null) || (collection.isEmpty())) {
			return "";
		}
		StringBuilder result = new StringBuilder();
		boolean first = true;
		for (Integer value : collection) {
			if (!first) {
				result.append(",");
			}
			first = false;
			result.append(value);
		}
		return result.toString();
	}

	private void displayValue(PrintWriter writer, String label, String value) {
		writer
			.append(label +": ")
			.append(value)
			.append("<br/>");
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
