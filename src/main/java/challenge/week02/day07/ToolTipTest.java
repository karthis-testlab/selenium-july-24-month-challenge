package challenge.week02.day07;

import java.time.Duration;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ToolTipTest {

	ChromeDriver driver;
	WebDriverWait wait;

	@BeforeMethod
	public void setUp() {
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		driver.manage().window().maximize();
		driver.navigate().to("https://jqueryui.com/tooltip/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
	}

	@AfterMethod
	public void tearDown() {
		driver.close();
	}

	@Test
	public void validateTooltipTextInJQueryUITooltip() {
		driver.switchTo().frame(driver.findElement(By.className("demo-frame")));
		Actions actions = new Actions(driver);
		actions.moveToElement(driver.findElement(By.linkText("Tooltips"))).perform();
		Assert.assertEquals(driver.findElement(By.cssSelector("div.ui-tooltip-content")).getText(), "That's what this widget is");
		actions.moveToElement(driver.findElement(By.linkText("ThemeRoller"))).perform();		
		wait.until(ExpectedConditions.textMatches(By.cssSelector("div.ui-tooltip-content"),
				Pattern.compile("ThemeRoller: (\\w+)")));
		Assert.assertEquals(driver.findElement(By.cssSelector("div.ui-tooltip-content")).getText(), "ThemeRoller: jQuery UI's theme builder application");
		actions.moveToElement(driver.findElement(By.id("age"))).perform();
		wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("div.ui-tooltip-content"), " age "));
		Assert.assertEquals(driver.findElement(By.cssSelector("div.ui-tooltip-content")).getText(), "We ask for your age only for statistical purposes.");
	}

}