package challenge.week04.day16;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ScreenshotTest {
	
	/**
	 * 
	 * Take a Snapshot of the page and save it with a unique file name
	 * 
	 * TESTCASE:
	 * 
	 * > Launch the browser.
	 * > Load the URL as "https://www.codehim.com/demo/single-page-shopping-cart-template/".
	 * > Select First Image, Click "Add to Cart" and take a Snapshot.
	 * > Select First Image, Result click "View Gallery" and take a Snapshot.
	 * > Print the current URL of the child window.
	 * > Quit the browser.
	 *
	 */
	
	ChromeDriver driver;
	WebDriverWait wait;
	String pattern = "ddmmyyyyHHMMssSSSS";
	
	@BeforeMethod
	public void setUp() {
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		driver.manage().window().maximize();
		driver.navigate().to("https://www.codehim.com/demo/single-page-shopping-cart-template/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
	}

	@AfterMethod
	public void tearDown(ITestResult result) {
		driver.quit();
	}
	
	@Test
	public void testScreenshotActivity() {
		new Actions(driver)
		.moveToElement(driver.findElement(By.cssSelector("div#grid > div.product:first-child")))
		.perform();
		driver.findElements(By.xpath("//div[text()='Add to cart']")).get(0).click();
		waitUntilElementInvisible(By.cssSelector("div#cart > span"));
		takeScreenShot();
		driver.findElements(By.xpath("//div[text()='View gallery']")).get(0).click();
		waitUntilElementIsVisible(By.xpath("(//div[@class='flip-back'])[1]"));
		takeScreenShot();
	}
	
	public void takeScreenShot() {
		File src = driver.getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(src, new File(System.getProperty("user.dir")+"/images/"+new SimpleDateFormat(pattern).format(new Date())+".png"));
		} catch (IOException e) {				
			e.printStackTrace();
		}
	}
	
	public void waitUntilElementInvisible(By locator) {
		wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
	}

	public void waitUntilElementIsVisible(By locator) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

}