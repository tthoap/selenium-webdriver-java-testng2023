package testng;

import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeClass;

import java.lang.reflect.Method;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class Topic_05_DataProvider {

	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");

	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Windows")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		}

		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();

	}

	 @Test(dataProvider = "login")
	public void TC_01_Register_To_System(String username, String password) {
		System.out.println(username);
		System.out.println(password);

	}

	@Test(dataProvider = "login")
	public void TC_02_Login_To_System(String username, String password) {
		driver.get("http://live.techpanda.org/index.php/customer/account/login/");
		driver.findElement(By.id("email")).clear();
		driver.findElement(By.id("email")).sendKeys(username);
		driver.findElement(By.id("pass")).clear();
		driver.findElement(By.id("pass")).sendKeys(password);
		driver.findElement(By.id("send2")).click();

		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='col-1']//p")).getText().contains(username));
		driver.findElement(By.xpath("//header[@id='header']//span[text()='Account']")).click();
		driver.findElement(By.xpath("//a[text()='Log Out']")).click();

	}

	@DataProvider(name = "register")
	public Object[][] dp() {
		return new Object[][] { new Object[] { "selenium_11_01@gmail.com", "111111" },
				new Object[] { "selenium_11_02@gmail.com", "111111" },
				new Object[] { "selenium_11_03@gmail.com", "111111" } };
	}

	@DataProvider(name = "login")
	public Object[][] userpass(Method method) {
		Object[][] obj = null;
		if (method.getName().contains("Register_To_System")) {
			obj = new Object[][] { { "selenium" + generateEmail() + "@gmail.com", "111111" },
					{ "selenium" + generateEmail() + "@gmail.com", "111111" },
					{ "selenium" + generateEmail() + "@gmail.com", "111111" } };

		} else if (method.getName().contains("Login_To_System"))

		{
			obj = new Object[][] { { "selenium_11_01@gmail.com", "111111" }, { "selenium_11_02@gmail.com", "111111" },
					{ "selenium_11_03@gmail.com", "111111" } };
		}
		return obj;

	}

	public int generateEmail() {
		Random rand = new Random();
		return rand.nextInt(9999);
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}