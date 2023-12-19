package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_03_Selenium_locator_exercise {
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
				
	}
	
	@Test
	public void TC_01_Reg_with_empty_data() {
		//step1 access to the link
		driver.get("https://alada.vn/tai-khoan/dang-ky.html");
		//click to Button Dang Ky
		driver.findElement(By.xpath("//button[@class='btn_pink_sm fs16']")).click();
		Assert.assertTrue(driver.findElement(By.id("txtFirstname-error")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.id("txtEmail-error")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.id("txtCEmail-error")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.id("txtPassword-error")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.id("txtCPassword-error")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.id("txtPhone-error")).isDisplayed());

	}
	
	public void TC_02_Reg_with_invalid_email() {
		driver.navigate().refresh();
		driver.findElement(By.name("txtFirstname")).sendKeys("Hoa tran");
		driver.findElement(By.name("txtEmail")).sendKeys("Hoa@tran@1");
		driver.findElement(By.name("txtEmail")).sendKeys("Hoa@tran@1");
		driver.findElement(By.name("txtPassword")).sendKeys("12345678");
		driver.findElement(By.name("txtCPassword")).sendKeys("12345678");
		driver.findElement(By.name("txtPhone")).sendKeys("0971809071");
		
		//click to Button Dang Ky
		driver.findElement(By.xpath("//button[@class='btn_pink_sm fs16']")).click();
		//Verify
		Assert.assertEquals(driver.findElement(By.className("txtEmail-erro")).getText(), "Vui lòng nhập email hợp lệ");

		
	}
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}

