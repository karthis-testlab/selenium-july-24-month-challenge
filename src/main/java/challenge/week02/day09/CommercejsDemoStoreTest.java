package challenge.week02.day09;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CommercejsDemoStoreTest {
	
	/**
	 * 
	 * Verify the product is added in cart without exception
	 * 
	 * TESTCASE:
	 * 
	 * # Launch the Browser and Load the URL "https://commercejs-demo-store.netlify.app/".
	 * # Click on the Shop button.
	 * # Click on the products under Hair products.
	 * # Click on the shampoo conditioner set.
	 * # Click to Add to Cart button
	 * 
	 */	

	ChromeDriver driver;
	WebDriverWait wait;

	@BeforeMethod
	public void setUp() {
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		driver.manage().window().maximize();
		driver.get("https://commercejs-demo-store.netlify.app/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
	}

	@AfterMethod
	public void tearDown(ITestResult result) {
		driver.quit();
	}
	
	@Test
	public void validateTheGivenProductIsAddedInCart() {
		driver.findElement(By.linkText("Shop now")).click();
		driver.findElement(By.xpath("//p[text()='Hair Products']/following-sibling::a")).click();
		List<WebElement> elements = driver
				.findElements(By.xpath("//p[@id='hair-products']/following-sibling::div/div/a/p[1]"));
		for (WebElement element : elements) {
			if (element.getText().equals("Shampoo & Conditioner Set")) {
				element.click();
				break;
			}
		}
		waitUnitElementInvisibility(By.xpath("//h4[text()='Loading...']"));
		driver.findElement(By.xpath("//button/span[.='Add to cart']")).click();
		driver.findElement(By.className("cart-animation")).click();
		Assert.assertEquals(waitAndGetElement(By.xpath("(//div[starts-with(@class,'cart-item')]//p)[1]")).getText(),
				"Shampoo & Conditioner Set");
	}
	
	public WebElement waitAndGetElement(By locator) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	public void waitUnitElementInvisibility(By locator) {
		wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
	}

}