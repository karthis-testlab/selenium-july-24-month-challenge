package challenge.week03.day15;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class ExceptionHandlingTest {
	
	public static ChromeDriver driver;

	public static void main(String[] args) {
		try {
			driver = new ChromeDriver();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
			driver.manage().window().maximize();
			driver.get("https://www.saucedemo.com/");
			driver.findElement(By.id("user-name")).sendKeys("visual_user ");
			driver.findElement(By.xpath("//input[@id='password']")).sendKeys("secret_sauce");
			driver.findElement(By.id("login-button")).click();
			driver.findElement(By.xpath("(//div[@class='login-box']//input)[1]")).clear();
			driver.findElement(By.xpath("(//div[@class='login-box']//input)[1]")).sendKeys("visual_user");
			driver.findElement(By.id("login-button")).click();
			WebElement click = driver.findElement(By.xpath("//div[text()='Sauce Labs Backpack']"));
			driver.executeScript("arguments[0].click();", click);
			String price = driver.findElement(By.className("inventory_details_price")).getText();
			System.out.println("Price of Backpack " + price);
			String addToCart = driver.findElement(By.xpath("//button[text()='Add to cart']")).getText();
			System.out.println(addToCart);
			driver.findElement(By.xpath("//button[text()='Add to cart']")).click();
			driver.findElement(By.className("shopping_cart_badge")).click();			
			driver.findElement(By.xpath("//button[text()='Continue Shopping']/following::button")).click();
			driver.findElement(By.id("first-name")).sendKeys("Test");
			driver.findElement(By.id("last-name")).sendKeys("Leaf");
			driver.findElement(By.name("postalCode")).sendKeys("600114");
			driver.findElement(By.xpath("//button[text()='Cancel']/following-sibling::input")).click();
			String sauceCard = driver.findElement(By.xpath("//div[text()='Payment Information:']/following-sibling::div")).getText();
			System.out.println(sauceCard);
			driver.findElement(By.xpath("//button[text()='Finish']")).click();
			String completeHeader = driver.findElement(By.cssSelector("h2.complete-header")).getText();
			System.out.println(completeHeader);
			driver.findElement(By.id("react-burger-menu-btn")).click();
			driver.findElement(By.id("logout_sidebar_link")).click();
		} catch (NoSuchElementException e) {
			System.err.println("[ERROR]["+new SimpleDateFormat("dd:MM:yyyy:HH:mm:sss").format((new Date()))+"]: The desired web element cannot be located using the specified locator, Due to --> "+e.toString());
		} catch (InvalidSelectorException e) {
			System.err.println("[ERROR]["+new SimpleDateFormat("dd:MM:yyyy:HH:mm:sss").format((new Date()))+"]: Selenium WebDriver cannot interpret the provided selector due to incorrect syntax, expressions, or invalid context, Due to --> "+e.toString());
		} catch (JavascriptException e){
			System.err.println("[ERROR]["+new SimpleDateFormat("dd:MM:yyyy:HH:mm:sss").format((new Date()))+"]: JavascriptExecution is raised when there is an error while executing JavaScript code within the browser while running tests, Due to --> "+e.toString());
		} catch (NoSuchFrameException e) {
			System.err.println("[ERROR]["+new SimpleDateFormat("dd:MM:yyyy:HH:mm:sss").format((new Date()))+"]: This exception indicates that the frame or iframe you are trying to work with is either not present or has been removed, Due to --> "+e.toString());
		} finally {
			try {
				driver.close();
			} catch (Exception e) {
				driver.quit();
			}
		}
	}

}