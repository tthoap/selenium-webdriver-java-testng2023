package webdriver;

import java.time.Duration;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.common.base.Function;
import com.google.common.base.Functions;

public class Topic_17_Wait_Part_VII_Fluent {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	FluentWait<WebDriver> fluentWaitDriver;
	FluentWait<WebElement> fluentWaitElement;

//edit
	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Windows")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		}
		
		driver = new FirefoxDriver();
	}
	
	@Test
	public void TC_01_() {
		driver.get("https://automationfc.github.io/fluent-wait/");
		fluentWaitDriver = new FluentWait<WebDriver>(driver);
		WebElement countDownTime = driver.findElement(By.xpath("//div[@id='javascript_countdown_time']"));

		fluentWaitElement = new FluentWait<WebElement>(countDownTime);

		fluentWaitElement.withTimeout(Duration.ofSeconds(15)) //wait với tổng thời gian là 15 seconds
				.pollingEvery(Duration.ofSeconds(1)) // cơ chế tìm lại nếu chưa thõa mãn điều kiện là mỗi 1 giây tìm lại 1 lần
				.ignoring(NoSuchElementException.class) // nếu trong thời gian tìm lại mà không thấy element thì trả exception
				.until(new Function<WebElement, Boolean>() {
					//Điều kiện
					public Boolean apply(WebElement element) {
						String text = element.getText();
						System.out.println("Time = " + text);
						return text.endsWith("00");
					}
		});
	}

	public void TC_02_() {
		driver.get("https://automationfc.github.io/dynamic-loading/");
		driver.findElement(By.cssSelector("div#start>button")).click();
		

	}

	public String getDateTimeNow() {
		Date date = new Date();
		return date.toString();
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
