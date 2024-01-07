package webdriver;

import static org.testng.Assert.assertTrue;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

	@Test
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

	@Test
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
	
	@Test
	public void TC_04_Radio_button() {
		driver.get("https://material.angular.io/components/radio/examples");
		

		By winterRadioInput = By.xpath("//input[@value='Winter']");
		jsEx.executeScript("arguments[0].click();", driver.findElement(winterRadioInput));
		sleepInSecond(2);
		Assert.assertTrue(driver.findElement(winterRadioInput).isSelected());
	}
	
	
	@Test
	public void TC_05_checkBox() {
		driver.get("https://material.angular.io/components/checkbox/examples");
		
		By checkedBox = By.xpath("//label[text()='Checked']/preceding-sibling::div[@class='mdc-checkbox']//input");
		By indeterminateBox = By.xpath("//label[text()='Indeterminate']/preceding-sibling::div[@class='mdc-checkbox']//input");

		checkToCheckboxByJS(checkedBox);
		Assert.assertTrue(driver.findElement(By.xpath("//label[text()='Checked']/preceding-sibling::div[@class='mdc-checkbox']//input")).isSelected());
		sleepInSecond(1);

		checkToCheckboxByJS(indeterminateBox);
		sleepInSecond(2);
		Assert.assertTrue(driver.findElement(By.xpath("//label[text()='Indeterminate']/preceding-sibling::div[@class='mdc-checkbox']//input")).isSelected());

		uncheckToCheckboxByJS(checkedBox);
		uncheckToCheckboxByJS(indeterminateBox);
		
		sleepInSecond(2);
		Assert.assertFalse(driver.findElement(checkedBox).isSelected());
		Assert.assertFalse(driver.findElement(indeterminateBox).isSelected());
			
	}
	
	@Test
	public void TC_06_Radio_checkBox_GoogleDocs() {
		driver.get("https://docs.google.com/forms/d/e/1FAIpQLSfiypnd69zhuDkjKgqvpID9kwO29UCzeCVrGGtbNPZXQok0jA/viewform");
		By canTho = By.xpath("//div[@aria-label=\"Cần Thơ\" and @aria-checked='false']");
		Assert.assertTrue(driver.findElement(canTho).isDisplayed());

		By canThochecked = By.xpath("//div[@aria-label=\"Cần Thơ\" and @aria-checked='true']");
		checkToCheckbox(canTho);
		sleepInSecond(1);
		Assert.assertTrue(driver.findElement(canThochecked).isDisplayed());
		
		//Click all checkboxes
		List<WebElement> checkboxes = driver.findElements(By.xpath("//div[@role='checkbox']"));
		for (WebElement checkbox : checkboxes) {
			checkbox.click();
			sleepInSecond(1);
			
		}
		//Verify all checkboxes are checked
		for (WebElement checkbox : checkboxes) {
			Assert.assertEquals(checkbox.getAttribute("aria-checked"),"true");
			
		}
		
	}
	
	public void checkToCheckboxByJS(By by) {
		if(!driver.findElement(by).isSelected()) {
			jsEx.executeScript("arguments[0].click();", driver.findElement(by));
		}
	}
	public void uncheckToCheckboxByJS(By by) {
		if(driver.findElement(by).isSelected()) {
			jsEx.executeScript("arguments[0].click();", driver.findElement(by));
		}
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
