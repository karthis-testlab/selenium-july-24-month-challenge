package challenge.week04.day20;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v127.network.Network;
import org.openqa.selenium.devtools.v127.network.model.Headers;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class BasicAuthenticationTest {
	
	ChromeDriver driver;
	WebDriverWait wait;
	
	@BeforeMethod
	public void setUp() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-search-engine-choice-screen");
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
	}
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
	
	@Test
	public void testBasicAuthenticationHandingThroughDevTools() {
		basicAuthUsingDevTools("admin", "admin");
		driver.get("https://the-internet.herokuapp.com/basic_auth");
		System.out.println(driver.findElement(By.cssSelector("div.example > h3")).getText());
		System.out.println(driver.findElement(By.cssSelector("div.example > p")).getText());
	}
	
	@Test
	public void testBasicAuthenticationThroughUrl() {
		driver.get(basicAuthPassingInUrl("the-internet.herokuapp.com/basic_auth", "admin", "admin"));
		System.out.println(driver.findElement(By.cssSelector("div.example > h3")).getText());
		System.out.println(driver.findElement(By.cssSelector("div.example > p")).getText());
	}
	
	public void basicAuthUsingDevTools(String userName, String password) {
		String auth = userName+":"+ password;
		String encoded = Base64.getEncoder().encodeToString(auth.getBytes());
		Map<String, Object> heardes = new HashMap<String, Object>();
		heardes.put("Authorization", "Basic "+encoded);
		DevTools devTools = driver.getDevTools();
		devTools.createSession();
		devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
		devTools.send(Network.setExtraHTTPHeaders(new Headers(heardes)));
	}
	
	public String basicAuthPassingInUrl(String uri, String userName, String password) {
		return "https://"+userName+":"+password+"@"+uri;
	}

}