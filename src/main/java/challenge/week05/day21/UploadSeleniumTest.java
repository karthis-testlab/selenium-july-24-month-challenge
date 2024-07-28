package challenge.week05.day21;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class UploadSeleniumTest {
	
	ChromeDriver driver;
	WebDriverWait wait;
	
	@BeforeMethod
	public void setUp() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-search-engine-choice-screen");
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.get("https://the-internet.herokuapp.com/upload");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
	}
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
	
	@Test
	public void testUploadFileUsingSendKeys() {		
		uploadFileUsingSendKeys(System.getProperty("user.dir")+"/images/telerik-contact-success.png");
		driver.findElement(By.id("file-submit")).click();
		Assert.assertEquals(driver.findElement(By.id("uploaded-files")).getText().trim(), "telerik-contact-success.png");
	}
	
	@Test
	public void testUploadFileUsingRobotClass() {
		new Actions(driver).moveToElement(driver.findElement(By.id("file-upload"))).click().perform();
		uploadFileUsigRobotClass(System.getProperty("user.dir")+"\\images\\telerik-contact-success.png");
		driver.findElement(By.id("file-submit")).click();
		Assert.assertEquals(driver.findElement(By.id("uploaded-files")).getText().trim(), "telerik-contact-success.png");
	}	
	
	public void uploadFileUsingSendKeys(String filePath) {
		driver.findElement(By.id("file-upload")).sendKeys(filePath);		
	}
	
	public void uploadFileUsigRobotClass(String filePath) {		
		try {
			Robot robot = new Robot();
			robot.setAutoDelay(1000);
			StringSelection file_to_upload = new StringSelection(filePath);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(file_to_upload, null);			
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);			
			robot.keyPress(KeyEvent.VK_ENTER);		
		} catch (AWTException e) {
			e.printStackTrace();
		}
		
	}

}