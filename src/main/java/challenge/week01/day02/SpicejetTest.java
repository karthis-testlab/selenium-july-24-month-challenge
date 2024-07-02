package challenge.week01.day02;

import java.time.Duration;
import java.util.Arrays;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class SpicejetTest {

	/**
	 * Selenium July Month Challenge - Week01 Day02
	 * 
	 * TestCase:
	 * 
	 * 1. Launch the Browser and Load the URL "https://www.spicejet.com/".
	 * 2. Handle the Notification.
	 * 3. Resize your Browser Window. (1200,800)
	 * 4. Remove info of Chrome is being controlled by automated software.
	 * 5. Get the text of "Flights".
	 * 6. Print title of the page,
	 * 7. Close the browser.
	 * 
	 */
	public static void main(String[] args) {		
		
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		options.addArguments("--window-size=1200,800");
		options.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation", "disable-infobars"));
		ChromeDriver driver = new ChromeDriver(options);
		driver.get("https://www.spicejet.com/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		System.out.println(driver.findElement(By.xpath("//*[text()='Flights']")).getText());
		System.out.println(driver.getTitle());
		driver.close();
        
		
	}

}
