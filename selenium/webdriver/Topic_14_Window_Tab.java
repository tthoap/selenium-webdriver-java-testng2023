package webdriver;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Sleeper;
import org.testng.Assert;
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

	// @Test
	public void TC_01_ByID() {
		// Tab A: Parent page
		driver.get("https://automationfc.github.io/basic-form/index.html");

		// Get ra Page ID cuar window tab mà driver đang đứng
		String parentpageID = driver.getWindowHandle();
		System.out.println("Parent Page ID: " + parentpageID);

		// launch Tab B
		driver.findElement(By.xpath("//a[text()='GOOGLE']")).click();
		sleepInSecond(3);

		// switch qua GOOGLE apge
		switchToWindowByID(parentpageID);
		String googlePageID = driver.getWindowHandle();
		System.out.println("Google page ID: " + googlePageID);

		driver.findElement(By.name("q")).sendKeys("automation");
		// driver.findElement(By.name("btnK")).click();
		sleepInSecond(3);

		switchToWindowByID(googlePageID);
		sleepInSecond(5);

	}

	// @Test
	public void TC_02_Title() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		driver.findElement(By.xpath("//a[text()='GOOGLE']")).click();
		pageUrl = driver.getCurrentUrl();

		// Nhảy qua tab google
		switchToWindowByPageTitle("Google");
		sleepInSecond(2);
		pageUrl = driver.getCurrentUrl();
		driver.findElement(By.name("q")).sendKeys("automation");

		// nhảy về tab parent
		switchToWindowByPageTitle("Selenium WebDriver");
		sleepInSecond(2);
		pageUrl = driver.getCurrentUrl();
		driver.findElement(By.xpath("//a[text()='FACEBOOK']")).click();

		// nhảy qua facebook
		switchToWindowByPageTitle("Facebook – log in or sign up");
		sleepInSecond(2);
		pageUrl = driver.getCurrentUrl();

		// nhảy về tab parent
		switchToWindowByPageTitle("Selenium WebDriver");
		sleepInSecond(2);
		pageUrl = driver.getCurrentUrl();
		driver.findElement(By.xpath("//a[text()='TIKI']")).click();

		// nhảy qua Tiki
		switchToWindowByPageTitle("Tiki - Mua hàng online giá tốt, hàng chuẩn, ship nhanh");
		sleepInSecond(2);
		pageUrl = driver.getCurrentUrl();

		// nhảy về tab parent
		switchToWindowByPageTitle("Selenium WebDriver");
		sleepInSecond(2);
		pageUrl = driver.getCurrentUrl();
		driver.findElement(By.xpath("//a[text()='LAZADA']")).click();

		// nhảy qua LAZADA
		switchToWindowByPageTitle("Lazada - Mua Sắm Hàng Chất Giá Tốt Online'");
		sleepInSecond(2);
		pageUrl = driver.getCurrentUrl();

	}

	//@Test
	public void TC_03_Kyna() {
		driver.get("https://skills.kynaenglish.vn/");

		String parentPageID = driver.getWindowHandle();

		
		  // click vào facebook link bên dưới 
		  driver.findElement(By.xpath("//div[@class='hotline']//img[@alt='facebook']")).click(); 
		  switchToWindowByPageTitle("Kyna.vn | Ho Chi Minh City | Facebook"); 
		 
		  By popup = By.xpath("//form[@id='login_popup_cta_form']/parent::div");
			List<WebElement> popuphienthi = driver.findElements(popup);

			if (popuphienthi.size() > 0) {
				System.out.println("-----------Popup hien thi-------------");
				driver.findElement(By.xpath("//div[@aria-label='Close']")).click();
				sleepInSecond(2);

				Assert.assertEquals(driver.findElements(popup).size(), 0);
			} else
				System.out.println("--pop-up khong hien thi--");
			
		  Assert.assertEquals(driver.findElement(By.xpath("//h1[text()='Kyna.vn']")).getText(),"Kyna.vn ");
		 
		// Back về parent page rồi click youtube link
		switchToWindowByPageTitle("Kyna.vn - Học online cùng chuyên gia");
		driver.findElement(By.xpath("//div[@class='hotline']//img[@alt='youtube']")).click();
		switchToWindowByPageTitle("Kyna.vn - YouTube");
		Assert.assertEquals(driver.findElement(By.cssSelector("div#channel-header div#text-container *")).getText(),
				"Kyna.vn");

		closeAllWindowWithoutParent(parentPageID);
	}

	//@Test
	public void TC_04_Techpanda() {
		// Step 1- truy cập trang
		driver.get("http://live.techpanda.org/");

		String parentPageID = driver.getWindowHandle();

		// Step 2- Click vào Mobile
		driver.findElement(By.xpath("//a[text()='Mobile']")).click();

		// Add sony vào compare
		driver.findElement(By.xpath(
				"//a[text()='Sony Xperia']/parent::h2/following-sibling::div[@class='actions']//a[text()='Add to Compare']"))
				.click();
		Assert.assertTrue(driver
				.findElement(By.xpath("//span[text()='The product Sony Xperia has been added to comparison list.']"))
				.isDisplayed());
		sleepInSecond(2);

		// Add sony vào compare
		driver.findElement(By.xpath(
				"//a[text()='Samsung Galaxy']/parent::h2/following-sibling::div[@class='actions']//a[text()='Add to Compare']"))
				.click();
		Assert.assertTrue(driver
				.findElement(By.xpath("//span[text()='The product Samsung Galaxy has been added to comparison list.']"))
				.isDisplayed());

		// CLick vào COMPARE button
		driver.findElement(By.xpath("//span[text()='Compare']")).click();

		switchToWindowByPageTitle("Products Comparison List - Magento Commerce");
		sleepInSecond(2);
		Assert.assertEquals(driver.getTitle(), "Products Comparison List - Magento Commerce");
		closeAllWindowWithoutParent(parentPageID);

		driver.findElement(By.xpath("//a[text()='Clear All']")).click();
		driver.switchTo().alert().accept();
		sleepInSecond(2);
		Assert.assertTrue(
				driver.findElement(By.xpath("//span[text()='The comparison list was cleared.']")).isDisplayed());

	}

	@Test
	public void TC_05_dictionary() {
		// Step 1- truy cập trang
		driver.get("https://dictionary.cambridge.org/vi/");

		String parentPageID = driver.getWindowHandle();

		// Close pop-up
		By popup = By.xpath("//div[@role='dialog']");
		List<WebElement> popuphienthi = driver.findElements(popup);

		if (popuphienthi.size() > 0) {
			System.out.println("-----------Popup hien thi-------------");
			driver.findElement(By.xpath("//button[@class='fc-close']//i[text()='cancel']")).click();
			sleepInSecond(2);

			Assert.assertEquals(driver.findElements(popup).size(), 0);
		} else
			System.out.println("--pop-up khong hien thi--");

		// Step 2- Click vào đăng nhập
		driver.findElement(By.xpath("//span[text()='Đăng ký']/parent::span/preceding-sibling::span//span")).click();

		// switch qua new window
		switchToWindowByPageTitle("Login");
		sleepInSecond(10);
		driver.manage().window().maximize();
		driver.findElement(By.xpath("//input[@value='Login']")).click();
		Assert.assertEquals(driver
				.findElement(By.xpath("//input[@name='username']/following-sibling::span[@role='alert']")).getText(),
				"This field is required");
		Assert.assertEquals(driver
				.findElement(By.xpath("//input[@name='password']/following-sibling::span[@role='alert']")).getText(),
				"This field is required");

		closeAllWindowWithoutParent(parentPageID);
		switchToWindowByPageTitle(parentPageID);

		driver.findElement(By.cssSelector("input#searchword")).sendKeys("automation");
		driver.findElement(By.cssSelector("button.cdo-search-button")).click();
		Assert.assertEquals(driver.findElement(By.xpath(
				"//div[@id='cald4-1']/following-sibling::div[@class='pos-header dpos-h']//span[text()='automation']"))
				.getText(), "automation");

	}

	public void switchToWindowByID(String windowPageID) {
		// Lấy ra tất cả các ID đang có của các tab/window
		Set<String> allWindows = driver.getWindowHandles();

		// dùng vòng lặp duyệt qua từng ID
		for (String window : allWindows) {
			if (!window.equals(windowPageID)) {
				// switch qua
				driver.switchTo().window(window);
			}

		}
	}

	public void switchToWindowByPageTitle(String expectedPageTitle) {
		// Lấy ra tất cả các ID đang có của các tab/window
		Set<String> allWindows = driver.getWindowHandles();

		// dùng vòng lặp duyệt qua từng ID
		for (String window : allWindows) {

			// cho switch qua từng Tab/window trước
			driver.switchTo().window(window);
			sleepInSecond(2);
			String actualPageTitle = driver.getTitle().trim();
			if (actualPageTitle.equals(expectedPageTitle))
				break;
		}
	}

	public void closeAllWindowWithoutParent(String parentPageID) {
		// Lấy ra all window/tab đang open
		Set<String> allWindows = driver.getWindowHandles();

		// Dùng vòng lăp duyệt qua từng ID
		for (String window : allWindows) {
			// Nếu ID nào khác với ID truyền vào
			if (!window.equals(parentPageID)) {
				// Thì switch qua rồi close
				driver.switchTo().window(window);
				sleepInSecond(3);
				driver.close();
			}

		}
		driver.switchTo().window(parentPageID);
		sleepInSecond(3);

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
