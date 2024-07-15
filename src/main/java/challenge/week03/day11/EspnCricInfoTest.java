package challenge.week03.day11;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class EspnCricInfoTest {
	
	/**
	 * 
	 * Automate the following Test Case using different ways to scroll to an element and interact
	 * 
	 * TESTCASE:
	 * 
	 * > Launch the browser and Load URL as "https://www.espncricinfo.com/".
	 * > Click on first result under the Key Series.
	 * > Print the text of the first resulting add.
	 * > Quit the driver.
	 * 
	 */
	
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
	public void scrollTest() {		
		
		waitAndGetElement(By.cssSelector("div#onetrust-close-btn-container > button")).click();
		
		new Actions(driver)
		.scrollToElement(waitAndGetElement(By.xpath("//h2[text()='Key Series']")))
		.perform();
		
		waitAndGetElement(By.xpath("//h2[text()='Key Series']/../../following-sibling::div//a[1]")).click();
		
		driver.executeScript("arguments[0].scrollIntoView();", waitAndGetElement(By.xpath("//div[@class='feed']//h2[contains(@class,'ds-text-raw-white')]")));
		
		System.out.println(waitAndGetElement(By.xpath("//div[@class='feed']//h2[contains(@class,'ds-text-raw-white')]")).getText());
		
	}
	
	public WebElement waitAndGetElement(By locator) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

}