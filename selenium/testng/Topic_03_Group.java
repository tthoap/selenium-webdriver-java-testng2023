package testng;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class Topic_03_Group {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	
	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		if (osName.contains("Windows")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		}

		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		Assert.assertTrue(false);
	}

	@Test(groups = "user")
	public void User_01_Create_New_User() {
		System.out.println("Run TC 01");
	}
	
	@Test(groups = {"user", "admin"},priority = 2 )
	public void User__View_User() {
		System.out.println("Run TC 02");
	}
	
	@Test(groups = { "admin"},priority = 3, enabled = false)
	public void User__Edit_User() {
		System.out.println("Run TC 03");
	}
	
	@Test(groups = {"user", "admin", "super"},priority = 4)
	public void User__Move_User() {
		System.out.println("Run TC 04");
	}
	
	@Test(priority = 5,groups = {"super"})
	public void User_05_Delete_User() {
		System.out.println("Run TC 05");
	}


	@AfterClass(alwaysRun = true)
	public void afterClass() {
		driver.quit();
	}

}
