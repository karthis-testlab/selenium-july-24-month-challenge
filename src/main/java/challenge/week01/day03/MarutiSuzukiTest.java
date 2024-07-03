package challenge.week01.day03;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class MarutiSuzukiTest {
	
	/**
	 * Automate the following TestCase using only basic locators
	 * 
	 * TESTCASE: Maruthi Suzuki
	 * 
	 * Step 1: Open your preferred web browser.
	 * Step 2: Go to Maruthi Suzuki's official website "https://www.marutisuzuki.com/"
	 * 
	 */

	ChromeDriver driver;
	WebDriverWait wait;

	@BeforeMethod
	public void setUp() {
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		driver.manage().window().maximize();
		driver.get("https://www.marutisuzuki.com/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
	}

	@AfterMethod
	public void tearDown() {
		// driver.close();
	}

	@Test
	public void vaildateSearchFunctionalityAndProductExplore() {
		driver.findElement(By.cssSelector("li.headerSearch-btn > img")).click();
		driver.findElement(By.cssSelector("input[placeholder='Search']")).sendKeys("Swift", Keys.ENTER);
		waitAndGetElement(By.className("icon-general_info")).click();
		Select cityDropdwon = new Select(waitAndGetElement(By.id("selectcity1")));
		int totalCity = cityDropdwon.getOptions().size();
		int lastCity = totalCity - 1;
		System.out.println(totalCity);
		System.out.println(cityDropdwon.getOptions().get(lastCity).getText());
		cityDropdwon.selectByIndex(lastCity);
		waitAndGetElement(By.xpath("//a[normalize-space(text())='SAFETY']")).click();
		System.out.println(waitAndGetElement("div.safety-dis > p:first-child").getText());
		System.out.println(waitAndGetElement("div.safety-dis > p:last-child").getText());
		waitAndGetElement(By.className("backToTop")).click();
		scrollToElement(waitAndGetElement(By.partialLinkText("COLOURS")));
		waitAndGetElement(By.partialLinkText("COLOURS")).click();
	}

	public WebElement waitAndGetElement(String locator) {
		return wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.cssSelector(locator)));
	}

	public WebElement waitAndGetElement(By locator) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	public void scrollToElement(WebElement element) {
		driver.executeScript("arguments[0].scrollIntoView();", element);
	}

}