package challenge.week05.day22;

import java.io.File;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DownloadFileTest {
	
	ChromeDriver driver;
	WebDriverWait wait;
	File folder = new File(System.getProperty("user.dir")+"/download");	
	
	@BeforeMethod
	public void setUp() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("profile.default_conten_settings.popups", 0);
		map.put("download.default_directory", getDownloadAbsolutePath());
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-search-engine-choice-screen");
		options.setExperimentalOption("prefs", map);
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.get("https://the-internet.herokuapp.com/download");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
	}
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
		deleteAllFile();
	}
	
	@Test
	public void testDownloadFileToLocal() throws InterruptedException {
		driver.findElement(By.linkText("Screenshot_20230306_101426.png")).click();
		Thread.sleep(1000);
		Assert.assertTrue(isFilePresent("Screenshot_20230306_101426.png"));
	}
	
	public String getDownloadAbsolutePath() {		
		if(!folder.exists()) {
			folder.mkdir();
		}
		return folder.getAbsolutePath();
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

}