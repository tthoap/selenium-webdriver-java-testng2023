package webdriver;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_17_Wait_Part_VI_Mixing {
	WebDriver driver;
	WebDriverWait explicitWait;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");

	JavascriptExecutor jsExecutor;

//edit
	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Windows")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		}

		driver = new FirefoxDriver();
		jsExecutor = (JavascriptExecutor) driver;

	}

	// @Test //Happy path case
	public void TC_01_Found() {
		driver.get("https://automationfc.github.io/dynamic-loading/");

		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		explicitWait = new WebDriverWait(driver, 5);

		System.out.println("1.1 - Start implicit wait: " + getDateTimeNow());
		driver.findElement(By.cssSelector("div#start>button"));
		System.out.println("1.2 - end implicit wait: " + getDateTimeNow());

		System.out.println("2.1 - Start explicit wait: " + getDateTimeNow());
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#start>button")));
		System.out.println("2.1 - end explicit wait: " + getDateTimeNow());

	}

	//@Test // Unhappy path case
	public void TC_02_Not_Found_Only_Implicit() {
		driver.get("https://automationfc.github.io/dynamic-loading/");
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		System.out.println("1.1 - Start implicit wait: " + getDateTimeNow());
		
		try {
			driver.findElement(By.cssSelector("div#sstart1234"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("1.2 - end implicit wait: " + getDateTimeNow());
	}
	
	//@Test // Unhappy path case
	public void TC_03_Not_Found_Implicit() {
		driver.get("https://automationfc.github.io/dynamic-loading/");

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		System.out.println("1.1 - Start implicit wait: " + getDateTimeNow());

		try {
			driver.findElement(By.cssSelector("div#sstart1234"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("1.2 - end implicit wait: " + getDateTimeNow());
	}

	//@Test
	public void TC_03_Not_Found_Implicit_Explicit() {
		driver.get("https://automationfc.github.io/dynamic-loading/");
		
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		explicitWait = new WebDriverWait(driver, 5);
		
		System.out.println("1.1 - Start implicit wait: " + getDateTimeNow());
		try {
			driver.findElement(By.cssSelector("div#sstart1234"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("1.2 - end implicit wait: " + getDateTimeNow());
		
		System.out.println("2.1 - Start explicit wait: " + getDateTimeNow());
		try {
			explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#sstart1234")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("2.1 - end explicit wait: " + getDateTimeNow());
		
	}
	
	
	//@Test
	public void TC_03_Not_Found_Implicit_Longer_Than_Explicit() {
		driver.get("https://automationfc.github.io/dynamic-loading/");
		
		driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		explicitWait = new WebDriverWait(driver, 5);
		/*
		 * System.out.println("1.1 - Start implicit wait: " + getDateTimeNow()); try { driver.findElement(By.cssSelector("div#sstart1234")); } catch (Exception e) {
		 * e.printStackTrace(); } System.out.println("1.2 - end implicit wait: " + getDateTimeNow());
		 */
		
		System.out.println("2.1 - Start explicit wait: " + getDateTimeNow());
		try {
			explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#sstart1234")));
		} catch (Exception e) {
			System.out.println("------------Exception of explicit");
			e.printStackTrace();
			System.out.println("------------Exception of explicit");
		}
		System.out.println("2.1 - end explicit wait: " + getDateTimeNow());
		
	}
	
	//@Test
	public void TC_03_Not_Found_Explicit_By() {
		driver.get("https://automationfc.github.io/dynamic-loading/");
		
		explicitWait = new WebDriverWait(driver, 2);
		System.out.println("2.1 - Start explicit wait: " + getDateTimeNow());
		try {
			explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#sstart1234")));
		} catch (Exception e) {
			System.out.println("------------Exception of explicit");
			e.printStackTrace();
			System.out.println("------------Exception of explicit");
		}
		System.out.println("2.1 - end explicit wait: " + getDateTimeNow());
		
	}
	@Test
	public void TC_03_Not_Found_Explicit_Element() {
		driver.get("https://automationfc.github.io/dynamic-loading/");

		explicitWait = new WebDriverWait(driver, 2);
		System.out.println("2.1 - Start explicit wait: " + getDateTimeNow());
		try {
			explicitWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("div#sstart1234"))));
		} catch (Exception e) {
			System.out.println("------------Exception of explicit");
			e.printStackTrace();
			System.out.println("------------Exception of explicit");
		}
		System.out.println("2.1 - end explicit wait: " + getDateTimeNow());

	}
	
	public String getDateTimeNow() {
		Date date = new Date();
		return date.toString();
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
