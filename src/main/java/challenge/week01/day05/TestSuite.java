package challenge.week01.day05;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
	
	/**
	 * TESTCASE 1:
	 * 
	 * > Launch the Browser and Load the URL "https://my.aidaform.com/signup".
	 * > Enter the Username, Email Id, Password, Confirm Password.
	 * > Click Create My Free Account button to submit the form.
	 * > Close the browser
	 * 
	 */
	@Test
	public void testCase01() {
		driver.get("https://my.aidaform.com/signup");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));		
		waitAndGetElement(By.name("nickname")).sendKeys(testData.name().username());
		waitAndGetElement(By.name("email")).sendKeys(testData.internet().emailAddress());
		waitAndGetElement(By.name("password")).sendKeys("test@123");
		waitAndGetElement(By.name("confirm")).sendKeys("test@123");
		waitAndGetElement(By.xpath("//div[starts-with(@class, 'checkbox')]//*[local-name()='svg']")).click();
		waitAndGetElement(By.xpath("//button[text()='Create My Free Account']")).click();		
		waitAndGetElement(By.xpath("//*[text()=\"Just a moment! We're getting this page ready\"]"));
		waitAndGetElement(By.xpath("//h3[text()=\"Let's Confirm Your Email!\"]"));
	}
	
	/**
	 * TESTCASE 2:
	 * 
	 * > Launch the Browser and Load the URL "https://gemini.google.com/".
	 * > Click on the sign-in button & Print the Title of the Page.
	 * > Close the browser
	 * 
	 */
	@Test
	public void testCase02() {
		driver.get("https://gemini.google.com/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		waitAndGetElement("a[aria-label='Sign in']").click();
		waitAndGetElement("input[type='email']");
		System.out.println(driver.getTitle());
	}
	
	/**
	 * TESTCASE 3:
	 * 
	 * > Launch the Browser and Load the URL "https://petdiseasealerts.org/forecast-map#/".
	 * > Select Tick Borne disease Agents under Heart Worm
	 * > Close the browser
	 * 
	 */
	@Test
	public void testCase03() throws InterruptedException {
		driver.get("https://petdiseasealerts.org/forecast-map#/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		Thread.sleep(2000);
		driver.switchTo().frame(waitAndGetElement("iframe[id^='map']"));		
		Actions actions = new Actions(driver);
		actions.moveToElement(waitAndGetElement("div[name='category'] > a")).perform();
		waitAndGetElement(By.xpath("//ul[@class='filter-options-list']/li/a/span[text()='Tick Borne Disease Agents']")).click();
	           
	}
	
	public WebElement waitAndGetElement(String locator) {
		return wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.cssSelector(locator)));
	}

	public WebElement waitAndGetElement(By locator) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	public void waitUntilElementInvisible(By locator) {
		wait.until(ExpectedConditions
				.invisibilityOfElementLocated(locator));
	}
	
	public void scrollToElement(WebElement element) {
		driver.executeScript("arguments[0].scrollIntoView();", element);
	}
	
	public void scrollToElement(By locator) {
		driver.executeScript("arguments[0].scrollIntoView();", wait.until(ExpectedConditions.presenceOfElementLocated(locator)));
	}	

}