package challenge.week03.day11;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class EspnCricInfoTest {
	
	/**
	 * 
	 * Automate the following Test Case using different ways to scroll to an element and interact
	 * 
	 * TESTCASE:
	 * 
	 * > Launch the browser and Load URL as "https://www.espncricinfo.com/".
	 * > Click on first result under the Key Series.
	 * > Print the text of the first resulting add.
	 * > Quit the driver.
	 * 
	 * https://www.selenium.dev/documentation/webdriver/actions_api/wheel/
	 * https://github.com/SeleniumHQ/seleniumhq.github.io/blob/trunk/examples/java/src/test/java/dev/selenium/actions_api/WheelTest.java#L84
	 * 
	 */
	
	ChromeDriver driver;
	WebDriverWait wait;

	@BeforeMethod
	public void setUp() {
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		driver.manage().window().maximize();
		driver.get("https://www.espncricinfo.com/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
	}

	@AfterMethod
	public void tearDown(ITestResult result) {		
		driver.quit();
	}	
	
	@Test
	public void scrollTest() {		
		
		waitAndGetElement(By.cssSelector("div#onetrust-close-btn-container > button")).click();
		
		inViewport(waitAndGetElement(By.xpath("//h2[text()='Key Series']")));
		
		new Actions(driver)
		.scrollToElement(waitAndGetElement(By.xpath("//h2[text()='Key Series']")))
		.perform();
		
		inViewport(waitAndGetElement(By.xpath("//h2[text()='Key Series']")));
		
		waitAndGetElement(By.xpath("//h2[text()='Key Series']/../../following-sibling::div//a[1]")).click();
		
		driver.executeScript("arguments[0].scrollIntoView();", waitAndGetElement(By.xpath("//div[@class='feed']//h2[contains(@class,'ds-text-raw-white')]")));
		
		System.out.println(waitAndGetElement(By.xpath("//div[@class='feed']//h2[contains(@class,'ds-text-raw-white')]")).getText());
		
	}
	
	public WebElement waitAndGetElement(By locator) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	private void inViewport(WebElement element) {

        String script =
                "for(var e=arguments[0],f=e.offsetTop,t=e.offsetLeft,o=e.offsetWidth,n=e.offsetHeight;\n"
                        + "e.offsetParent;)f+=(e=e.offsetParent).offsetTop,t+=e.offsetLeft;\n"
                        + "return f<window.pageYOffset+window.innerHeight&&t<window.pageXOffset+window.innerWidth&&f+n>\n"
                        + "window.pageYOffset&&t+o>window.pageXOffset";

        System.out.println(driver.executeScript(script, element));
}

}