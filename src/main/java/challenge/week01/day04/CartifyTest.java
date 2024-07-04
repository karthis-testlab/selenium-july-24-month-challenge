package challenge.week01.day04;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CartifyTest {
	
	/*
	 * Automate the following Test case using only XPATH
	 * 
	 * TESTCASE: Verify Adding Floor-Standing Cloth Rack to Cart and Retrieving Subtotal
	 * 
	 * > Launch the browser and Load URL as "https://www.cartify.pk"
	 * > Click on "Shop By Categories"
	 * > Click on "Cloth Rack" category
	 * > Click on the "Floor-Standing Clock Rack With Wheels" item
	 * > Add the item to the cart
	 * > Click on Add to cart
	 * > Retrieve the "subtotal price"
	 * > Quit the driver
	 * 
	 */
	
	ChromeDriver driver;
	WebDriverWait wait;

	@BeforeMethod
	public void setUp() {
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		driver.manage().window().maximize();
		driver.get("https://www.cartify.pk/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
	
	@Test
	public void validateTotalPriceIntheAddToCartPage() {
		waitAndGetElement("//ul[@id='SiteNav']//span[text()='Shop By Categories']").click();
		waitAndGetElement("//div[normalize-space(text())='Cloth Rack']").click();
		waitAndGetElement("//span[text()='Floor-Standing Cloth Rack With Wheels']/..").click();
		waitAndGetElement("//button[@name='add']").click();
		waitUntilElementInvisible("//*[local-name()='svg' and contains(@class, 'icon-spinner')]");
		waitAndGetElement("//div[@class='cart-popup']/a").click();
		System.out.println("The subtotal price is: "+waitAndGetElement("//div[@class='cart-subtotal']/span[contains(@class,'price')]").getText());
	}
	
	public WebElement waitAndGetElement(String locator) {
		return wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath(locator)));
	}
	
	public void waitUntilElementInvisible(String locator) {
		wait.until(ExpectedConditions
				.invisibilityOfElementLocated(By.xpath(locator)));
	}
	
	public void scrollToElement(WebElement element) {
		driver.executeScript("arguments[0].scrollIntoView();", element);
	}

}