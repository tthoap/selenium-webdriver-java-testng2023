package webdriver;

import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
public class Topic_14_Window_Tab {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	String pageUrl;
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

	@Test
	public void TC_01_ByID() {
		//Tab A: Parent page
		driver.get("https://automationfc.github.io/basic-form/index.html");
		
		//Get ra Page ID cuar window tab mà driver đang đứng
		String parentpageID = driver.getWindowHandle();
		System.out.println("Parent Page ID: " + parentpageID);
		
		//launch Tab B
		driver.findElement(By.xpath("//a[text()='GOOGLE']")).click();
		sleepInSecond(3);
		
		//switch qua GOOGLE apge
		switchToWindowByID(parentpageID);
		String googlePageID = driver.getWindowHandle();
		System.out.println("Google page ID: " + googlePageID);
		
		driver.findElement(By.name("q")).sendKeys("automation");
	//	driver.findElement(By.name("btnK")).click();
		sleepInSecond(3);
		
		switchToWindowByID(googlePageID);
		sleepInSecond(5);
		
		
	}

	@Test
	public void TC_02_Title() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		driver.findElement(By.xpath("//a[text()='GOOGLE']")).click();
		pageUrl = driver.getCurrentUrl();
		
		//Nhảy qua tab google 
		switchToWindowByPageTitle("Google");
		sleepInSecond(2);
		pageUrl = driver.getCurrentUrl();
		driver.findElement(By.name("q")).sendKeys("automation");
		
		//nhảy về tab parent 
		switchToWindowByPageTitle("Selenium WebDriver");
		sleepInSecond(2);
		pageUrl = driver.getCurrentUrl();
		driver.findElement(By.xpath("//a[text()='FACEBOOK']")).click();
		
		//nhảy qua facebook
		switchToWindowByPageTitle("Facebook – log in or sign up");
		sleepInSecond(2);
		pageUrl = driver.getCurrentUrl();
		
		//nhảy về tab parent 
		switchToWindowByPageTitle("Selenium WebDriver");
		sleepInSecond(2);
		pageUrl = driver.getCurrentUrl();
		driver.findElement(By.xpath("//a[text()='TIKI']")).click();
		
		//nhảy qua Tiki
		switchToWindowByPageTitle("Tiki - Mua hàng online giá tốt, hàng chuẩn, ship nhanh");
		sleepInSecond(2);
		pageUrl = driver.getCurrentUrl();
		
		//nhảy về tab parent 
		switchToWindowByPageTitle("Selenium WebDriver");
		sleepInSecond(2);
		pageUrl = driver.getCurrentUrl();
		driver.findElement(By.xpath("//a[text()='LAZADA']")).click();
		
		//nhảy qua LAZADA
		switchToWindowByPageTitle("Lazada - Mua Sắm Hàng Chất Giá Tốt Online'");
		sleepInSecond(2);
		pageUrl = driver.getCurrentUrl();

		
	}

	@Test
	public void TC_03_() {

	}
	
	public void switchToWindowByID(String windowPageID) {
		//Lấy ra tất cả các ID đang có của các tab/window 
		Set<String> allWindows = driver.getWindowHandles();
		
		//dùng vòng lặp duyệt qua từng ID
		for (String window  : allWindows) {
			if(!window.equals(windowPageID)) {
				//switch qua
				driver.switchTo().window(window);
			}
			
			
		}
	}
	public void switchToWindowByPageTitle(String expectedPageTitle) {
		//Lấy ra tất cả các ID đang có của các tab/window 
		Set<String> allWindows = driver.getWindowHandles();
		
		//dùng vòng lặp duyệt qua từng ID
		for (String window  : allWindows) {
			
			//cho switch qua từng Tab/window trước
			driver.switchTo().window(window);
			sleepInSecond(2);
			String actualPageTitle = driver.getTitle().trim();
			if(actualPageTitle.equals(expectedPageTitle))break;
				
			
		}
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
