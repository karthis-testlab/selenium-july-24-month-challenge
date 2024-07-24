package challenge.week04.day18;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class YahooFinanceMarketTest {	
	
	/**
	 * Read the respective value from the web table for given input
	 * 
	 * TESTCASE:
	 * 
	 * > Open your preferred web browser.
	 * > Navigate to the URL: "https://finance.yahoo.com/most-active/".
	 * > Identify the minimum value among the price listed.
	 * > Print the company name, price and market cap for the company with the lowest price.
	 * 
	 */	
	
	FirefoxDriver driver;
	WebDriverWait wait;	
	
	@BeforeMethod
	public void setUp() {
		driver = new FirefoxDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		driver.manage().window().maximize();
		driver.navigate().to("https://finance.yahoo.com/most-active/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
	}

	@AfterMethod
	public void tearDown(ITestResult result) {
		driver.quit();
	}
	
	@Test
	public void toPrintTheCompanyNameMarketCapPriceOfLowestOne() {
		if (!driver.getCurrentUrl().endsWith("/markets/stocks/most-active/")) {
			System.out.println("The lowest price company name is: " + getParticularRow(getRowIndexBasedLowestPrice())
					.findElement(By.cssSelector("td[aria-label^='Name']")).getText());
			System.out.println("The lowest price market cap is: " + getParticularRow(getRowIndexBasedLowestPrice())
					.findElement(By.cssSelector("td[aria-label^='Market Cap'] > fin-streamer")).getText());
			System.out.println("The lowest price is: " + getParticularRow(getRowIndexBasedLowestPrice())
					.findElement(By.cssSelector("td[aria-label^='Price'] > fin-streamer")).getText());
		} else {
			System.out.println("The lowest price company name is: " + getParticularRowInMarketTable(getRowIndexBasedLowestPrice(getColumnIndex("Price")))
					.findElement(By.cssSelector("div[class^='name '] > span[class$='longName']")).getText());
			System.out.println("The lowest price market cap is: " + getParticularRowInMarketTable(getRowIndexBasedLowestPrice(getColumnIndex("Price")))
					.findElement(By.cssSelector("fin-streamer[data-field='marketCap']")).getText());
			System.out.println("The lowest price is: " + getParticularRowInMarketTable(getRowIndexBasedLowestPrice(getColumnIndex("Price")))
			.findElement(By.cssSelector("span > fin-streamer[data-field='regularMarketPrice']")).getText());
		}
	}
	
	public int getColumnIndex(String columnName) {
		List<WebElement> elements = driver.findElements(By.xpath("//table[starts-with(@class,'markets-table')]/thead/tr/th/div"));
		int i = 1;
		for (WebElement element : elements) {
			if(element.getText().contains("Price")) {
				break;
			}
			i++;
		}
		return i;
	}
	
	public int getRowIndexBasedLowestPrice() {
		List<Double> numbers = new ArrayList<Double>();
		List<WebElement> elements = driver.findElements(By.xpath("//div[@id='scr-res-table']//table/tbody/tr/td[starts-with(@aria-label,'Price')]/fin-streamer"));
		for (WebElement element : elements) {
			numbers.add(Double.valueOf(element.getAttribute("value")));
		}
		return numbers.indexOf(Collections.min(numbers));
	}
	
	public Double getLowestPrice() {
		List<Double> numbers = new ArrayList<Double>();
		List<WebElement> elements = driver.findElements(By.xpath("//div[@id='scr-res-table']//table/tbody/tr/td[starts-with(@aria-label,'Price')]/fin-streamer"));
		for (WebElement element : elements) {
			numbers.add(Double.valueOf(element.getAttribute("value")));
		}
		return Collections.min(numbers);
	}	
	
	public int getRowIndexBasedLowestPrice(int index) {
		List<Double> numbers = new ArrayList<Double>();
		List<WebElement> elements = driver.findElements(By.xpath("//table[starts-with(@class,'markets-table')]/tbody/tr/td["+index+"]/span/fin-streamer"));
		for (WebElement element : elements) {
			numbers.add(Double.valueOf(element.getAttribute("data-value")));
		}
		return numbers.indexOf(Collections.min(numbers));
	}
	
	public Double getLowestPrice(int index) {
		List<Double> numbers = new ArrayList<Double>();
		List<WebElement> elements = driver.findElements(By.xpath("//table[starts-with(@class,'markets-table')]/tbody/tr/td["+index+"]/span/fin-streamer"));
		for (WebElement element : elements) {
			numbers.add(Double.valueOf(element.getAttribute("data-value")));
		}
		return Collections.min(numbers);
	}	
	
	public WebElement getParticularRow(int index) {
		return driver.findElements(By.xpath("//div[@id='scr-res-table']//table/tbody/tr")).get(index);
	}
	
	public WebElement getParticularRowInMarketTable(int index) {
		return driver.findElements(By.xpath("//table[starts-with(@class,'markets-table')]/tbody/tr")).get(index);
	}	

}