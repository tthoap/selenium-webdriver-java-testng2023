package webdriver;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic06_textbox_textarea {
	WebDriver driver;

	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	JavascriptExecutor jsExecutor;

	String emaillAddress, loginPageUrl;
	String userID, passWord, name, gender, dataOfbithInput, dateOfbirthOutput, addressInput, addressOutput, city, state,
			pin, phone, email;
	String customerID, editAddressInput, editAddressOutput, editCity, editState, editPhone, editEmail, editPin;
	
	

	By nameTextBoxBy = By.name("name");
	By genderRadioBy = By.xpath("//input[@value='f']");
	By genderTextboxBy = By.name("gender");
	By dateOfBirthTextBoxBy = By.name("dob");
	By addressTextAreaBy = By.name("addr");
	By cityTextboxBy = By.name("city");
	By stateTextBoxBy = By.name("state");
	By phoneTextboxBy = By.name("telephoneno");
	By pinTextboxBy = By.name("pinno");
	By emailTextBoxBy = By.name("emailid");
	By passwordTextboxBy = By.name("password");

	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Windows")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		}
		
		//ép kiểu tường minh
		jsExecutor = (JavascriptExecutor) driver;
		
		name = "Hoatran";
		gender = "female";
		dataOfbithInput = "01/01/1990";
		dateOfbirthOutput = "1990/01/01";
		addressInput = "234 PO Bridge\nNewYork";
		addressOutput = "234 PO Bridge NewYork";
		city = "Los Angle";
		state = "California";
		pin = "225599";
		phone = "0971809077";
		email = "Angle" + generateEmail() + "@mail.net";

		editAddressInput = "200 PO Bridge\nNewYork";
		editAddressOutput = "200 PO Bridge NewYork";
		editCity = "Angeless";
		editState = "mancty";
		editPhone = "9875111121";
		editEmail = "Angle" + generateEmail() + "@hotmail.com";
		editPin = "233000";

		// khởi tạo biến driver
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get("http://demo.guru99.com/v4");

	}

	@Test
	public void TC01_Register() {
		loginPageUrl = driver.getCurrentUrl();

		driver.findElement(By.xpath("//a[text()='here']")).click();
		driver.findElement(By.name("emailid")).sendKeys(email);
		driver.findElement(By.name("btnLogin")).click();

		userID = driver.findElement(By.xpath("//td[text()='User ID :']/following-sibling::td")).getText();
		System.out.println(userID);
		passWord = driver.findElement(By.xpath("//td[text()='Password :']/following-sibling::td")).getText();
		System.out.println(passWord);
	}

	@Test
	public void TC02_login() {
		driver.get(loginPageUrl);
		driver.findElement(By.name("uid")).sendKeys(userID);
		driver.findElement(By.name("password")).sendKeys(passWord);
		driver.findElement(By.name("btnLogin")).click();

		// Verify
		Assert.assertTrue(driver
				.findElement(By
						.xpath("//marquee[@class='heading3' and text()=\"Welcome To Manager's Page of Guru99 Bank\"]"))
				.isDisplayed());

	}

	@Test
	public void TC03_New_Customer() {
		driver.findElement(By.xpath("//a[text()='New Customer']")).click();

		driver.findElement(nameTextBoxBy).sendKeys(name);
		driver.findElement(genderRadioBy).sendKeys(gender);
		sleepInSecond(8000);
		jsExecutor.executeScript("arguments[0].removeAttribute('type')", driver.findElement(dateOfBirthTextBoxBy));
		sleepInSecond(3000);
		
		
		driver.findElement(dateOfBirthTextBoxBy).sendKeys(dateOfbirthOutput);
		driver.findElement(addressTextAreaBy).sendKeys(addressInput);
		driver.findElement(cityTextboxBy).sendKeys(city);
		driver.findElement(stateTextBoxBy).sendKeys(state);
		driver.findElement(pinTextboxBy).sendKeys(pin);
		driver.findElement(phoneTextboxBy).sendKeys(phone);
		driver.findElement(emailTextBoxBy).sendKeys(email);
		driver.findElement(passwordTextboxBy).sendKeys(passWord);
		driver.findElement(By.name("sub")).click();

		// Verify
		Assert.assertTrue(driver
				.findElement(By.xpath("//p[@class=\"heading3\" and text()=\"Customer Registered Successfully!!!\"]"))
				.isDisplayed());
		Assert.assertEquals(
				driver.findElement(By.xpath("//td[text()='Customer Name']/following-sibling::td")).getText(), name);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Gender']/following-sibling::td")).getText(),
				gender);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Birthdate']/following-sibling::td")).getText(),
				dateOfbirthOutput);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Address']/following-sibling::td")).getText(),
				addressOutput);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='City']/following-sibling::td")).getText(), city);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Pin']/following-sibling::td")).getText(), pin);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='State']/following-sibling::td")).getText(),
				state);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Mobile No.']/following-sibling::td")).getText(),
				phone);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Email']/following-sibling::td")).getText(),
				email);
		customerID = driver.findElement(By.xpath("//td[text()='Customer ID']/following-sibling::td")).getText();

	}

	@Test
	public void TC04_Update_User() {
		driver.findElement(By.xpath("//a[text()='Edit Customer']")).click();
		driver.findElement(By.name("cusid")).sendKeys(customerID);
		driver.findElement(By.name("AccSubmit")).click();

		// Verify
		Assert.assertEquals(driver.findElement(nameTextBoxBy).getAttribute("value"), name);
		Assert.assertEquals(driver.findElement(genderTextboxBy).getAttribute("value"), gender);
		Assert.assertEquals(driver.findElement(dateOfBirthTextBoxBy).getAttribute("value"), dateOfbirthOutput);
		Assert.assertEquals(driver.findElement(addressTextAreaBy).getAttribute("value"), addressOutput);
		Assert.assertEquals(driver.findElement(cityTextboxBy).getAttribute("value"), city);
		Assert.assertEquals(driver.findElement(stateTextBoxBy).getAttribute("value"), state);
		Assert.assertEquals(driver.findElement(phoneTextboxBy).getAttribute("value"), phone);
		Assert.assertEquals(driver.findElement(pinTextboxBy).getAttribute("value"), pin);
		Assert.assertEquals(driver.findElement(emailTextBoxBy).getAttribute("value"), email);

		// Edit

		driver.findElement(addressTextAreaBy).clear();
		driver.findElement(addressTextAreaBy).sendKeys(editAddressInput);
		driver.findElement(cityTextboxBy).clear();
		driver.findElement(cityTextboxBy).sendKeys(editCity);
		driver.findElement(stateTextBoxBy).clear();
		driver.findElement(stateTextBoxBy).sendKeys(editState);
		driver.findElement(pinTextboxBy).clear();
		driver.findElement(pinTextboxBy).sendKeys(editPin);
		driver.findElement(phoneTextboxBy).clear();
		driver.findElement(phoneTextboxBy).sendKeys(editPhone);
		driver.findElement(emailTextBoxBy).clear();
		driver.findElement(emailTextBoxBy).sendKeys(editEmail);
		driver.findElement(By.name("sub")).click();
		
		//Verify 
		Assert.assertTrue(driver
				.findElement(By.xpath("//p[@class=\"heading3\" and text()='Customer details updated Successfully!!!']"))
				.isDisplayed());
		
		Assert.assertEquals(
				driver.findElement(By.xpath("//td[text()='Customer Name']/following-sibling::td")).getText(), name);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Gender']/following-sibling::td")).getText(),
				gender);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Birthdate']/following-sibling::td")).getText(),
				dateOfbirthOutput);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Address']/following-sibling::td")).getText(),
				editAddressOutput);
		
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='City']/following-sibling::td")).getText(), editCity);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Pin']/following-sibling::td")).getText(), editPin);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='State']/following-sibling::td")).getText(),
				editState);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Mobile No.']/following-sibling::td")).getText(),
				editPhone);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Email']/following-sibling::td")).getText(),
				editEmail);

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

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}
