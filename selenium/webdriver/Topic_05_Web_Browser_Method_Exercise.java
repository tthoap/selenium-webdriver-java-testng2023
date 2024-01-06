package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_05_Web_Browser_Method_Exercise {
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

		//Mở ra trình duyệt
		driver = new FirefoxDriver();
		//set timeout to find element
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		//Launch the app
		driver.get("http://live.techpanda.org/");
	}
	
	@Test
	public void TC_01_Verify_URL() {
		//Click vào My account ở dưới footer
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		Assert.assertEquals(driver.getCurrentUrl(), "http://live.techpanda.org/index.php/customer/account/login/");
		
		//Click để chuyển vào trang Register
		driver.findElement(By.xpath("//a[@title='Create an Account']")).click();
		Assert.assertEquals(driver.getCurrentUrl(), "http://live.techpanda.org/index.php/customer/account/create/");
		
	}
	
	@Test
	public void TC_01_Verify_Title() {
		//Click vào My account ở dưới footer
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		Assert.assertEquals(driver.getTitle(), "Customer Login");
		
		//Click để chuyển vào trang Register
		driver.findElement(By.xpath("//a[@title='Create an Account']")).click();
		Assert.assertEquals(driver.getTitle(), "Create New Customer Account");
	}
	
	@Test
	public void TC_01_Verify_Navigation() {
		//Click vào My account ở dưới footer
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
			
		//Click để chuyển vào trang Register
		driver.findElement(By.xpath("//a[@title='Create an Account']")).click();
		driver.navigate().back();
		
		Assert.assertEquals(driver.getTitle(), "Customer Login");
		
		driver.navigate().forward();
		Assert.assertEquals(driver.getTitle(), "Create New Customer Account");
		
	}
	
	@Test
	public void TC_01_Verify_getPageSource() {
		//Click vào My account ở dưới footer
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		String currentSourcePage = driver.getPageSource();
		Assert.assertTrue(currentSourcePage.contains("Login or Create an Account"));
		
		driver.findElement(By.xpath("//a[@title='Create an Account']")).click();
		currentSourcePage = driver.getPageSource();
		Assert.assertTrue(currentSourcePage.contains("Create an Account"));

		
	}
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	
}
