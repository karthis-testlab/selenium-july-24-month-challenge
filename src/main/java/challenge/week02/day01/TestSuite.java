package challenge.week02.day01;

import java.time.Duration;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

public class TestSuite {
	
	ChromeDriver driver;
	WebDriverWait wait;
	Faker testData;

	@BeforeMethod
	public void setUp() {
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		driver.manage().window().maximize();
		testData = new Faker();
	}

	@AfterMethod
	public void tearDown() {
		driver.close();
	}
	
	@Test
	public void testCase() {
		
	}
	

}