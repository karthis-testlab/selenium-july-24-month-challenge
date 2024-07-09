package challenge.week02.day07;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class MapzTest {
	
	/**
	 * 
	 * Retrieve the tooltip message by using selenium webdriver
	 * 
	 * TEST STEPS:
	 * 
	 * STEP 1: Open your preferred web browser.
	 * STEP 2: Navigate to the URL: "https://www.mapz.com/map".
	 * STEP 3: Click on the Draw tab from the right side menu.
	 * STEP 4: Mousehover on the Email input field.
	 * STEP 5: Retrieve and print the tooltip message "Please fill out this field."
	 * 
	 */

	ChromeDriver driver;
	WebDriverWait wait;

	@BeforeMethod
	public void setUp() {
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		driver.manage().window().maximize();
		driver.navigate().to("https://mapz.com/map");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
	}

	@AfterMethod
	public void tearDown() {
		driver.close();
	}
	
	@Test
	public void validateTooltipTextInMapzApp() {
		driver.findElement(By.xpath("//a[normalize-space(text())='Draw']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("form[action$='login'] > div > div.row.first > div > input")));
		Actions action = new Actions(driver);
		action.moveToElement(driver.findElement(By.cssSelector("form[action$='login'] > div > div.row.first > div > input"))).build().perform();
		System.out.println("Tooltip Message: "+driver.findElement(By.cssSelector("form[action$='login'] > div > div.row.first > div > input")).getAttribute("validationMessage"));
	}

}