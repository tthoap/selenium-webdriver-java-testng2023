package webdriver;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_15_JavascriptExecutor {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	JavascriptExecutor jsExecutor;

//edit
	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Windows")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		}

		driver = new FirefoxDriver();
		jsExecutor = (JavascriptExecutor) driver;

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();

	}

	// @Test
	public void TC_01_TechPanda() {
		navigateToUrlByJS("http://live.techpanda.org/");
		sleepInSecond(5);
		String liveGuruDOmain = (String) executeForBrowser("return document.domain");
		Assert.assertEquals(liveGuruDOmain, "live.techpanda.org");

		String liveGuruUrl = (String) executeForBrowser("return document.URL");
		Assert.assertEquals(liveGuruUrl, "http://live.techpanda.org/");

		hightlightElement("//a[text()='Mobile']");
		clickToElementByJS("//a[text()='Mobile']");

		hightlightElement(
				"//a[text()='Samsung Galaxy']/parent::h2/following-sibling::div[@class='actions']//span[text()='Add to Cart']");
		clickToElementByJS(
				"//a[text()='Samsung Galaxy']/parent::h2/following-sibling::div[@class='actions']//span[text()='Add to Cart']");

		String innerTextValue = getInnerText();
		Assert.assertTrue(innerTextValue.contains("Samsung Galaxy was added to your shopping cart."));
		Assert.assertTrue(isExpectedTextInInnerText("Samsung Galaxy was added to your shopping cart."));

		hightlightElement("//a[text()='Customer Service']");
		clickToElementByJS("//a[text()='Customer Service']");

		Assert.assertEquals(executeForBrowser("return document.title;"), "Customer Service");

		scrollToElementOnTop("//input[@id='newsletter']");
		sleepInSecond(5);

		sendkeyToElementByJS("//input[@id='newsletter']", generateEmail() + "@mail.com");
		sleepInSecond(3);

		hightlightElement("//button[@title='Subscribe']");
		clickToElementByJS("//button[@title='Subscribe']");

		Assert.assertTrue(getInnerText().contains("Thank you for your subscription."));

		navigateToUrlByJS("https://www.facebook.com/");
		sleepInSecond(3);
		Assert.assertEquals(executeForBrowser("return document.domain;"), "facebook.com");

	}

	// @Test
	public void TC_02_HTML5() {
		driver.get("https://login.ubuntu.com/");
		sleepInSecond(3);
		By popup = By.xpath("//div[@role='dialog']");

		// step 2: kiem tra pop-up hien thi khong, neu ko hien thi thi qua step 3
		if (driver.findElement(popup).isDisplayed()) {
			System.out.println("-------Popup hien thi va click----------");
			driver.findElement(By.cssSelector("#cookie-policy-button-accept")).click();
			sleepInSecond(2);
			// Assert.assertFalse(driver.findElement(popup).isDisplayed());
		} else
			System.out.println("--------------Popup khong hien thi-----------");

		hightlightElement("//button[@data-qa-id='login_button']");
		driver.findElement(By.xpath("//button[@data-qa-id='login_button']")).click();
		sleepInSecond(2);
		Assert.assertEquals(getElementValidationMessage("//form[@id='login-form']//input[@id='id_email']"),
				"Vui lòng điền vào trường này.");

		driver.findElement(By.xpath("//form[@id='login-form']//input[@id='id_email']")).sendKeys("h@mm@");
		hightlightElement("//button[@data-qa-id='login_button']");
		driver.findElement(By.xpath("//button[@data-qa-id='login_button']")).click();
		sleepInSecond(2);
		Assert.assertEquals(getElementValidationMessage("//form[@id='login-form']//input[@id='id_email']"),
				"Vui lòng điền một địa chỉ email.");

		driver.findElement(By.xpath("//form[@id='login-form']//input[@id='id_email']")).clear();
		driver.findElement(By.xpath("//form[@id='login-form']//input[@id='id_email']")).sendKeys("tthoa@gmail.com");
		hightlightElement("//button[@data-qa-id='login_button']");
		driver.findElement(By.xpath("//button[@data-qa-id='login_button']")).click();
		sleepInSecond(2);
		Assert.assertEquals(getElementValidationMessage("//form[@id='login-form']//input[@id='id_password']"),
				"Vui lòng điền vào trường này.");
	}

	// @Test
	public void TC_03_RemoveAttribute() {

		String emaillAddress, loginPageUrl;
		String userID, passWord, name, gender, dataOfbithInput, dateOfbirthOutput, addressInput, addressOutput, city,
				state, pin, phone, email;

		name = "Hoatran";
		gender = "male";
		dataOfbithInput = "01/01/1990";
		dateOfbirthOutput = "1990-01-01";
		addressInput = "234 PO Bridge\nNewYork";
		addressOutput = "234 PO Bridge NewYork";
		city = "Los Angle";
		state = "California";
		pin = "225599";
		phone = "0971809077";
		email = "Angle" + generateEmail() + "@mail.net";
		passWord = "dugevYn";

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

		driver.get("http://demo.guru99.com/v4");
		sleepInSecond(2);

		driver.findElement(By.name("uid")).sendKeys("mngr549869");
		driver.findElement(By.name("password")).sendKeys("dugevYn");
		hightlightElement("//input[@name='btnLogin']");
		driver.findElement(By.name("btnLogin")).click();

		driver.findElement(By.xpath("//a[text()='New Customer']")).click();

		driver.findElement(nameTextBoxBy).sendKeys(name);
		driver.findElement(genderRadioBy).sendKeys(gender);
		sleepInSecond(3);
		jsExecutor.executeScript("arguments[0].removeAttribute('type')", driver.findElement(dateOfBirthTextBoxBy));
		removeAttributeInDOM("//input[@name='dob']", "type");
		sleepInSecond(3);

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
	}

	@Test
	public void TC_04_Create_Account() {
		navigateToUrlByJS("http://live.techpanda.org/");

		clickToElementByJS("//div[@id='header-account']//a[text()='My Account']");
		clickToElementByJS("//a[@title='Create an Account']");

		sendkeyToElementByJS("//input[@id='firstname']", "Hoaauto");
		sendkeyToElementByJS("//input[@id='lastname']", "Tran");
		sendkeyToElementByJS("//input[@id='email_address']", generateEmail() + "@gmail.com");
		sendkeyToElementByJS("//input[@id='password']", "12345678");
		sendkeyToElementByJS("//input[@id='confirmation']", "12345678");
		clickToElementByJS("//span[text()='Register']");
		sleepInSecond(3);
		Assert.assertTrue(getInnerText().contains("Thank you for registering with Main Website Store."));

		clickToElementByJS("//a[@title='Log Out']");
		Assert.assertTrue(driver.findElement(By.xpath("//p[text()='Default welcome msg! ']")).isDisplayed());

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

	public Object executeForBrowser(String javaScript) {
		return jsExecutor.executeScript(javaScript);
	}

	public String getInnerText() {
		return (String) jsExecutor.executeScript("return document.documentElement.innerText;");
	}

	public boolean isExpectedTextInInnerText(String textExpected) {
		String textActual = (String) jsExecutor
				.executeScript("return document.documentElement.innerText.match('" + textExpected + "')[0];");
		return textActual.equals(textExpected);
	}

	public void scrollToBottomPage() {
		jsExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
	}

	public void navigateToUrlByJS(String url) {
		jsExecutor.executeScript("window.location = '" + url + "'");
		sleepInSecond(3);
	}

	public void hightlightElement(String locator) {
		WebElement element = getElement(locator);
		String originalStyle = element.getAttribute("style");
		jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1])", element,
				"border: 2px solid red; border-style: dashed;");
		sleepInSecond(2);
		jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1])", element, originalStyle);
	}

	public void clickToElementByJS(String locator) {
		jsExecutor.executeScript("arguments[0].click();", getElement(locator));
		sleepInSecond(3);
	}

	public void scrollToElementOnTop(String locator) {
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", getElement(locator));
	}

	public void scrollToElementOnDown(String locator) {
		jsExecutor.executeScript("arguments[0].scrollIntoView(false);", getElement(locator));
	}

	public void setAttributeInDOM(String locator, String attributeName, String attributeValue) {
		jsExecutor.executeScript("arguments[0].setAttribute('" + attributeName + "', '" + attributeValue + "');",
				getElement(locator));
	}

	public void removeAttributeInDOM(String locator, String attributeRemove) {
		jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", getElement(locator));
	}

	public void sendkeyToElementByJS(String locator, String value) {
		jsExecutor.executeScript("arguments[0].setAttribute('value', '" + value + "')", getElement(locator));
	}

	public String getAttributeInDOM(String locator, String attributeName) {
		return (String) jsExecutor.executeScript("return arguments[0].getAttribute('" + attributeName + "');",
				getElement(locator));
	}

	public String getElementValidationMessage(String locator) {
		return (String) jsExecutor.executeScript("return arguments[0].validationMessage;", getElement(locator));
	}

	/*
	 * public boolean isImageLoaded(String locator) { boolean status = (boolean)
	 * jsExecutor.
	 * executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != 'undefined' && arguments[0].naturalWidth > 0"
	 * , getElement(locator)); return status; }
	 */

	public WebElement getElement(String locator) {
		return driver.findElement(By.xpath(locator));
	}
}
