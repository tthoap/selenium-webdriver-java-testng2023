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

	@Test
	public void TC_01_() {
		navigateToUrlByJS("http://live.techpanda.org/");
		sleepInSecond(5);
		String liveGuruDOmain = (String) executeForBrowser("return document.domain");
		Assert.assertEquals(liveGuruDOmain, "live.techpanda.org");
		
		String liveGuruUrl = (String) executeForBrowser("return document.URL");
		Assert.assertEquals(liveGuruUrl, "http://live.techpanda.org/");
		
		hightlightElement("//a[text()='Mobile']");
		clickToElementByJS("//a[text()='Mobile']");
		
		hightlightElement("//a[text()='Samsung Galaxy']/parent::h2/following-sibling::div[@class='actions']//span[text()='Add to Cart']");
		clickToElementByJS("//a[text()='Samsung Galaxy']/parent::h2/following-sibling::div[@class='actions']//span[text()='Add to Cart']");
		
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

	@Test
	public void TC_02_() {
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
	
	public Object executeForBrowser(String javaScript) {
		return jsExecutor.executeScript(javaScript);
	}

	public String getInnerText() {
		return (String) jsExecutor.executeScript("return document.documentElement.innerText;");
	}

	public boolean isExpectedTextInInnerText(String textExpected) {
		String textActual = (String) jsExecutor.executeScript("return document.documentElement.innerText.match('" + textExpected + "')[0];");
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
		jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1])", element, "border: 2px solid red; border-style: dashed;");
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
		jsExecutor.executeScript("arguments[0].setAttribute('" + attributeName + "', '" + attributeValue +"');", getElement(locator));
	}

	public void removeAttributeInDOM(String locator, String attributeRemove) {
		jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", getElement(locator));
	}
	
	public void sendkeyToElementByJS(String locator, String value) {
		jsExecutor.executeScript("arguments[0].setAttribute('value', '" + value + "')", getElement(locator));
	}
	
	public String getAttributeInDOM(String locator, String attributeName) {
		return (String) jsExecutor.executeScript("return arguments[0].getAttribute('" + attributeName + "');", getElement(locator));
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
