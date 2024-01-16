package webdriver;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
public class Topic_13_Frame_Iframe {
	WebDriver driver;
	Select select;
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
		driver.manage().window().maximize();
		
	}

	//@Test
	public void TC_01_Ifame() {
		//HTML A
		driver.get("https://skills.kynaenglish.vn/");
		
		sleepInSecond(5);
		//Switch vao iframe chua element truoc - HTML B
		driver.switchTo().frame(driver.findElement(By.cssSelector("div.fanpage iframe")));
		String kynaFanpageLike = driver.findElement(By.xpath("//a[text()='Kyna.vn']/parent::div/following-sibling::div")).getText();
		System.out.println(kynaFanpageLike);
		sleepInSecond(2);
		
		//Back ve HTML A
		driver.switchTo().defaultContent();
		Assert.assertTrue(driver.findElement(By.cssSelector("a.login-btn")).isDisplayed());
		
		sleepInSecond(3);
		//switch vao chat Iframe - HTML B1
		driver.switchTo().frame(driver.findElement(By.xpath("//div[@id='cs-live-chat']//iframe")));
		sleepInSecond(5);
		driver.findElement(By.xpath("//div[@class='button_text']/following-sibling::div")).click();
		driver.findElement(By.xpath("//input[@ng-model='login.username']")).sendKeys("anonymous user");
		driver.findElement(By.xpath("//input[@ng-model='login.phone']")).sendKeys("0984222256");
		
		select = new Select(driver.findElement(By.id("serviceSelect")));
		select.selectByVisibleText("HỖ TRỢ KỸ THUẬT");
		driver.findElement(By.xpath("//label[text()='Tin nhắn']/parent::label/following-sibling::textarea")).sendKeys("Test message");
		
		//Back ve HTML A
		driver.switchTo().defaultContent();
		driver.findElement(By.cssSelector("input#live-search-bar")).sendKeys("Excel");
		sleepInSecond(3);
		driver.findElement(By.cssSelector("button.search-button")).click();
		
		sleepInSecond(3);
		List<WebElement> excelItem = driver.findElements(By.xpath("//a[@class='card-popup' and contains(@href,'excel')]"));
		for (WebElement excel : excelItem) {
			System.out.println(excel.getAttribute("href"));
			sleepInSecond(2);
		}
		
		
	}

	@Test
	public void TC_02_frame() {
		driver.get("https://netbanking.hdfcbank.com/netbanking/");
		
		//switch vao frame cua login page
		driver.switchTo().frame(driver.findElement(By.cssSelector("frame[name='login_page']")));
		
		driver.findElement(By.cssSelector("input[name='fldLoginUserId']")).sendKeys("automationfc");
		sleepInSecond(3);
		driver.findElement(By.cssSelector("a.login-btn")).click();
		
		
	}

	@Test
	public void TC_03_() {

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
