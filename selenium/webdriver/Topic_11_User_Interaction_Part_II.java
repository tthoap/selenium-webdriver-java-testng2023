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
public class Topic_11_User_Interaction_Part_II {
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
	public void TC_01_Right_Click() {
		driver.get("http://swisnl.github.io/jQuery-contextMenu/demo.html");
		action.contextClick(driver.findElement(By.xpath("//span[@class='context-menu-one btn btn-neutral']"))).perform();
		sleepInSecond(2);
		
		action.moveToElement(driver.findElement(By.cssSelector("li.context-menu-icon-quit"))).perform();
		sleepInSecond(2);
		
		Assert.assertTrue(driver.findElement(By.cssSelector("li.context-menu-visible.context-menu-hover")).isDisplayed());
		sleepInSecond(2);
		
		action.click(driver.findElement(By.cssSelector("li.context-menu-icon-quit"))).perform();
		sleepInSecond(2);
		
		driver.switchTo().alert().accept();
		sleepInSecond(2);
		Assert.assertFalse(driver.findElement(By.cssSelector("li.context-menu-icon-quit")).isDisplayed());
		
		
		
		
		
	}
	@Test
	public void TC_02_Drag_Drop_HTML4() {
		driver.get("https://automationfc.github.io/kendo-drag-drop/");
		WebElement smallCirle = driver.findElement(By.id("draggable"));
		WebElement bigCirle = driver.findElement(By.id("droptarget"));
		
		Assert.assertEquals(bigCirle.getText(), "Drag the small circle here.");
		
		action.dragAndDrop(smallCirle, bigCirle).perform();
		sleepInSecond(2);

		Assert.assertEquals(bigCirle.getText(), "You did great!");
		
		
		
	}

	//@Test
	public void TC_02_Click_And_Hold_Block() {
		driver.get("https://automationfc.github.io/jquery-selectable/");
		
	}
	
	//@Test
	public void TC_02_Click_And_Hold_Random() {
		driver.get("https://automationfc.github.io/jquery-selectable/");
		

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
