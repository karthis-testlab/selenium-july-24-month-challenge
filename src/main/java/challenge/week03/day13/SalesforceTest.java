package challenge.week03.day13;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SalesforceTest {
	
	/**
	 * 
	 * Handle WebElement Present Inside The Frame By Using Selenium WebDriver
	 * 
	 * TESTCASE:
	 * 
	 * > Load the URL
	 *   i) Open your preferred web browser.
	 *   ii) Navigate to the URL: "https://login.salesforce.com/".
	 * > Use the following credentials for login, Username as "<username>" and password as "<password>".
	 * > Click on the App launcher and search as 'Opportunities'.
	 * > Open the first available Opportunity.
	 * > Click on the Email icon under the Activity tab.
	 * > Enter your email ID in the To Address field.
	 * > Enter the Content as "I have completed the Selenium Challenge 13"
	 * > Click on the Send Button and Close the browser.
	 *
	 */
	
	ChromeDriver driver;
	WebDriverWait wait;
	
	@BeforeMethod
	public void setUp() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		options.addArguments("--incognito");
		driver = new ChromeDriver(options);
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		driver.manage().window().maximize();
		driver.navigate().to("https://login.salesforce.com/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
	}

	@AfterMethod
	public void tearDown(ITestResult result) {
		driver.close();
	}

	@Test
	public void validateEmailSendInOpportunitiesaAndHandleFrames() {
		driver.findElement(By.id("username")).sendKeys("gokul.sekar@testleaf.com");
		driver.findElement(By.id("password")).sendKeys("Leaf$123");
		driver.findElement(By.id("Login")).click();		
		driver.findElement(By.xpath("//button[@title='App Launcher']/div[@class='slds-icon-waffle']")).click();
		waitAndGetElement(By.xpath("//input[starts-with(@placeholder,'Search apps and items')]")).sendKeys("Opportunities");
		driver.executeScript("arguments[0].click();", waitAndGetElement(By.xpath("//a[.='Opportunities']")));
		ifViewSplitChangeToTable();
		waitAndGetElement(By.xpath("//table/tbody/tr[1]/th//a")).click();
		waitAndGetElement(By.xpath("//button[@title='Email']/span")).click();		
		waitAndGetElement(By.xpath("//span[text()='To']/../following-sibling::div[@class='standardField']//input")).sendKeys("karthike.selene@gmail.com");
		waitAndGetElement(By.xpath("//span[text()='Subject']/../following-sibling::input")).sendKeys("Reg: July Selenium Challenge");
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@title='CK Editor Container']")));
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@title='Email Body']")));
		waitAndGetElement(By.xpath("//body[@aria-label='Email Body']")).sendKeys("I have completed the Selenium Challenge 13");
		driver.switchTo().defaultContent();
		waitAndGetElement(By.xpath("//span[text()='Send']/parent::button")).click();
		Assert.assertEquals(waitAndGetElement(By.xpath("//span[starts-with(@class,'toastMessage')]")).getText(), "Email was sent.");
	}
	
	public WebElement waitAndGetElement(By locator) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	public void ifViewSplitChangeToTable() {
		if(driver.findElement(By.xpath("//div[starts-with(@class,'slds-split-view') and contains(@class,'test-listViewManager')]")).isEnabled()) {
			driver.findElement(By.xpath("//button[@title='Select list display']")).click();
			waitAndGetElement(By.xpath("//li[@title='Display as table']/a")).click();
		}
	}

}