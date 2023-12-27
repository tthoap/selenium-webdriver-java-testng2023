package webdriver;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic05_Web_Element_Method_Exercise {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	
	//khai báo biến
	String firstName, lastName,fullName, email, password;
	
	By emailTextBox = By.id("mail");
	By educationTextarea = By.id("edu");
	By under18radio = By.id("under_18");
	By java = By.id("java");
	
	By disablePassword = By.id("disable_password");
	By radioDisabled = By.id("radio-disabled");
	By biology = By.id("bio");
	By job3 = By.id("job3");
	By slider2 = By.id("slider-2");
	
	
	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Windows")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		}

		//khởi tạo biến driver
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		//Khởi tạo data test
		firstName = "Omasa";
		lastName = "Bin laden";
		fullName = firstName + " " + lastName;
		email = "Omasa" + generateEmail();
		password = "123456789";

		
	}
	
	//@Test
	public void TC_01_Create_new_account() {
		//Launch the app
		driver.get("http://live.techpanda.org/");	
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		driver.findElement(By.xpath("//a[@title='Create an Account']")).click();
		driver.findElement(By.id("firstname")).sendKeys(firstName);
		driver.findElement(By.id("lastname")).sendKeys(lastName);
		driver.findElement(By.id("email_address")).sendKeys(email);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.id("confirmation")).sendKeys(password);
		
		driver.findElement(By.xpath("//button[@title='Register']")).click();
		
		Assert.assertEquals(driver.findElement(By.xpath("//li[@class='success-msg']//span")).getText(), "Thank you for registering with Main Website Store.");
		
		//Dùng hàm isDisplay để kiểm tra
		Assert.assertTrue(driver.findElement(By.xpath("//h3[text()=\"Contact Information\"]/parent::div/following-sibling::div/p[contains(string(),'" + fullName + "')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//h3[text()=\"Contact Information\"]/parent::div/following-sibling::div/p[contains(string(),'" + email + "')]")).isDisplayed());

		//Cách verify thứ 2
		String contactInfo = driver.findElement(By.xpath("//h3[text()=\"Contact Information\"]/parent::div/following-sibling::div/p")).getText();
		System.out.println(contactInfo);
		Assert.assertTrue(contactInfo.contains(fullName));
		Assert.assertTrue(contactInfo.contains(email));
		
		driver.findElement(By.cssSelector(".skip-account")).click();
		driver.findElement(By.cssSelector("a[title='Log Out']")).click();
		
	}
	
	
	//@Test
	public void TC_02_Create_new_account() {
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		driver.findElement(By.cssSelector("#email")).sendKeys(email);
		driver.findElement(By.cssSelector("#pass")).sendKeys(password);
		driver.findElement(By.xpath("//span[text()='Login']")).click();
		
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='welcome-msg']//strong")).getText(), "Hello, " + fullName +"!");
	
	}
	
	//@Test
	public void TC03_Displayed() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		By emailTextBox = By.id("mail");
		By educationTextarea = By.id("edu");
		By under18radio = By.id("under_18");
		
		if(isElementDisplayed(emailTextBox)){
			sendKey(emailTextBox, "hoaTran");
		}
		 
	
		if(isElementDisplayed(educationTextarea)) {
			sendKey(educationTextarea, "hoaTran");
		}
		
		
		if(isElementDisplayed(under18radio)) {
			clickToElement(under18radio);
		}
	}
	
	
	
	//@Test
	public void TC04_Selected() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		clickToElement(under18radio);
		clickToElement(java);
		
		//Verify checkbox/ radio đã dc check
		Assert.assertTrue(isElementSelected(under18radio));
		Assert.assertTrue(isElementSelected(java));

		clickToElement(java);
		
		Assert.assertFalse(isElementSelected(java));
		Assert.assertTrue(isElementSelected(under18radio));

	}
	
	//@Test
	public void TC05_Enabled() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		//Expected Enable
		Assert.assertTrue(isElementEnabled(emailTextBox));
		Assert.assertTrue(isElementEnabled(educationTextarea));
		Assert.assertTrue(isElementEnabled(under18radio));
		Assert.assertTrue(isElementEnabled(java));

		//Expected disaled
		Assert.assertFalse(isElementEnabled(disablePassword));
		Assert.assertFalse(isElementEnabled(radioDisabled));
		Assert.assertFalse(isElementEnabled(job3));
		Assert.assertFalse(isElementEnabled(slider2));
		Assert.assertFalse(isElementEnabled(biology));		
		
	}
	
	@Test
	public void TC06_Register_validate() {
		driver.get("https://login.mailchimp.com/signup/");
		
		By pwtextbox = By.id("new_password");
		By uppercasecompleted = By.cssSelector(".uppercase-char.completed");
		By lowercasecompleted = By.cssSelector(".lowercase-char.completed");
		By oneNumber = By.cssSelector(".number-char.completed");
		By specialcompleted = By.cssSelector(".special-char.completed");
		By eightcharcompleted = By.xpath("//li[@class='8-char completed']");
		By usernamecheckcompleted = By.cssSelector(".username-check.completed");
	//	By signUpbtn = By.cssSelector("#create-account-enabled");
		
		
		driver.findElement(By.id("email")).sendKeys("hoa@mail.com");
		driver.findElement(By.id("new_username")).sendKeys("hoa");
		
		//Uppercase
		driver.findElement(pwtextbox).sendKeys("A");
		Assert.assertEquals(driver.findElement(uppercasecompleted).getCssValue("color"), "rgb(167, 50, 5)");

		
		//Lowercase
		driver.findElement(pwtextbox).clear();
		driver.findElement(pwtextbox).sendKeys("a");
		Assert.assertEquals(driver.findElement(lowercasecompleted).getCssValue("color"), "rgb(167, 50, 5)");
		
		//at least 1 number
		driver.findElement(pwtextbox).clear();
		driver.findElement(pwtextbox).sendKeys("1");
		Assert.assertEquals(driver.findElement(oneNumber).getCssValue("color"), "rgb(167, 50, 5)");
		
		//contains special char
		driver.findElement(pwtextbox).clear();
		driver.findElement(pwtextbox).sendKeys("@");
		Assert.assertEquals(driver.findElement(specialcompleted).getCssValue("color"), "rgb(167, 50, 5)");
		
		//contains 8 chars
		driver.findElement(pwtextbox).clear();
		driver.findElement(pwtextbox).sendKeys("111122223");
		Assert.assertEquals(driver.findElement(eightcharcompleted).getCssValue("color"), "rgb(167, 50, 5)");
		
		//not contains username
		driver.findElement(pwtextbox).clear();
		driver.findElement(pwtextbox).sendKeys("hoa	");
		Assert.assertEquals(driver.findElement(usernamecheckcompleted).getCssValue("color"), "rgb(167, 50, 5)");
		

		
	}
	
	@AfterClass
	public void afterClass() {
		//driver.quit();
	}
	
	public boolean isElementDisplayed(By by) {
		if(driver.findElement(by).isDisplayed()) {
			System.out.println(by + " is displayed");
			return true;
		}else {
			System.out.println(by + " is not displayed");
			return false;
		}
	}
	
	public boolean isElementSelected(By by) {
		if(driver.findElement(by).isSelected()) {
			System.out.println(by + " is selected");
			return true;
		}else {
			System.out.println(by + " is not selected");
			return false;
		}
	}
	
	public boolean isElementEnabled(By by) {
		if(driver.findElement(by).isEnabled()) {
			System.out.println(by + " is enabled");
			return true;
		}else {
			System.out.println(by + " is disabled");
			return false;
		}
	}
	
	public void sendKey(By by, String value) {
		driver.findElement(by).clear();
		driver.findElement(by).sendKeys(value);
	}
	
	public void clickToElement(By by) {
		driver.findElement(by).click();
	}
	
	
	public String generateEmail() {
		Random rand = new Random();;
		return rand.nextInt(9999) + "@mail.vn";
	}

}
