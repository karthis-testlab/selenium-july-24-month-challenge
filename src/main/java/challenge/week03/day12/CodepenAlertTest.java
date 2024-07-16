package challenge.week03.day12;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CodepenAlertTest {
	
	/**
	 * 
	 * Handle Webelement present inside the Frame And Alert by using Selenium Webdriver
	 * 
	 * TESTCASE:
	 * 
	 * > Open your preferred web browser.
	 * > Navigate to URL "https://codepen.io/unknownUser7/pen/ZEdGLbq".
	 * > Enter the username as "user" and password as "pass".
	 * > Click on the Login button.
	 * > Handle alert and retrieve the text of the alert.
	 * > Verify the welcome text.
	 * > Note: Handle the unexcepted alert.
	 * 
	 */	

	ChromeDriver driver;
	WebDriverWait wait;
	
	@BeforeMethod
	public void setUp() {
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		driver.manage().window().maximize();
		driver.navigate().to("https://codepen.io/unknownUser7/pen/ZEdGLbq");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
	}

	@AfterMethod
	public void tearDown(ITestResult result) {
		driver.quit();
	}

	@Test
	public void validateTheWelcomeTextAndHandleTheAlerts() {
		try {
			waitAndGetElement(By.cssSelector("button#view-switcher-button")).click();
			waitAndGetElement(By.id("left-layout")).click();
			waitAndGetElement(By.cssSelector("button#view-switcher-button")).click();
			driver.switchTo().frame("result");
			waitAndGetElement(By.id("username")).sendKeys("user");
			waitAndGetElement(By.id("password")).sendKeys("pass");
			waitAndGetElement(By.xpath("//button[text()='Login']")).click();		
			System.out.println("Login Alert Text: " + waitForAlert().getText());
			waitForAlert().accept();
			Assert.assertEquals(waitAndGetElement(By.cssSelector("div#welcomePage > h1")).getText(), "Welcome!");
			Assert.assertEquals(waitAndGetElement(By.cssSelector("div#welcomePage > p")).getText(),
					"You have successfully logged in.");
			waitAndGetElement(By.cssSelector("div#welcomePage > button")).click();
			waitForAlert();
			System.out.println("Logout Alert Text: " + driver.switchTo().alert().getText());
			driver.switchTo().alert().accept();
			Assert.assertTrue(waitAndGetElement(By.id("username")).isEnabled());
		} catch (UnhandledAlertException e) {			
			Assert.assertEquals(waitAndGetElement(By.cssSelector("div#welcomePage > h1")).getText(), "Welcome!");
			Assert.assertEquals(waitAndGetElement(By.cssSelector("div#welcomePage > p")).getText(),
					"You have successfully logged in.");
			waitAndGetElement(By.cssSelector("div#welcomePage > button")).click();			
			System.out.println("Logout Alert Text: " + waitForAlert().getText());
			waitForAlert().accept();
			Assert.assertTrue(waitAndGetElement(By.id("username")).isEnabled());
		}

	}

	public WebElement waitAndGetElement(By locator) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	public Alert waitForAlert() {
		return wait.until(ExpectedConditions.alertIsPresent());
	}

}