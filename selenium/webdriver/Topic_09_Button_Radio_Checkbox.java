package webdriver;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.Color;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
public class Topic_09_Button_Radio_Checkbox {
	WebDriver driver;
	JavascriptExecutor jsEx;
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
		jsEx = (JavascriptExecutor) driver;
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
	}

	//@Test
	public void TC_01_Button() {
		driver.get("https://www.fahasa.com/customer/account/create");
		By loginButton = By.cssSelector("button.fhs-btn-login");
		
		driver.findElement(By.cssSelector("li.popup-login-tab-login")).click();
		//Verify Login button is disabled
		Assert.assertFalse(driver.findElement(loginButton).isEnabled());
		
		
		driver.findElement(By.cssSelector("input#login_username")).sendKeys("hoa@gmail.com");
		driver.findElement(By.cssSelector("input#login_password")).sendKeys("12345678");
		sleepInSecond(1);
		
		//Verify login button is enabled
		Assert.assertTrue(driver.findElement(loginButton).isEnabled());

		
		driver.navigate().refresh();
		sleepInSecond(2);
		driver.findElement(By.cssSelector("li.popup-login-tab-login")).click();
		sleepInSecond(2);
		//Remove disabled attribute of login button
		jsEx.executeScript("arguments[0].removeAttribute('disabled');", driver.findElement(loginButton));
		sleepInSecond(2);
		
		//Verify login button  with background color = RED
		String rgbaColor = driver.findElement(loginButton).getCssValue("background-color");
		System.out.println("RGBA color = " + rgbaColor);
		
		String hexColor = Color.fromString(rgbaColor).asHex().toUpperCase();
		System.out.println("Hex color = " + hexColor);
		
		Assert.assertEquals(hexColor, "#C92127");
		Assert.assertEquals(Color.fromString(driver.findElement(loginButton).getCssValue("background-color")).asHex().toUpperCase(), "#C92127");
		
		driver.findElement(loginButton).click();
		//Verify alert
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='popup-login-content']//label[text()='Số điện thoại/Email']/following-sibling::div[@class='fhs-input-alert']")).getText(), "Thông tin này không thể để trống");
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='popup-login-content']//label[text()='Mật khẩu']/following-sibling::div[@class='fhs-input-alert']")).getText(), "Thông tin này không thể để trống");
		
	}

	//@Test
	public void TC_02_Radio_default() {
		driver.get("https://demos.telerik.com/kendo-ui/radiobutton/index");
		By petrolTwo = By.xpath("//label[text()='1.8 Petrol, 118kW']/preceding-sibling::input");
		Assert.assertFalse(driver.findElement(petrolTwo).isSelected());
		driver.findElement(petrolTwo).click();
		Assert.assertTrue(driver.findElement(petrolTwo).isSelected());
		driver.findElement(petrolTwo).click();
		Assert.assertTrue(driver.findElement(petrolTwo).isSelected());
		
		By diselTwo = By.xpath("//label[text()='1.6 Diesel, 77kW']/preceding-sibling::input");
		driver.findElement(diselTwo).click();
		Assert.assertFalse(driver.findElement(petrolTwo).isSelected());
		Assert.assertTrue(driver.findElement(diselTwo).isSelected());
		
		By petrolThree = By.xpath("//label[text()='3.6 Petrol, 191kW']/preceding-sibling::input");
		
		Assert.assertFalse(driver.findElement(petrolThree).isEnabled());
		

	}

	@Test
	public void TC_03_CheckBox_default() {
		driver.get("https://demos.telerik.com/kendo-ui/checkbox/index");
		
		By luggage = By.xpath("//label[text()='Luggage compartment cover']/preceding-sibling::input");
		By Rearside = By.xpath("//label[text()='Rear side airbags']/preceding-sibling::input");
		
		checkToCheckbox(Rearside);
		sleepInSecond(2);

		uncheckToCheckbox(Rearside);
		sleepInSecond(2);
		
		checkToCheckbox(luggage);
		sleepInSecond(2);
		Assert.assertTrue(driver.findElement(luggage).isSelected());
		uncheckToCheckbox(luggage);
		sleepInSecond(2);
		Assert.assertFalse(driver.findElement(luggage).isSelected());

	}
	
	public void checkToCheckbox(By by) {
		if(!driver.findElement(by).isSelected()) {
			driver.findElement(by).click();
		}
	}
	public void uncheckToCheckbox(By by) {
		if(driver.findElement(by).isSelected()) {
			driver.findElement(by).click();
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
