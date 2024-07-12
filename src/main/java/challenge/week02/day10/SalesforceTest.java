package challenge.week02.day10;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SalesforceTest {
	
	/**
	 * Automate the following test case using multiple click methods
	 * 
	 * TESTCASE 1
	 * 
	 * > Launch the browser and Load URL as "https://login.salesforce.com/".
	 * > Login to the application using the valid <user-name> and <password> and click login.
	 * > Click App Manager by search "App Manager" in the Quick Find input.
	 * > Click on the 'New Connected App' button.
	 * > Click to view the page in Salesforce Classic.
	 * > Enter app name & developer name.
	 * > Enter "test@gmail.com" as the contact email.
	 * > Click Enable OAuth checkbox.
	 * > Select the first 6 OAuth scopes.
	 * > Deselect the last 3 Selected scopes.
	 * > click Save Button and Close the browser.
	 *
	 */
	
	ChromeDriver driver;
	WebDriverWait wait;

	@BeforeMethod
	public void setUp() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		driver = new ChromeDriver(options);
		wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		driver.manage().window().maximize();
		driver.get("https://login.salesforce.com/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
	}

	@AfterMethod
	public void tearDown(ITestResult result) {
		driver.executeScript("arguments[0].click();", waitAndGetElement(By.xpath("//parent::div[@class='linkElements']/a[text()='Switch to Lightning Experience']")));
		waitAndGetElement(By.className("slds-icon-waffle"));
		driver.close();
	}
	
	@Test(retryAnalyzer = Retry.class)
	public void validateSalesforceNewConnectedAppModule() {
		driver.findElement(By.id("username")).sendKeys("vinothkumar@testleaf.com");
		driver.findElement(By.id("password")).sendKeys("SeleniumChallenge@24");
		driver.findElement(By.id("Login")).click();
		waitAndGetElement(By.cssSelector("input[placeholder='Quick Find']")).sendKeys("App Manager");
		waitAndGetElement(By.linkText("App Manager")).click();
		waitAndGetElement(By.xpath("//button[.='New Connected App']")).click();
		waitAndGetElement(By.cssSelector("span[data-aura-class='forceSocialPhoto']")).click();
		waitAndGetElement(By.linkText("Switch to Salesforce Classic")).click();
		waitAndGetElement(By.linkText("Setup")).click();
		waitAndGetElement(By.id("setupSearch")).sendKeys("Apps");
		waitAndGetElement(By.xpath("//div[@id='DevTools']//a[text()='Apps']")).click();
		waitAndGetElement(By.id("newAppMgmt")).click();
		waitAndGetElement(By.cssSelector("input[id$='nameSection:name']")).sendKeys("TestLeafJune2016");
		waitAndGetElement(By.cssSelector("input[id$='emailSection:contactEmail']")).sendKeys("test@gmail.com");
		waitAndGetElement(By.cssSelector("input[id$='isOauth']")).click();
		waitAndGetElement(By.cssSelector("input[id$='deviceFlowSection:deviceCheckbox']")).click();
		Select dropDown = new Select(waitAndGetElement(By.xpath("//label[text()='Available OAuth Scopes']/../following-sibling::select")));		
		for (int i = 0; i < 6; i++) {
			dropDown.selectByIndex(i);
		}
		for (int i = dropDown.getAllSelectedOptions().size() - 1; i >= 3; i--) {
			dropDown.deselectByIndex(i);
		}
		waitAndGetElement(By.xpath("//label[text()='Available OAuth Scopes']/../following-sibling::select/../following-sibling::td[@class='buttonCell']//img[@alt='Add']")).click();
		waitAndGetElement(By.cssSelector("input[id$='bottom:save']")).click();		
	}
	
	public WebElement waitAndGetElement(By locator) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

}