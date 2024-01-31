package webdriver;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
public class Topic_12_Popup {
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
		driver.manage().window().maximize();
		//driver.get("");
	}

	//@Test
	public void TC_01_Fixed_Popup() {
		driver.get("https://ngoaingu24h.vn/");
	

		//check pop-up is not display
		//Assert.assertFalse(driver.findElement(By.xpath("//div[@id='modal-login-v1' and contains(@style,'display')]")).isDisplayed());
		
		//click Dang Ky button
		driver.findElement(By.cssSelector("button.login_.icon-before")).click();
		//check pop-up is  display
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='modal-login-v1' and @class='modal fade in']")).isDisplayed());
		driver.findElement(By.xpath("//div[@id='modal-login-v1' and @class='modal fade in']//input[@id='account-input']")).sendKeys("hautomationuser");
		driver.findElement(By.xpath("//div[@id='modal-login-v1' and @class='modal fade in']//input[@id='password-input']")).sendKeys("123412345");
		
		driver.findElement(By.xpath("//div[@id='modal-login-v1' and @class='modal fade in']//button[@class='btn-v1 btn-login-v1 buttonLoading']")).click();
		sleepInSecond(2);
		
		Assert.assertEquals(driver.findElement(By.xpath("//div[@id='modal-login-v1' and @class='modal fade in']//div[@class='row error-login-panel']")).getText(),"Tài khoản không tồn tại!");
		sleepInSecond(2);
		driver.findElement(By.xpath("//div[@id='modal-login-v1' and @class='modal fade in']//button[@class='close']")).click();
		
		//Assert.assertFalse(driver.findElement(By.xpath("//div[@id='modal-login-v1' and contains(@style,'display')]")).isDisplayed());


	}

	//@Test
	public void TC_02_Random_Popup_In_DOM() {
		driver.get("https://skills.kynaenglish.vn/");
		
		//click dang nhap button
		driver.findElement(By.cssSelector("a.login-btn")).click();
		
		//kiem tra popup login hien thi

		Assert.assertTrue(driver.findElement(By.cssSelector("div.k-popup-account-mb-content")).isDisplayed());
		driver.findElement(By.cssSelector("input#user-login")).sendKeys("automation@gmail.com");
		driver.findElement(By.cssSelector("input#user-password")).sendKeys("123456");
		sleepInSecond(3);
		//CLick dang nhap btn
		driver.findElement(By.cssSelector("button#btn-submit-login")).click();
		sleepInSecond(3);
		Assert.assertEquals(driver.findElement(By.cssSelector("div#password-form-login-message")).getText(), "Sai tên đăng nhập hoặc mật khẩu");
		//close pop-up
		driver.findElement(By.xpath("//div[@id='k-popup-account-login']//span[@aria-hidden='true']")).click();
		//kiem tra popup is not display
		Assert.assertFalse(driver.findElement(By.cssSelector("div.k-popup-account-mb-content")).isDisplayed());

	}

	//@Test
	public void TC_03_Random_Popup_Not_In_DOM() {
		driver.get("https://tiki.vn/");
		driver.findElement(By.xpath("//span[text()='Tài khoản']")).click();
		
		By popup = By.cssSelector("div.ReactModal__Content");
		//verify pop-up hien thi
		Assert.assertTrue(driver.findElement(popup).isDisplayed());
		
		//click dang nhap bang email
		driver.findElement(By.cssSelector("p.login-with-email")).click();
		
		//ko nhap va click Dang nhap btn
		driver.findElement(By.xpath("//button[text()='Đăng nhập']")).click();
		//Verify error messages
		Assert.assertTrue(driver.findElement(By.xpath("//span[@class='error-mess' and text()='Email không được để trống']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//span[@class='error-mess' and text()='Mật khẩu không được để trống']")).isDisplayed());
		
		//close pop-up
		driver.findElement(By.xpath("//img[@class='close-img']")).click();
		
		List<WebElement> popuphienthi = driver.findElements(popup);
		
		if(popuphienthi.size()>0) {
			System.out.println("-----------Popup hien thi-------------");
			driver.findElement(By.xpath("//img[@class='close-img']")).click();
			sleepInSecond(2);
			
			Assert.assertEquals(driver.findElements(popup).size(), 0);
		} else System.out.println("--pop-up khong hien thi--");
		
		
	}
	
	//@Test
	public void TC_04_Fixed_Popup_Not_In_DOM() {
		driver.get("https://www.facebook.com/");
		driver.findElement(By.xpath("//a[text()='Create new account']")).click();
		
		By popup = By.xpath("//div[text()='Sign Up']/parent::div/parent::div/parent::div");
		
		//verify pop-up hien thi
		Assert.assertTrue(driver.findElement(popup).isDisplayed());
		
		//click close popup
		driver.findElement(By.xpath("//div[text()='Sign Up']/parent::div/preceding-sibling::img")).click();
		
		//Verify register pop-up ko con hien thi
		
		List<WebElement> popuphienthi = driver.findElements(popup);
		
		if(popuphienthi.size()>0) {
			System.out.println("-----------Popup hien thi-------------");
			driver.findElement(By.xpath("//div[text()='Sign Up']/parent::div/preceding-sibling::img")).click();
			sleepInSecond(2);
			
			Assert.assertEquals(driver.findElements(popup).size(), 0);
		} else System.out.println("--pop-up khong hien thi--");
		
	}

	//@Test
	public void TC_05_Random_Popup_In_DOM() {
		//Step 1: 
		driver.get("https://vnk.edu.vn/");
		By popup = By.xpath("//div[@class='tve-content-box-background cb_style_7-bg']");
		
		//step 2: kiem tra pop-up hien thi khong, neu ko hien thi thi qua step 3
		if(driver.findElement(popup).isDisplayed()) {
			System.out.println("-------Popup hien thi va click----------");
			driver.findElement(By.xpath("//div[contains(@class,'tve_ea_thrive_leads_form_close ')]")).click();
			sleepInSecond(2);
			Assert.assertFalse(driver.findElement(popup).isDisplayed());
		}else System.out.println("--------------Popup khong hien thi-----------");
		
		
	}
	//@Test
	public void TC_06_Random_Popup_In_DOM() {
		//Step 1: 
		driver.get("http://www.kmplayer.com/");
		By popup = By.xpath("//div[@class='pop-container']");
		
		//step 2: kiem tra pop-up hien thi khong, neu ko hien thi thi qua step 3
		if(driver.findElement(popup).isDisplayed()) {
			System.out.println("-------Popup hien thi va click close----------");
			driver.findElement(By.xpath("//div[@class='pop-container']//div[@class='close']")).click();
			sleepInSecond(2);
			Assert.assertFalse(driver.findElement(popup).isDisplayed());
		}else System.out.println("--------------Popup khong hien thi-----------");
		
		
	}
	//@Test
	public void TC_07_Random_Popup_Not_In_DOM() {
		//Step 1: 
		driver.get("https://dehieu.vn/");
		sleepInSecond(3);
		By popup = By.cssSelector("section#popup div.popup-content");
		
	
		//step 2: kiem tra pop-up hien thi khong, neu ko hien thi thi qua step 3
		List<WebElement> popupElements = driver.findElements(popup);
		
		if(popupElements.size()>0) {
			System.out.println("-------Popup hien thi va click close----------");
			driver.findElement(By.cssSelector("section#popup div.popup-content button#close-popup")).click();
			sleepInSecond(2);
			//Assert.assertFalse(driver.findElement(popup).isDisplayed());
		}else System.out.println("--------------Popup khong hien thi-----------");
		
		driver.findElement(By.xpath("//a[text()='Đăng nhập']")).click();
		sleepInSecond(3);
		driver.findElement(By.xpath("//button[text()='Đăng nhập']")).click();
		sleepInSecond(3);
		Assert.assertTrue(driver.findElement(By.xpath("//div[contains(text(),'Bạn chưa nhập email hoặc số điện thoại!')]")).isDisplayed());
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
