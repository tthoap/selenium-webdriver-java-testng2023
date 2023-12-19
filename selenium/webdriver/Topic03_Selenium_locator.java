package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic03_Selenium_locator {
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

		//Init browser
		driver = new FirefoxDriver();
		//set timeout to find element
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		//Launch the app
		driver.get("http://live.techpanda.org/index.php/customer/account/login/");
	}
	
	@Test
	public void TC_01_demo_testcase() throws InterruptedException {
		System.out.println("1 - Send key to email textbox by : ID");
		
		driver.findElement(By.id("email")).sendKeys("id@gmail.com");
		Thread.sleep(3000);
		driver.findElement(By.id("email")).clear();
		
		System.out.println("2 - Send key to email textbox by : Class");
		driver.findElement(By.className("validate-email")).sendKeys("class@gmail.com");
		Thread.sleep(3000);
		driver.findElement(By.className("validate-email")).clear();
		
		System.out.println("3 - Send key to email textbox by: Name");
		driver.findElement(By.name("login[username]")).sendKeys("name@gmail.com");
		Thread.sleep(3000);
		driver.findElement(By.name("login[username]")).clear();
		
		System.out.println("4 - Send key to email textbox by: Css");
		driver.findElement(By.cssSelector("input[id='email']")).sendKeys("css@gmail.com");
		Thread.sleep(3000);
		driver.findElement(By.cssSelector("input[id='email']")).clear();
		
		System.out.println("5 - Send key to email textbox by: Link Text ");
		driver.findElement(By.linkText("Forgot Your Password?")).click();;
		Assert.assertTrue(driver.findElement(By.id("email_address")).isDisplayed());
		
		System.out.println("6 - Partial link text");
		driver.findElement(By.partialLinkText("Back to")).click();
		Assert.assertTrue(driver.findElement(By.id("email")).isDisplayed());
		
		driver.navigate().refresh();
		
		System.out.println("7 - Xpath cover id");
		driver.findElement(By.xpath("//input[@id='email']")).sendKeys("XpathwithID@gmail.com");
		Thread.sleep(3000);
		driver.findElement(By.xpath("//input[@id='email']")).clear();
		
		System.out.println("8 - Xpath cover address , enter password");
		driver.findElement(By.xpath("//input[@class=\"input-text required-entry validate-password\"]")).sendKeys("12345678");
		Thread.sleep(3000);
		driver.findElement(By.xpath("//input[@class=\"input-text required-entry validate-password\"]")).clear();
		
		System.out.println("9 - Xpath cover name");
		driver.findElement(By.xpath("//input[@name='login[username]']")).sendKeys("xpathcovername@gmail,com");
		Thread.sleep(3000);
		driver.findElement(By.xpath("//input[@name='login[username]']")).clear();
	}
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}
