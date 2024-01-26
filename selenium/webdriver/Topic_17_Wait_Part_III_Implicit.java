package webdriver;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
public class Topic_17_Wait_Part_III_Implicit {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
//edit
	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Windows")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		}

		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		
	}

	@Test
	public void TC_01_Timeout_Less_Than_Element_Display() {
		driver.get("https://automationfc.github.io/dynamic-loading/");
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		
		driver.findElement(By.cssSelector("div#start>button")).click();
		
		//After click - Takes 5s to display Hellow Word
		//Assert.assertFalse(driver.findElement(By.cssSelector("div#finish>h4")).isDisplayed());
		//driver.findElement(By.cssSelector("div#finish>h4")).getText();
	}

	@Test
	public void TC_02_Timeout_Equal_Element_Display() {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.get("https://automationfc.github.io/dynamic-loading/");
		driver.findElement(By.cssSelector("div#start>button")).click();
		
		//After click - Takes 5s to display Hellow Word
		Assert.assertTrue(driver.findElement(By.cssSelector("div#finish>h4")).isDisplayed());
		driver.findElement(By.cssSelector("div#finish>h4")).getText();

		
	}

	@Test
	public void TC_03_Timeout_Longer_Than_Element_Display() {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://automationfc.github.io/dynamic-loading/");
		driver.findElement(By.cssSelector("div#start>button")).click();
		
		//After click - Takes 5s to display Hellow Word
		Assert.assertTrue(driver.findElement(By.cssSelector("div#finish>h4")).isDisplayed());
		driver.findElement(By.cssSelector("div#finish>h4")).getText();


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
