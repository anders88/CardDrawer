package no.anksoft.carddrawer;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

public class CardDrawerServlet extends HttpServlet {

	private static final long serialVersionUID = -100236375084824924L;
	private CardDrawerDao cardDrawerDao;
	private DisplayStatusForm displayStatusForm = new DisplayStatusForm();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter writer = resp.getWriter();
		String pathInfo = req.getPathInfo();
		if ("/login.html".equals(pathInfo)) {
			displayLoginScreen(writer);
		} else if ("/drawCard.html".equals(pathInfo)) {
			drawCard(writer,req.getSession());
		} else {
			displayStatus(writer,req.getSession());
		}
	}

	private void drawCard(PrintWriter writer, HttpSession session) {
		int cardNo = cardDrawerDao.drawCard(loggedInPlayer(session));
		writer  //
			.append("<html><body>") //
			.append("You drew card " + cardNo) //
			.append("<br/>") //
			.append("<a href='status.html'>Status screen</a><br/>") //
			.append("</body></html>") //
		;
	}

	private void displayStatus(PrintWriter writer, HttpSession session) throws ParseErrorException, MethodInvocationException, ResourceNotFoundException, IOException {
		Player player = loggedInPlayer(session);
		PlayerStatus status = cardDrawerDao.getStatus(player);
		
		displayStatusForm.write(writer,player,status);
					
	}

	private Player loggedInPlayer(HttpSession session) {
		return (Player) session.getAttribute("player");
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
