package webdriver;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
public class Topic_17_Wait_Part_I_Condition_ElementStatus {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	WebDriverWait explicitwait;
//edit
	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Windows")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		}

		driver = new FirefoxDriver();
		explicitwait = new WebDriverWait(driver, 20);
		
	}

	@Test
	public void TC_01_Visible() {
		driver.get("https://facebook.com/");
		explicitwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@data-testedid='open-registration-form-button']"))).click();
		explicitwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='reg_email__']"))).sendKeys("hoa@gmail.com");

		//Wait chi element được visible/display
		Dimension confirmEmailSize = explicitwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='reg_email_confirmation__']"))).getSize();
		System.out.println("Confirm Email Heght = " + confirmEmailSize.getHeight());
		System.out.println("Confirm Email Width = " + confirmEmailSize.getWidth());

	}

	@Test
	public void TC_02_Invisible_In_DOM() {
		driver.get("https://facebook.com/");
		explicitwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@data-testedid='open-registration-form-button']"))).click();

		//Element không hiển thị: ko có trên UI + không có trong DOM
		explicitwait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//input[@name='reg_email_confirmation__']")));


	}
	@Test
	public void TC_02_Invisible_Not_In_DOM() {
		driver.get("https://facebook.com/");
	
		//lement không hiển thị: ko có trên UI + không có trong DOM
		explicitwait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//input[@name='reg_email_confirmation__']")));
		
	}
	@Test
	public void TC_03_Presence_In_UI() {
		driver.get("https://facebook.com/");
		
		explicitwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@data-testedid='open-registration-form-button']"))).click();
		explicitwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='reg_email__']"))).sendKeys("hoa@gmail.com");
		
		explicitwait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='reg_email_confirmation__']")));
	}
	
	@Test
	public void TC_03_Presence_Not_In_UI() {
		driver.get("https://facebook.com/");
		
		explicitwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@data-testedid='open-registration-form-button']"))).click();
		
		explicitwait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='reg_email_confirmation__']")));
	}
	
	
	@Test
	public void TC_03_Presence_Not_In_DOM_Fil() {
		driver.get("https://facebook.com/");
		
		explicitwait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='reg_email_confirmation__']")));
	}

	@Test
	public void TC_04_Staleness() {
		driver.get("https://facebook.com/");
		
		explicitwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@data-testedid='open-registration-form-button']"))).click();
		sleepInSecond(3);
		explicitwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='reg_email__']"))).sendKeys("hoa@gmail.com");

		//Element confirm email hiện tại có trong DOM (visible)
		WebElement confirmEmail = driver.findElement(By.xpath("//input[@name='reg_email_confirmation__']"));
		explicitwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(""))).click();
		
		//Wait cho confirm email staleness
		explicitwait.until(ExpectedConditions.stalenessOf(confirmEmail));

	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	
	public int generateEmail() {
		Random rand = new Random();
		return rand.nextInt(9999);
	}

	public void sleepInSecond(long timeoutInSecond) {
		try {
			Thread.sleep(timeoutInSecond * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
