package webdriver;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
public class Topic_11_User_Interaction_Part_I {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	JavascriptExecutor jsEx;
	Actions action;
	Keys key;
	
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
		jsEx = (JavascriptExecutor)driver;
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
		if(osName.contains("Window")) {
			key = Keys.CONTROL;
		}else key = Keys.COMMAND;
	}

	//@Test
	public void TC_01_Hover() {
		driver.get("https://automationfc.github.io/jquery-tooltip/");
		
		action.moveToElement(driver.findElement(By.id("age"))).perform();
		sleepInSecond(2);
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='ui-tooltip-content' and text()='We ask for your age only for statistical purposes.']")).isDisplayed());
		
	}
	//@Test
	public void TC_01_Hover_Menu() {
		driver.get("https://yody.vn/");
		
		action.moveToElement(driver.findElement(By.xpath("//div[contains(@class,'wrap-container')]//a[@title='TRẺ EM']"))).perform();
		sleepInSecond(2);
		
		action.click(driver.findElement(By.xpath("//div[contains(@class,'wrap-container')]//a[@title='Quần trẻ em']"))).perform();

		Assert.assertTrue(driver.findElement(By.xpath("//span[contains(text(),'Quần trẻ em')]")).isDisplayed());
	
	}

	//@Test
	public void TC_02_Click_And_Hold_Block() {
		driver.get("https://automationfc.github.io/jquery-selectable/");
		List<WebElement> allNumber = driver.findElements(By.cssSelector("ol#selectable>li"));
		//click and hold (1-4)
		action.clickAndHold(allNumber.get(0)).moveToElement(allNumber.get(3)).release().perform();
		Assert.assertEquals(driver.findElements(By.cssSelector("ol#selectable>li[class$='ui-selected']")).size(), 4);
	}
	
	//@Test
	public void TC_02_Click_And_Hold_Random() {
		driver.get("https://automationfc.github.io/jquery-selectable/");
		List<WebElement> allNumber = driver.findElements(By.cssSelector("ol#selectable>li"));
	
		//Nhấn ctrl xuống
		action.keyDown(key).perform();
		
		//chọn random
		action.click(allNumber.get(0)).click(allNumber.get(19)).perform();
		
		//nhả phím
		action.keyUp(key).perform();
		sleepInSecond(5);

	}

	@Test
	public void TC_04_Double_Click() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		
		//scroll tới element trước khi click
		jsEx.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//button[text()='Double click me']")));
		sleepInSecond(2);
		action.doubleClick(driver.findElement(By.xpath("//button[text()='Double click me']"))).perform();
		sleepInSecond(2);
		Assert.assertTrue(driver.findElement(By.xpath("//p[@id='demo' and text()='Hello Automation Guys!']")).isDisplayed());


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
