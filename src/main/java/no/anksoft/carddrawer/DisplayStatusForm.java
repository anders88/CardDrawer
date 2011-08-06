package no.anksoft.carddrawer;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Collection;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

public class DisplayStatusForm {

	public void write(PrintWriter writer, Player player, PlayerStatus status) throws ParseErrorException, MethodInvocationException, ResourceNotFoundException, IOException {
		VelocityEngine engine = new VelocityEngine();
		VelocityContext context = new VelocityContext();

		context.put("playerName",player.getName());
		context.put("cardsInDrawpile", "" + status.cardsInDrawpile());
		context.put("playerCards", commaSeparated(status.playerCards()));
		context.put("discardedCards", commaSeparated(status.discardedCards()));
		context.put("outOfPlayCards", commaSeparated(status.outOfPlayCards()));
		
		InputStream resourceAsStream = getClass().getResourceAsStream("/cardDrawer/statusScreen.html.vl");
		engine.evaluate(context, writer, "statusScreen", new InputStreamReader(resourceAsStream));
	
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


}
