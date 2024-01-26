package webdriver;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
public class Topic_17_Wait_Part_V_Explicit {
	WebDriver driver;
	WebDriverWait explicitWait;
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
		explicitWait = new WebDriverWait(driver, 6);
		driver.manage().window().maximize();
		
		
	}

	@Test
	public void TC_01_Invisible() {
		driver.get("https://automationfc.github.io/dynamic-loading/");
				
		driver.findElement(By.cssSelector("div#start>button")).click();	
		
		//Wait cho loading icon bien mat
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@id='loading']")));
		Assert.assertTrue(driver.findElement(By.cssSelector("div#finish>h4")).isDisplayed());
		System.out.println(driver.findElement(By.cssSelector("div#finish>h4")).getText());

	}

	@Test
	public void TC_02_Visible() {
		driver.get("https://automationfc.github.io/dynamic-loading/");
		
		driver.findElement(By.cssSelector("div#start>button")).click();	
		// Chờ cho hellow word text hiển thị
		explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("div#finish>h4")));
		
		Assert.assertTrue(driver.findElement(By.cssSelector("div#finish>h4")).isDisplayed());
		System.out.println(driver.findElement(By.cssSelector("div#finish>h4")).getText());
	
	}
	
	@Test
	public void TC_03_date_Picker() {
		driver.get("https://demos.telerik.com/aspnet-ajax/ajaxloadingpanel/functionality/explicit-show-hide/defaultcs.aspx");
		
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='ctl00_ContentPlaceholder1_RadCalendar1_Top']")));
		explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='26']")));
		driver.findElement(By.xpath("//a[text()='26']")).click();
		
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(@id,'_RadCalendar1')]//div[@class='raDiv']")));
		
		
	}

	//@Test
	public void TC_03_Timeout_Longer_Than_Element_Display() {
		explicitWait = new WebDriverWait(driver, 10);

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
