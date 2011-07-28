package no.anksoft.carddrawer;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;

public class JettyServer {

	public static void main(String[] args) throws Exception {
		Server server = new Server(8081);
		server.addHandler(new WebAppContext("src/main/webapp", "/"));
		server.start();
		System.out.println("http://localhost:8081/");
	}

}
