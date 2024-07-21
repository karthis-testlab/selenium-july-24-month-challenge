package challenge.week04.day16;

import java.time.Duration;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ScreenshotTest {
	
	ChromeDriver driver;
	WebDriverWait wait;
	String parentWindow;	
	
	@BeforeMethod
	public void setUp() {		
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		driver.manage().window().maximize();
		driver.navigate().to("https://www.irctc.co.in/nget/train-search");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		parentWindow = driver.getWindowHandle();
		System.out.println("Parent Window Handle Value is: "+parentWindow);
	}

	@AfterMethod
	public void tearDown(ITestResult result) {
		driver.quit();
	}
	
	@Test
	public void testScreenshotActivity() {
		
	}

}