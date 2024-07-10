package challenge.week02.day08;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TelerikContactTest {

	/**
	 * 
	 * Automate the following Test Case to handle the dropdown using the Select Class 
	 * 
	 * Testcase:
	 * 
	 * > Launch the browser and Load Url as "https://www.telerik.com/contact" 
	 * > Select "Invoicing/Billing" from the first dropdown using selectByValue 
	 * > Choose "Testing Framework" from the dropdown 
	 * > Enter 'Your First Name' in the first name field 
	 * > Enter 'Your Last Name' in the last name field 
	 * > Select "United States" from the country dropdown using selectByIndex 
	 * > Quit the driver
	 * 
	 */

	ChromeDriver driver;
	WebDriverWait wait;

	@BeforeMethod
	public void setUp() {
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		driver.manage().window().maximize();
		driver.get("https://www.telerik.com/contact");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
	}

	@AfterMethod
	public void tearDown(ITestResult result) {
		if(result.isSuccess()) {
			takeScreenShot("telerik-contact-success");
		}
		driver.quit();		
	}	

	@Test
	public void testDropDownHandlingInTelerikContactPage() {
		accpectCookieBanner();
		dropDown("Dropdown-1").selectByValue("Invoicing/Billing");
		takeScreenShot("Dropdown-1");
		Actions actions = new Actions(driver);
		actions.scrollToElement(driver.findElement(By.xpath("//select[@id='Dropdown-2']/option[@value='Testing Framework']")));
		dropDown("Dropdown-2").selectByVisibleText("Testing Framework");
		takeScreenShot("Dropdown-2");
		type("Textbox-1", "Karthikeyan");
		type("Textbox-2", "Rajendran");
		type("Email-1", "karthikeyan.rajendran@testleaf.com");
		type("Textbox-3", "TestLeaf");
		dropDown("Country-1").selectByIndex(1);
		dropDown(waitAndGetElement(By.id("State-1"))).selectByValue("NY");
		type("Textbox-4", "7894561235");
		type("Textarea-1", "Test Message");
	}

	public void accpectCookieBanner() {
		if (driver.findElement(By.id("onetrust-banner-sdk")).isDisplayed()) {
			driver.executeScript("arguments[0].click();", driver.findElement(By.id("onetrust-accept-btn-handler")));
		}
	}

	public Select dropDown(String id) {
		return new Select(driver.findElement(By.id(id)));
	}

	public Select dropDown(WebElement element) {
		return new Select(element);
	}

	public void type(String id, String value) {
		driver.findElement(By.id(id)).sendKeys(value);
	}

	public WebElement waitAndGetElement(By locator) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	public void takeScreenShot(String filename) {
		File src = driver.getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(src, new File("./images/"+filename+".png"));
		} catch (IOException e) {				
			e.printStackTrace();
		}
	}
	
	@AfterClass
	public void afterClass() {
		System.out.println("Use the screenshots in './images' folder for the verification. Thank you!");
	}

}