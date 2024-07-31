package challenge.week05.day23;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class PolymerWebSiteShadowDomTest {
	
	/**
	 * 
	 * Interact Element in a shadow DOM
	 * 
	 * TESTSTEP:
	 * 
	 * STEP 1: Launch your preferred browser.
	 * STEP 2: Go to the Polymer website "https://shop.polymer-project.org/".
	 * Step 3: Scroll Down to "Men's T-shirt".
	 * Step 4: Click the "Shop Now" button under the "Men's T-shirt".
	 * Step 5: Print the total number of items.
	 * Step 6: Print all T-shirt prices and names.
	 * Step 7: Click the first T-shirt and print the T-shirt price.
	 * Step 8: Compare both price to see if they're same.
	 * Step 9: Select size "XL" and Select quantity as "2".
	 * Step 10: Click the "Add to Cart" button.
	 * Step 11: Click the "View Cart" button and print the subtotal.
	 * Step 12: Close the browser.
	 * 
	 */
	
	ChromeDriver driver;
	WebDriverWait wait;
	
	@BeforeMethod
	public void setUp() {		
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-search-engine-choice-screen");
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.get("https://shop.polymer-project.org/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
	}
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}	
	
	@Test
	public void testShadowDomInPolymwerEcommerceWebSite() throws InterruptedException {
		WebElement shadowHost = driver.findElement(By.cssSelector("shop-app[page='home']"));
		SearchContext shadowRootHome = shadowHost.getShadowRoot();
		SearchContext shadowRootHome2 = shadowRootHome.findElement(By.cssSelector("shop-home.iron-selected")).getShadowRoot();
		List<WebElement> list = shadowRootHome2.findElements(By.cssSelector("div.item > h2"));
		List<WebElement> shopButtons = shadowRootHome2.findElements(By.cssSelector("div.item > shop-button"));
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).getText().equals("Men's T-Shirts")) {
				new Actions(driver).scrollToElement(shopButtons.get(i)).click(shopButtons.get(i)).perform();
				break;
			}
		}
		Thread.sleep(1000);
		SearchContext shadowRootList = driver.findElement(By.cssSelector("shop-app[page='list']")).getShadowRoot();
		SearchContext shadowRootList2 = shadowRootList.findElement(By.cssSelector("shop-list.iron-selected")).getShadowRoot();
		List<WebElement> items = shadowRootList2.findElements(By.cssSelector("ul.grid > li > a > shop-list-item"));
		System.out.println("Total Number of items are: "+items.size());
		for (WebElement item : items) {
			SearchContext shadowRoot = item.getShadowRoot();
			System.out.println("T-shirt Name is: "+shadowRoot.findElement(By.cssSelector("div.title")).getText());
			System.out.println("T-shirt Price is: "+shadowRoot.findElement(By.cssSelector("span.price")).getText());
		}
		String firstItemPrice = items.get(0).getShadowRoot().findElement(By.cssSelector("span.price")).getText();
		System.out.println("The price of the first T-shirt is: "+firstItemPrice);
		items.get(0).click();
		Thread.sleep(1000);
		SearchContext shadowRootDetail = driver.findElement(By.cssSelector("shop-app[page='detail']")).getShadowRoot();
		SearchContext shadowRootDetail2 = shadowRootDetail.findElement(By.cssSelector("shop-detail.iron-selected")).getShadowRoot();
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(shadowRootDetail2.findElement(By.cssSelector("div#content > div.detail > div.price")).getText(), firstItemPrice);
		Select sizeSelect = new Select(shadowRootDetail2.findElement(By.cssSelector("div#content > div.detail > div.pickers > shop-select > select#sizeSelect")));
		sizeSelect.selectByVisibleText("XL");
		Select quantitySelect = new Select(shadowRootDetail2.findElement(By.cssSelector("div#content > div.detail > div.pickers > shop-select > select#quantitySelect")));
		quantitySelect.selectByVisibleText("2");
		shadowRootDetail2.findElement(By.cssSelector("div#content > div.detail > shop-button > button")).click();
		Thread.sleep(800);
		SearchContext shadowRootCartModal = shadowRootDetail.findElement(By.cssSelector("shop-cart-modal[role='dialog']")).getShadowRoot();
		shadowRootCartModal.findElement(By.cssSelector("div.layout-horizontal > shop-button > a#viewCartAnchor")).click();
		Thread.sleep(800);
		SearchContext shadowRootCart = driver.findElement(By.cssSelector("shop-app[page='cart']")).getShadowRoot();
		SearchContext shadowRootCart1 = shadowRootCart.findElement(By.cssSelector("shop-cart[name='cart']")).getShadowRoot();
		System.out.println("The subtotal is: "+shadowRootCart1.findElement(By.cssSelector("span.subtotal")).getText());
	}
	
	public WebElement waitAndGetElement(By locator) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

}