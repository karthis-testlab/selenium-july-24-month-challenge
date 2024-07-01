package challenge.week01.day01;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class LaunchBrowser {

	public static void main(String[] args) {

		ChromeDriver way1 = new ChromeDriver();
		way1.get("https://www.testleaf.com");		

		try {
			RemoteWebDriver driver = new RemoteWebDriver(
					new URL("http://localhost:4444/wd/hub"), new ChromeOptions());
			driver.get("https://www.testleaf.com");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		WebDriver driver = WebDriverManager.chromedriver().create();
		driver.get("https://www.testleaf.com");

	}

}