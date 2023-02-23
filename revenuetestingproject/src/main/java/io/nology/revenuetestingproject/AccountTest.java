package io.nology.revenuetestingproject;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
// If it doesn't auto import we add the below in and that can help fix it. 
// Same with Testng
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AccountTest {
	ChromeDriver driver;
	
	@BeforeMethod
	public void setUp() {
		System.setProperty("web.chrome.driver", "C:\\Program Files\\Webdrivers\\chromedriver.exe");

		driver = new ChromeDriver();
		//driver.get("http://127.0.0.1:5173/");
		driver.get("http://localhost:5173/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	}
	
	@Test(description = "  No accounts should be dislayed on page load.")
	public void noAccountsOnPageLoad() {
		List<WebElement> noAccountsDisplayMessage = driver.findElements(By.xpath("//p[text()='There are no accounts to display']")); 
		Assert.assertEquals(noAccountsDisplayMessage.size(), 1);
	}
	
	@Test(description = "  Accounts should be displayed after submission.")
	public void showNewAccountOnSubmission() {
		// //input[@id='appName']
		// //input[@id='username']
		// //button[normalize-space()='Submit']
		
		WebElement appName = driver.findElement(By.xpath("//input[@id='appName']"));
		WebElement username = driver.findElement(By.xpath("//input[@id='username']"));
		WebElement submitButton = driver.findElement(By.xpath("//button[normalize-space()='Submit']"));
		
		appName.sendKeys("Gmail");
		username.sendKeys("thisIsAUsername");
		
		// everything on the page can be click so therefore is clickable. 
		// .click is available for every WebElement
		submitButton.click();
		
		List<WebElement> accounts = driver.findElements(By.xpath("//p[contains(text(),'Gmail - thisIsAUsername')]"));
		List<WebElement> noAccountsMessage = driver.findElements(By.xpath("//p[text()='There are no accounts to display']"));
		
		Assert.assertEquals(noAccountsMessage.size(), 0);
		Assert.assertEquals(accounts.size(), 1);	
	}
	
	@Test(description = "  Input fields should be clear after submission.")
	public void clearInputAfterSubmission() {
		WebElement appName = driver.findElement(By.xpath("//input[@id='appName']"));
		WebElement username = driver.findElement(By.xpath("//input[@id='username']"));
		WebElement submitButton = driver.findElement(By.xpath("//button[normalize-space()='Submit']"));
		
		appName.sendKeys("Gmail2");
		username.sendKeys("thisIsAUsername2");
		
		submitButton.click();
		
		appName = driver.findElement(By.xpath("//input[@id='appName']"));
		username = driver.findElement(By.xpath("//input[@id='username']"));
		
		Assert.assertEquals(appName.getAttribute("value"), "");
		Assert.assertEquals(username.getAttribute("value"), "");
	}
	
	@Test(description = "  Successfully delete the account corresponding to the button.")
	public void deleteAccount() {
		WebElement appName = driver.findElement(By.xpath("//input[@id='appName']"));
		WebElement username = driver.findElement(By.xpath("//input[@id='username']"));
		WebElement submitButton = driver.findElement(By.xpath("//button[normalize-space()='Submit']"));
		
		int n = 5; 
		
		for(int i = 1; i <= n; i++) {
			appName.sendKeys("Gmail"+i);
			username.sendKeys("thisIsAUsername"+i);
			submitButton.click();
		}
		
		Random rdn = new Random();
		int rdnNum = rdn.nextInt(n) + 1;
		
		WebElement deleteBtn = driver.findElement(By.xpath("//p["+rdnNum+"]//button[1]"));
		
		// Test button exists
//		boolean btnExists = driver.findElement(By.xpath("//p["+rdnNum+"]//button[1]")).isDisplayed();
//		Assert.assertEquals(btnExists, true);
		
		
		deleteBtn.click();

		//submitButton.click();
		
		List<WebElement> accounts = driver.findElements(By.xpath("//p[.//button and contains(text(),'Gmail')]"));
		List<WebElement> exists = driver.findElements(By.xpath("//p[@id='"+rdnNum+"']"));
		
		//boolean accountExists = driver.findElement(By.xpath("//p[@id='"+rdnNum+"']")).isDisplayed();
		//Assert.assertEquals(accountExists, false);
		
		Assert.assertEquals(accounts.size(), n-1);
		Assert.assertEquals(exists.size(), 0);
	}
	
	@Test(description = "  Inputs are required before submission.")
	public void inputsRequired() {
		WebElement appName = driver.findElement(By.xpath("//input[@id='appName']"));
		WebElement username = driver.findElement(By.xpath("//input[@id='username']"));
		WebElement submitButton = driver.findElement(By.xpath("//button[normalize-space()='Submit']"));
		
		submitButton.click();
		
		String appValidation = driver.findElement(By.name("appName")).getAttribute("validationMessage");
		String userValidation = driver.findElement(By.name("appName")).getAttribute("validationMessage");
		
		System.out.println(appValidation);
		System.out.println(userValidation);
		
		Assert.assertEquals(appValidation, "Please fill out this field.");
		Assert.assertEquals(userValidation, "Please fill out this field.");
		
		List<WebElement> noAccountsDisplayMessage = driver.findElements(By.xpath("//p[text()='There are no accounts to display']")); 
		Assert.assertEquals(noAccountsDisplayMessage.size(), 1);
	}
	
	@Test(description = "  Should only accept unique emails.")
	public void acceptUniqueEmail() {
		WebElement appName = driver.findElement(By.xpath("//input[@id='appName']"));
		WebElement username = driver.findElement(By.xpath("//input[@id='username']"));
		WebElement submitButton = driver.findElement(By.xpath("//button[normalize-space()='Submit']"));
		
		appName.sendKeys("user1@gmail.com");
		username.sendKeys("thisIsAUsername2");
		submitButton.click();
		
		appName.sendKeys("user1@gmail.com");
		username.sendKeys("thisIsAUsername2");
		submitButton.click();
		
		List<WebElement> accounts = driver.findElements(By.xpath("//p[.//button and contains(text(),'gmail')]"));
		Assert.assertEquals(accounts.size(), 1);
		
		WebElement errorMsg = driver.findElement(By.xpath("//span[@id='errorMsg']"));
		Assert.assertEquals(errorMsg.getText(), "Email already exists");
	}
	
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
}
