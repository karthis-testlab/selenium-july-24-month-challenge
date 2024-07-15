package challenge.week03.day12;

import java.time.Duration;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AlertTest {
	
	ChromeDriver driver;
	WebDriverWait wait;

	@BeforeMethod
	public void setUp() {
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		driver.manage().window().maximize();
		driver.get("https://www.espncricinfo.com/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
	}

	@AfterMethod
	public void tearDown(ITestResult result) {		
		driver.quit();
	}
	
	@Test
	public void alterTest() {
		
	}

}