package no.anksoft.carddrawer;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Ignore;
import org.junit.Test;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class CardDrawerWebTest {
	@Test
	@Ignore
	public void shouldBeAbleToLogInAndSeeStatusScreeen() throws Exception {
		Server server = new Server(0);
		server.addHandler(new WebAppContext("src/main/webapp", "/"));
		server.start();
		int localPort = server.getConnectors()[0].getLocalPort();
		String baseUrl = "http://localhost:" + localPort + "/";
		WebDriver browser = createBrowser();
		browser.get(baseUrl);
		browser.findElement(By.linkText("Login")).click();
		browser.findElement(By.name("player_name")).sendKeys("Darth");
		browser.findElement(By.name("loginPlayer")).click();
		
		assertThat(browser.getPageSource()).contains("Player Darth");
		assertThat(browser.getCurrentUrl()).contains("cardDrawer/status.html");
	}

	private HtmlUnitDriver createBrowser() {
		return new HtmlUnitDriver() {
			@Override
			public WebElement findElement(By by) {
				try {
					return super.findElement(by);
				} catch (NoSuchElementException e) {
					throw new NoSuchElementException("Did not find " + by + " in " + getPageSource());
				}
			}
		};
	}
}
