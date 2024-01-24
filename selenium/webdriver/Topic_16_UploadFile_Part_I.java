package webdriver;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
public class Topic_16_UploadFile_Part_I {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	WebDriverWait explicitwait;
	
	String dalatName = "DaLat.jpg";
	String hueName = "Hue.jpg";
	String sapaName = "SaPa.jpg";
	String dalatpath = projectPath + "\\uploadFiles\\" + dalatName;
	String huepath = projectPath + "\\uploadFiles\\" + hueName;
	String sapapath = projectPath + "\\uploadFiles\\" + sapaName;
//edit
	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Windows")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		}

		driver = new FirefoxDriver();
		explicitwait = new WebDriverWait(driver, 20);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		//driver.manage().window().maximize();
		
	}

	//@Test
	public void TC_01_Sendkey_SingleFile() {
		driver.get("https://blueimp.github.io/jQuery-File-Upload/");
		
		driver.findElement(By.xpath("//input[@name='files[]']")).sendKeys(dalatpath);
		sleepInSecond(2);
		driver.findElement(By.xpath("//input[@name='files[]']")).sendKeys(huepath);
		sleepInSecond(2);
		driver.findElement(By.xpath("//input[@name='files[]']")).sendKeys(sapapath);
		sleepInSecond(2);
		
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='"+ dalatName +"']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='"+ hueName +"']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='"+ sapaName +"']")).isDisplayed());
		
		List<WebElement> uploadButtons = driver.findElements(By.cssSelector("tr.template-upload button.start")); 
		for (WebElement uploadButton : uploadButtons) {
			uploadButton.click();
			sleepInSecond(1);			
		}
		
		//verify Upload File success
		Assert.assertTrue(driver.findElement(By.cssSelector("p.name a[title='"+ dalatName +"']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("p.name a[title='"+ hueName +"']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("p.name a[title='"+ sapaName +"']")).isDisplayed());
	}
	
	//@Test
	public void TC_02_Sendkey_Multiple_File() {
		driver.get("https://blueimp.github.io/jQuery-File-Upload/");
		
		driver.findElement(By.xpath("//input[@name='files[]']")).sendKeys(dalatpath +"\n" + huepath +"\n" + sapapath);
		sleepInSecond(2);
		
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='"+ dalatName +"']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='"+ hueName +"']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='"+ sapaName +"']")).isDisplayed());
		
		List<WebElement> uploadButtons = driver.findElements(By.cssSelector("tr.template-upload button.start")); 
		for (WebElement uploadButton : uploadButtons) {
			uploadButton.click();
			sleepInSecond(1);			
		}
		
		//verify Upload File success
		Assert.assertTrue(driver.findElement(By.cssSelector("p.name a[title='"+ dalatName +"']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("p.name a[title='"+ hueName +"']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("p.name a[title='"+ sapaName +"']")).isDisplayed());
	}
	@Test
	public void TC_03_Go_File() {
		driver.get("https://gofile.io/uploadFiles");
		
		driver.findElement(By.cssSelector("#filesUpload button.filesUploadButton")).sendKeys(dalatpath +"\n" + huepath +"\n" + sapapath);
		//Wait cho các loading(đại diện cho các file upload) biến mất
		explicitwait.until(ExpectedConditions.invisibilityOfAllElements(driver.findElement(By.xpath(""))));
		
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
}
