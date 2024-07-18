package challenge.week03.day14;

import java.time.Duration;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class WindowSwtichTest {
	
	/**
	 * 
	 * Verify switching to child window using window title
	 * 
	 * TESTCASE:
	 * 
	 * > Launch the browser and load the URL as "https://www.irctc.co.in/nget/train-search".
	 * > Click on the "Buses" link.
	 * > Capture the window handles for the parent and child windows.
	 * > Switch to the child window using title of the window.
	 * > Print the current URL of the child window.
	 * > Quit the browser.
	 *
	 */
	
	ChromeDriver driver;
	WebDriverWait wait;
	String parentWindow;	
	
	@BeforeMethod
	public void setUp() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		options.addArguments("--incognito");
		driver = new ChromeDriver(options);
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		driver.manage().window().maximize();
		driver.navigate().to("https://www.irctc.co.in/nget/train-search");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		parentWindow = driver.getWindowHandle();
		System.out.println("Parent Window Handle Value is: "+parentWindow);
	}

	@AfterMethod
	public void tearDown(ITestResult result) {
		driver.quit();
	}
	
	@Test
	public void switchWindowTest() {		
		waitAndGetElement(By.xpath("//nav[starts-with(@class,'nav-bar')]/ul[@class='menu']/li/a[normalize-space(text())='BUSES']")).click();		
		System.out.println("Child Window Handle Value is: "+switchToChildWindow("IRCTC Bus - Online Bus Ticket Booking | Bus Reservation"));
		System.out.println("Child Window Page URL: "+driver.getCurrentUrl());		
	}
	
	public void ifConsentContainerEnabledHandleThat() {
		if(driver.findElements(By.className("fc-dialog-container")).size() != 0) {
			waitAndGetElement(By.xpath("//button[@aria-label='Consent']")).click();
		}
	}
	
	public WebElement waitAndGetElement(By locator) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	public void waitForChildWindow() {
		wait.until(ExpectedConditions.numberOfWindowsToBe(2));
	}
	
	public String switchToChildWindow(String title) {
		String childWindow = null;
		waitForChildWindow();
		Set<String> windowHandles = driver.getWindowHandles();
		for (String windowHandle : windowHandles) {
			if (driver.switchTo().window(windowHandle).getTitle().equals(title)) {
				childWindow = windowHandle;
				break;
			}
		}
		return childWindow;
	}

}