package testng;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.event.SwingPropertyChangeSupport;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Topic_06_Parameters {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	Select select;
	String email, firstName, lastName, companyName, day, month, year;
	List<String> expectedItemtext;

	@Parameters("browser")
	@BeforeClass
	public void beforeClass(String browserName) {
		switch (browserName) {
		case "firefox":
			if (osName.contains("Windows")) {
				System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
			} else {
				System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
			}
			driver = new FirefoxDriver();
			break;
		case "chrome":
			if (osName.contains("Windows")) {
				System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
			} else {
				System.setProperty("webdriver.chrome.driver", projectPath + "/browserDrivers/chromedriver");
			}
			driver = new ChromeDriver();
			break;
		case "edge":
			if (osName.contains("Windows")) {
				System.setProperty("webdriver.edge.driver", projectPath + "\\browserDrivers\\msedgedriver.exe");
			} else {
				System.setProperty("webdriver.edge.driver", projectPath + "/browserDrivers/msedgedriver");
			}
			driver = new EdgeDriver();
			break;

		default:
			throw new RuntimeException("Browser not support");
		}

		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		email = "Angle" + generateEmail() + "@mail.net";
		firstName = "Hoatran";
		lastName = "auto";
		companyName = "sams";
		day = "10";
		month = "June";
		year = "1990";
		expectedItemtext = new ArrayList<String>(Arrays.asList("Month", "January", "February", "March", "April","May", "June", "July", "August", "September", "October", "November", "December"));

	}

	@Test
	public void TC_01_Nopcommerce() {
		driver.get("https://demo.nopcommerce.com/");

		driver.findElement(By.className("ico-register")).click();
		driver.findElement(By.id("FirstName")).sendKeys(firstName);
		driver.findElement(By.id("LastName")).sendKeys(lastName);

		select = new Select(driver.findElement(By.name("DateOfBirthDay")));
		select.selectByVisibleText(day);

		Assert.assertEquals(select.getFirstSelectedOption().getText(), day);
		
		select = new Select(driver.findElement(By.name("DateOfBirthMonth")));
		select.selectByVisibleText(month);
		Assert.assertEquals(select.getFirstSelectedOption().getText(), month);
		
		select = new Select(driver.findElement(By.name("DateOfBirthYear")));
		select.selectByVisibleText(year);
		Assert.assertEquals(select.getFirstSelectedOption().getText(), year);

		driver.findElement(By.id("Email")).sendKeys(email);
		driver.findElement(By.id("Company")).sendKeys(companyName);
		driver.findElement(By.id("Password")).sendKeys("12345678");
		driver.findElement(By.id("ConfirmPassword")).sendKeys("12345678");
		driver.findElement(By.id("register-button")).click();
		
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='result' and text()='Your registration completed']")).isDisplayed());
		//sleepInSecond(2000);
		//login
		driver.findElement(By.className("ico-login")).click();
		driver.findElement(By.id("Email")).sendKeys(email);
		driver.findElement(By.id("Password")).sendKeys("12345678");
		driver.findElement(By.cssSelector(".login-button")).click();
		
		
		//sleepInSecond(2000);
		
		driver.findElement(By.className("ico-account")).click();
		Assert.assertEquals(driver.findElement(By.id("FirstName")).getAttribute("value"), firstName);
		Assert.assertEquals(driver.findElement(By.id("LastName")).getAttribute("value"), lastName);
		
		select = new Select(driver.findElement(By.name("DateOfBirthDay")));
		Assert.assertEquals(select.getFirstSelectedOption().getText(), day);
		select = new Select(driver.findElement(By.name("DateOfBirthMonth")));
		Assert.assertEquals(select.getFirstSelectedOption().getText(), month);
		select = new Select(driver.findElement(By.name("DateOfBirthYear")));
		Assert.assertEquals(select.getFirstSelectedOption().getText(), year);
		
		Assert.assertEquals(driver.findElement(By.id("Company")).getAttribute("value"), companyName);
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
