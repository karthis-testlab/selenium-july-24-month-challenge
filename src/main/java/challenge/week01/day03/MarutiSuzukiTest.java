package challenge.week01.day03;

import java.time.Duration;
//import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
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
	 * Step 2: Go to Maruthi Suzuki's official website "https://www.marutisuzuki.com/".
	 * Step 3: Click the search icon.
	 * Step 4: Type "Swift" in the search field and press Enter.
	 * Step 5: Click on the General Info icon for the Swift product.
	 * Step 6: Count the number of options in the city dropdown list.
	 * Step 7: Print the last options in the city dropdown list and select it.
	 * Step 8: Click on the Safety section.
	 * Step 9: Print the description under the Safety section.
	 * Step 10: Click on the Colours section.
	 * Step 11: Print the currently selected color.
	 * Step 12: Close the browser.
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
		driver.close();
	}

	@Test
	public void vaildateSearchFunctionalityAndProductExplore() {
		//driver.findElement(By.cssSelector("li.headerSearch-btn > img")).click();
		driver.findElement(By.className("headerSearch-btn")).click();
		//driver.findElement(By.cssSelector("input[placeholder='Search']")).sendKeys("Swift", Keys.ENTER);
		driver.findElement(By.name("key")).sendKeys("Swift", Keys.ENTER);
		//waitAndGetElement(By.className("icon-general_info")).click();
		driver.findElement(By.className("icon-general_info")).click();
		//Select cityDropdwon = new Select(waitAndGetElement(By.id("selectcity1")));
		Select cityDropdwon = new Select(driver.findElement(By.id("selectcity1")));
		int totalCity = cityDropdwon.getOptions().size();
		int lastCity = totalCity - 1;
		System.out.println("Total number of the cities in the city dropdown list: "+totalCity);
		System.out.println("Last city in the city dropdown list is: "+cityDropdwon.getOptions().get(lastCity).getText());
		cityDropdwon.selectByIndex(lastCity);
		Actions actions = new Actions(driver);
		/* actions.moveToElement(waitAndGetElement(By.xpath("//a[normalize-space(text())='SAFETY']")))
		       .click().perform(); */
		actions.moveToElement(driver.findElement(By.partialLinkText("SAFETY")))
	       .click().perform();
		System.out.println("The description under the Safety section;");
	    /* System.out.println(waitAndGetElement("div.safety-dis > p:first-child").getText());
		System.out.println(waitAndGetElement("div.safety-dis > p:last-child").getText()); */
		System.out.println(driver.findElement(By.className("caption-text")).getText());
		// waitAndGetElement(By.className("backToTop")).click();		
		/* actions.moveToElement(waitAndGetElement(By.partialLinkText("COLOURS")))
		       .click().perform(); */
		actions.moveToElement(driver.findElement(By.partialLinkText("COLOURS")))
	       .click().perform();
		/* List<WebElement> elements = driver.findElements(By.cssSelector("div[class^='colorName'] > p > span"));
		for (WebElement element : elements) {
			if(element.isDisplayed()) {
				System.out.println("Currently selected color is: "+element.getText());
				break;
			}	
		
		} */
		String text = driver.findElement(By.className("carcolors active-tab")).getText();
		String[] colors = text.split("-");
		if(colors.length == 4) {
			System.out.println("Currently selected car color is: "+colors[1] +" & "+colors[3]);
		} else {
			System.out.println("Currently selected car color is: "+colors[1]);
		}
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
	
	public void scrollToElement(By locator) {
		driver.executeScript("arguments[0].scrollIntoView();", wait.until(ExpectedConditions.presenceOfElementLocated(locator)));
	}

}