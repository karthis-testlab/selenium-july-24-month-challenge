package challenge.week04.day17;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CheapOairTest {
	
	/**
	 * 
	 * Automate The Test Case For Selecting Tomorrow's Date From The Clander
	 * 
	 * TEST STEPS:
	 * 
	 * Step 1: Launch your preferred browser.
	 * Step 2: Load CheapOair "https://www.cheapoair.com/".
	 * Step 3: Choose One Way trip.
	 * Step 4: Enter the From as an "MAA".
	 * Step 5: Choose the first resulting city.
	 * Step 6: Enter the From as a "DXB".
	 * Step 7: Choose the first resulting city.
	 * Step 8: Choose the Depart date as a tomorrow's date.
	 * Step 9: Add one adult and senior in Travelers.
	 * Step 10: Click the Done button.
	 * Step 11: Click the Search Flights button.
	 * Step 12: Close the alert.
	 * Step 13: Write a logic to find the lowest price flight and print
	 * Step 14: Close the browser.
	 * 
	 */	
	
	ChromeDriver driver;
	WebDriverWait wait;
    String pattern = "dd MMMM YYYY";	
	
	@BeforeMethod
	public void setUp() {
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		driver.manage().window().maximize();
		driver.get("https://www.cheapoair.com/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
	}

	@AfterMethod
	public void tearDown(ITestResult result) {
		driver.quit();
	}
	
	@Test
	public void testTomorrowDateSelectionAndPrintLowestPrice(){		
		constantWait(2000);
		driver.executeScript("arguments[0].click();", driver.findElement(By.xpath("//label[@for='onewayTrip']/..")));		
		driver.findElement(By.xpath("//input[starts-with(@id,'from')]")).click();
		driver.findElement(By.xpath("//input[starts-with(@id,'from')]")).sendKeys(Keys.DELETE);
		driver.findElement(By.xpath("//input[starts-with(@id,'from')]")).sendKeys("MAA");
		driver.findElement(By.xpath("//input[starts-with(@id,'from')]/../section[@class='suggestion-box']//ul/li[@data-suggestion][1]")).click();		
		driver.findElement(By.xpath("//input[starts-with(@id,'to')]")).sendKeys(Keys.CONTROL,"a",Keys.DELETE);
		driver.findElement(By.xpath("//input[starts-with(@id,'to')]")).sendKeys("DXB");
		driver.findElement(By.xpath("//input[starts-with(@id,'to')]/../section[@class='suggestion-box']//ul/li[@data-suggestion][1]")).click();	
		new Actions(driver).moveToElement(driver.findElement(By.xpath("//a[@aria-label='"+fetchTomorrowsDate()+"']")))
		.click().perform();
		driver.findElement(By.id("travellerButton")).click();
		driver.findElement(By.id("addseniors")).click();
		driver.findElement(By.id("closeDialog")).click();
		driver.findElement(By.id("searchNow")).click();
		driver.findElement(By.cssSelector("a.close-icon")).click();	       
	    System.out.println("The lowest price for the "+fetchTomorrowsDate()+" is: "+fecthLowestPrice());
	}
	
	public Double fecthLowestPrice() {
		waitUntilAllRecordLoad(By.xpath("//span[contains(@class,'currency') and @title]"));
		return driver.findElements(By.xpath("//span[contains(@class,'currency') and @title]")).stream()
	              .map(price -> Double.parseDouble(price.getAttribute("title")))
	              .distinct()
	              .min(Comparator.naturalOrder())
	              .get();
	}
	
	public void waitUntilAllRecordLoad(By locator) {
		wait.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver input) {
				if (input.findElements(locator).size() == 20) {
					return true;
				} else {
					return false;
				}
			}
		});
	}
	
	public void waitUntilElementInvisible(By locator) {
		wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));		
	}
	
	public WebElement waitAndGetElement(By locator) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	public void constantWait(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
	}
	
	public void waitForAllElements(By locator) {
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}
	
	public void ifModalConsentContainerEnabledHandleThat() {
		if(driver.findElements(By.className("modal-content")).size() != 0) {
			waitAndGetElement(By.className("modal-close-icon")).click();
		}
	}
	
	public String fetchTomorrowsDate() {
		return DateTimeFormatter.ofPattern(pattern).format(LocalDate.now().plusDays(1));		
	}

}