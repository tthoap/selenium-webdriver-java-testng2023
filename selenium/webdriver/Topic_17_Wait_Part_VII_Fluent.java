package webdriver;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.common.base.Function;

public class Topic_17_Wait_Part_VII_Fluent {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	FluentWait<WebDriver> fluentWaitDriver;
	FluentWait<WebElement> fluentWaitElement;
	JavascriptExecutor jsExecutor;
	WebDriverWait explicitWait;
	Actions action;


//edit
	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Windows")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		}

		driver = new FirefoxDriver();
		action = new Actions(driver);
	}

	// @Test
	public void TC_01_() {
		driver.get("https://automationfc.github.io/fluent-wait/");
		fluentWaitDriver = new FluentWait<WebDriver>(driver);
		WebElement countDownTime = driver.findElement(By.xpath("//div[@id='javascript_countdown_time']"));

		fluentWaitElement = new FluentWait<WebElement>(countDownTime);

		fluentWaitElement.withTimeout(Duration.ofSeconds(15)) // wait với tổng thời gian là 15 seconds
				.pollingEvery(Duration.ofSeconds(1)) // cơ chế tìm lại nếu chưa thõa mãn điều kiện là mỗi 1 giây tìm lại 1 lần
				.ignoring(NoSuchElementException.class) // nếu trong thời gian tìm lại mà không thấy element thì trả exception
				.until(new Function<WebElement, Boolean>() {
					// Điều kiện
					public Boolean apply(WebElement element) {
						String text = element.getText();
						System.out.println("Time = " + text);
						return text.endsWith("00");
					}
				});
	}

	// @Test
	public void TC_02_() {
		driver.get("https://automationfc.github.io/dynamic-loading/");
		driver.findElement(By.cssSelector("div#start>button")).click();

		fluentWaitDriver = new FluentWait<WebDriver>(driver);
		fluentWaitDriver.withTimeout(Duration.ofSeconds(6)).pollingEvery(Duration.ofSeconds(1)).ignoring(NoSuchElementException.class);
		WebElement heloworldText = fluentWaitDriver.until(new Function<WebDriver, WebElement>() {

			public WebElement apply(WebDriver driver) {
				return driver.findElement(By.cssSelector("div#finish>h4"));
			}
		});
		Assert.assertEquals(heloworldText.getText(), "Hello World!");
	}

	//@Test
	public void TC_03_() {
		driver.get("https://automationfc.github.io/dynamic-loading/");
		driver.findElement(By.cssSelector("div#start>button")).click();
		
		fluentWaitDriver = new FluentWait<WebDriver>(driver);
		fluentWaitDriver.withTimeout(Duration.ofSeconds(6)).pollingEvery(Duration.ofSeconds(1)).ignoring(NoSuchElementException.class).until(new Function<WebDriver, Boolean>() {
			
			public Boolean apply(WebDriver driver) {
				return driver.findElement(By.cssSelector("div#finish>h4")).getText().equals("Hello World!");
			}
		});
		
	}
	
	//@Test
	public void TC_04_() {
		driver.get("https://automationfc.github.io/dynamic-loading/");
		waitForElementAndClick(By.cssSelector("div#start>button"));
		Assert.assertEquals(getWebElement(By.cssSelector("div#finish>h4")).getText(), "Hello World!");
		Assert.assertTrue(waitForElementAndDisplayed(By.xpath("//div[@id='finish']/h4[text()='Hello World!']")));
	}
	
	//@Test
	public void TC_05_() {
		driver.get("https://admin-demo.nopcommerce.com");
		
		getWebElement(By.id("Email")).clear();
		getWebElement(By.id("Password")).clear();
		getWebElement(By.id("Email")).sendKeys("admin@yourstore.com");
		getWebElement(By.id("Password")).sendKeys("admin");
		getWebElement(By.xpath("//button[text()='Log in']")).click();
		Assert.assertTrue(isPageLoadedSuccess(driver));
		
		getWebElement(By.xpath("//a[text()='Logout']")).click();
		Assert.assertTrue(isPageLoadedSuccess(driver));
		waitForElementAndDisplayed(By.xpath("//strong[text()='Welcome, please sign in!']"));
		
	}
	
	@Test
	public void TC_06_() {
		driver.get("https://blog.testproject.io/");

		/*
		 * List<WebElement> popuphienthi = driver.findElements(By.xpath("//div[@class='pop-up']"));
		 * 
		 * if(popuphienthi.size()>0) { System.out.println("-----------Popup hien thi-------------"); driver.findElement(By.xpath("//img[@class='close-img']")).click();
		 * sleepInSecond(2);
		 * 
		 * Assert.assertEquals(driver.findElements(By.xpath("")).size(), 0); } else System.out.println("--pop-up khong hien thi--");
		 */
		
		//Nếu pop-up hiển thị thì close, còn không thì tiếp step sau
		action.moveToElement(driver.findElement(By.xpath("//section[@id='search-2']//input[@class='search-field']")));
		Assert.assertTrue(isPageLoadedSuccess(driver));
		
		getWebElement(By.xpath("//section[@id='search-2']//input[@class='search-field']")).clear();
		getWebElement(By.xpath("//section[@id='search-2']//input[@class='search-field']")).sendKeys("Selenium");
		getWebElement(By.xpath("//section[@id='search-2']//span[@class='glass']")).click();
		
		List<WebElement> searchTitle = driver.findElements(By.xpath("//h3[@class='post-title']/a"));
		for (WebElement title : searchTitle) {
			Assert.assertTrue(title.getText().contains("Selenium"));
			System.out.println("Title = "+ title.getText());
		}
		
	}
	
	
	//Find Element (Custom)
	public WebElement getWebElement(final By locator) {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(30))
				.pollingEvery(Duration.ofSeconds(1))
				.ignoring(NoSuchElementException.class);
		WebElement element = wait.until(new Function<WebDriver, WebElement>() {
			
			public WebElement apply(WebDriver driver) {
				return driver.findElement(locator);
			}
		});
		return element;
	}
	
	public void waitForElementAndClick(By locator) {
		WebElement element = getWebElement(locator);
		element.click();
	}
	public boolean waitForElementAndDisplayed(By locator) {
		WebElement element = getWebElement(locator);
		return element.isDisplayed();
	}
	
	
	
	public boolean isPageLoadedSuccess(WebDriver driver) {
		explicitWait = new WebDriverWait(driver, 600);
		jsExecutor = (JavascriptExecutor) driver;
		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {

			public Boolean apply(WebDriver driver) {
				// TODO Auto-generated method stub
				return (Boolean) jsExecutor.executeScript("return (window.jQuery != null) && (jQuery.active==0);");
			}
		};
		return explicitWait.until(jQueryLoad);
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
