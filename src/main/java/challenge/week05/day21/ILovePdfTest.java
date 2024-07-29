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
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ILovePdfTest {
	
	/**
	 * 
	 * Automate the following test case for using file upload
	 * 
	 * TESTCASE:
	 * 
	 * > Open your preferred web browser.
	 * > Go to the I Love pdf official website "https://www.ilovepdf.com/"
	 * > Click the PDF to Word
	 * > Click the Select PDF
	 * > Upload your PDF file
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
		driver.get("https://www.ilovepdf.com/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
	}
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
	
    @Test
	public void testUploadFileUsingSendKeys() {		
		driver.findElement(By.cssSelector("a[title='PDF to Word']")).click();
		uploadFileUsingSendKeys(System.getProperty("user.dir")+"/upload/SoftwareTesting.pdf");		
		Assert.assertEquals(driver.findElement(By.cssSelector("span.file__info__name")).getText().trim(), "SoftwareTesting.pdf");
	}
	
	@Test
	public void testUploadFileUsingRobotClass() {
		driver.findElement(By.cssSelector("a[title='PDF to Word']")).click();
		driver.findElement(By.linkText("Select PDF file")).click();
		uploadFileUsigRobotClass(System.getProperty("user.dir")+"\\upload\\SoftwareTesting.pdf");		
		Assert.assertEquals(driver.findElement(By.cssSelector("span.file__info__name")).getText().trim(), "SoftwareTesting.pdf");
	}	
	
	public void uploadFileUsingSendKeys(String filePath) {
		driver.findElement(By.xpath("//div[text()='or drop PDF here']/following-sibling::div/input")).sendKeys(filePath);	
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