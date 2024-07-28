package challenge.week04.day19;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class MoglixTest {
	
	/**
	 * 
	 * Verify adding the lowest prices TV to cart on Monglix
	 * 
	 * TESTCASE:
	 * 
	 * > Launch the browser.
	 * > Navigate to the URL: "https://www.moglix.com/".
	 * > Search for "TV" using the search box.
	 * > Retrieve and parse the prices of all listed TVs.
	 * > Sort the list of TV prices in ascending order.
	 * > Find the TV with the lowest price and add it to the cart.
	 * > Open the cart.
	 * > Retrieve and print the total amount in the cart.
	 * > Close the browser.
	 * 
	 */
	
	FirefoxDriver driver;
	WebDriverWait wait;	
	List<Integer> prices = new ArrayList<Integer>();	
	
	@BeforeMethod
	public void setUp() {
		driver = new FirefoxDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		driver.manage().window().maximize();
		driver.navigate().to("https://www.moglix.com/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
	}

	@AfterMethod
	public void tearDown(ITestResult result) {
		driver.quit();
	}
	
	@Test
	public void validateUserAddTheLowestPriceTVToTheCart() {
		driver.findElement(By.id("search-input")).sendKeys("TV", Keys.ENTER);
		waitStalenessOfElement(By.cssSelector("div.prod-price-row > div[class^=prod-selling-price] > span"));
		List<WebElement> elements = driver.findElements(By.cssSelector("div.prod-price-row > div[class^=prod-selling-price] > span"));
		List<WebElement> productGrid = driver.findElements(By.cssSelector("div.product-vertical-grid-card.cursor.active-hover"));
		List<WebElement> buttons = driver.findElements(By.xpath("//button[starts-with(@class,'cart-btn')]"));
		for (WebElement element : elements) {
			prices.add(Integer.parseInt(element.getText().replaceAll("\\D", "")));
		}
		Collections.sort(prices);
		for (int i = 0; i < elements.size(); i++) {
			if(Integer.parseInt(elements.get(i).getText().replaceAll("\\D", "")) == prices.get(0)) {
				driver.executeScript("arguments[0].scrollIntoView();", productGrid.get(i));
				new Actions(driver)
				.moveToElement(productGrid.get(i))
				.click(buttons.get(i))
				.perform();
				break;
			}
		}
		waitAndGetElement(By.cssSelector("div[class^='cartNotification']")).findElement(By.linkText("VIEW CART")).click();
		Assert.assertEquals(Integer.parseInt(removeDecimalPointAndSpecialChar(waitAndGetElement(By.xpath("//span[text()='Amount Payable']/following-sibling::span")).getText())), prices.get(0));
		System.out.println("Total Amount is: "+waitAndGetElement(By.xpath("//span[text()='Total Amount']/following-sibling::span")).getText());
		System.out.println("Total GST is: "+waitAndGetElement(By.xpath("//span[text()='Total GST']/following-sibling::span")).getText());
		System.out.println("Total Shipping is: "+waitAndGetElement(By.xpath("//span[text()='Total Shipping']/following-sibling::span")).getText());
		System.out.println("Total Coupon Discount is: "+waitAndGetElement(By.xpath("//span[text()='Total Coupon Discount']/following-sibling::span")).getText());
		System.out.println("Amount Payment is: "+waitAndGetElement(By.xpath("//span[text()='Amount Payable']/following-sibling::span")).getText());
	}
	
	public void waitStalenessOfElement(By locator) {
		wait.until(ExpectedConditions.stalenessOf(driver.findElement(locator)));
	}
	
	public WebElement waitAndGetElement(By locator) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	public String removeDecimalPointAndSpecialChar(String price) {		
		return price.replaceAll(".00", "").replaceAll("\\D", "");
	}

}