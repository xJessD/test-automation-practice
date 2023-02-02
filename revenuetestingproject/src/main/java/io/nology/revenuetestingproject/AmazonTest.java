package io.nology.revenuetestingproject;

import java.time.Duration;

import org.openqa.selenium.By;
// If it doesn't auto import we add the below in and that can help fix it. 
// Same with Testng
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AmazonTest {
	// ChromeDriver class comes from the selenium package
	ChromeDriver driver;
	
//	@Test
//	public void testtest() {
//		Assert.assertEquals(1, 1);
//	}
	
	@BeforeMethod
	public void setUp() {
		System.setProperty("web.chrome.driver", "C:\\Program Files\\Webdrivers\\chromedriver.exe");
	
		// Create the driver -> call / find the above driver from the given path.
		driver = new ChromeDriver();
		
		// before we do testing we need to pull up the page
		// may need the www
		driver.get("https://amazon.com.au");
		
		// Maximize the window
		driver.manage().window().maximize();
		
		// wait for page to load
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	}
	
	@Test
	public void verifyTitle() {
		// <title>Amazon.com.au: Shop online for Electronics, Apparel, Toys, Books, DVDs &amp; more</title>
		// Need to make sure that the &amp; is actually a "&" otherwise it fails
		
		// Will rip the title tag from the head tag in the actual site
		String realTitle = driver.getTitle();
		
		String expectedTitle = "Amazon.com.au: Shop online for Electronics, Apparel, Toys, Books, DVDs & more";
		
		Assert.assertEquals(realTitle, expectedTitle);
	}
	
	@Test
	public void verifyLogo() {
		// there isn't a getTitle() method that can find a logo for us
		// so we'll need to use selenium selectors to find it
		// called xpath selectors
		//(<tagname>[function(), input])
		
		// //a[@id='nav-logo-sprites']
		
		// isDisplayed() at the end turns the WebElement type to a boolean type
		// By class comes from selenium
		boolean isDisplayed = driver.findElement(By.xpath("//a[@id='nav-logo-sprites']")).isDisplayed(); // takes in a By object type
		
		Assert.assertEquals(isDisplayed, true);
	}
	
	@AfterMethod
	public void tearDown() {
		// to make sure we are actually quitting our browser sessions before we move on to another test
		driver.quit();
	}
}
