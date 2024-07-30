package challenge.week05.day22;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.time.Duration;
import java.util.Arrays;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DownloadFileTest {
	
	/**
	 * 
	 * Download the file by using selenium webdriver
	 * 
	 * TESTCASE:
	 * 
	 * > Launch Chrome Browser in Incognito mode.
	 * > Load the URL as "https://pontoon.mozilla.org/".
	 * > Click on 'Start Localizing Now'.
	 * > Enter 'India' in the filter.
	 * > Click on 'Bengali (India)'.
	 * > Click on 'Download Terminology'.
	 * > Save the downloaded file into the files folder under the current project directory and name it "terminology.tbx".
	 * > Close the browser.
	 * 
	 */	
	
	ChromeDriver driver;
	WebDriverWait wait;
	File folder = new File(System.getProperty("user.dir")+"/download");
	File download_file = new File(System.getProperty("user.dir")+"/download/terminology");	
	
	@BeforeMethod
	public void setUp() {		
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-search-engine-choice-screen");
		options.addArguments("--incognito");
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.get("https://pontoon.mozilla.org/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
	}
	
	@AfterMethod
	public void tearDown() {
		driver.quit();		
		deleteAllFile();
	}
	
	@Test
	public void testDownloadFileToLocal() {
		driver.findElement(By.linkText("Teams")).click();
		driver.findElement(By.className("table-filter")).sendKeys("India", Keys.ENTER);
		driver.findElement(By.linkText("Bengali (India)")).click();
		driver.findElement(By.xpath("//div[starts-with(@class, 'download-selector')]/div[starts-with(@class,'button')]/i")).click();
		driver.findElement(By.linkText("Download Terminology")).click();		
		saveFileIntoProjectDir(getDownloadAbsolutePath());	
		Assert.assertTrue(isFilePresent("terminology.tbx"));
	}
	
	public void saveFileIntoProjectDir(String downloadPath) {
		try {
			Robot robot = new Robot(); 
			StringSelection stringSelection = new StringSelection(downloadPath); 
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
			robot.setAutoDelay(500);
			robot.keyPress(KeyEvent.VK_CONTROL); 
			robot.keyPress(KeyEvent.VK_V); 
			robot.keyRelease(KeyEvent.VK_V); 
			robot.keyRelease(KeyEvent.VK_CONTROL); 
			robot.setAutoDelay(500);
			robot.keyPress(KeyEvent.VK_ENTER); 
			robot.keyRelease(KeyEvent.VK_ENTER);
		} catch (HeadlessException e) {			
			e.printStackTrace();
		} catch (AWTException e) {			
			e.printStackTrace();
		}
	}
	
	public String getDownloadAbsolutePath() {	
		if(!folder.exists()) {
			folder.mkdir();
		}
		return download_file.getAbsolutePath();
	}
	
	public boolean isFilePresent(String fileName) {			
		return Arrays.asList(folder.list()).contains(fileName);
	}
	
	public void deleteAllFile() {		
		File[] files = folder.listFiles();
		for (File file : files) {
			file.deleteOnExit();
		}		
	}	
	
	public static void main(String[] args) {
		DownloadFileTest download = new DownloadFileTest();
		download.deleteAllFile();
	}

}