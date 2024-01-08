	package webdriver;
	
	import java.io.IOException;
	import java.util.Random;
	import java.util.concurrent.TimeUnit;
	
	import org.openqa.selenium.Alert;
	import org.openqa.selenium.By;
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.firefox.FirefoxDriver;
	import org.openqa.selenium.support.ui.ExpectedConditions;
	import org.openqa.selenium.support.ui.WebDriverWait;
	import org.testng.Assert;
	import org.testng.annotations.AfterClass;
	import org.testng.annotations.BeforeClass;
	import org.testng.annotations.Test;
	public class Topic_10_Alert {
		WebDriver driver;
		Alert alert;
		WebDriverWait explicitWait; 
		String projectPath = System.getProperty("user.dir");
		String osName = System.getProperty("os.name");
		String authenFirefoxAutoIT = projectPath + "\\AutoIT\\authen_firefox.exe";
		String authenChromeAutoIT = projectPath + "\\AutoIT\\authen_chrome.exe";
	//edit
		@BeforeClass
		public void beforeClass() {
			if (osName.contains("Windows")) {
				System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
			} else {
				System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
			}
	
			driver = new FirefoxDriver();
			explicitWait = new WebDriverWait(driver, 15);
			
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			//driver.manage().window().maximize();
		}
	
		//@Test
		public void TC_01_Accept_Alert() {
			driver.get("https://automationfc.github.io/basic-form/index.html");
			
			driver.findElement(By.xpath("//button[text()='Click for JS Alert']")).click();
			
			//Wait until alert present
			alert = explicitWait.until(ExpectedConditions.alertIsPresent());
			//alert = driver.switchTo().alert();
			System.out.println(alert.getText());
			Assert.assertEquals(alert.getText(), "I am a JS Alert");
			alert.accept();
			Assert.assertEquals(driver.findElement(By.cssSelector("#result")).getText(), "You clicked an alert successfully");
			
		}
		//@Test
		public void TC_02_Confirm_Alert() {
			driver.get("https://automationfc.github.io/basic-form/index.html");
			
			driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();
			
			//Wait until alert present
			alert = explicitWait.until(ExpectedConditions.alertIsPresent());
			//alert = driver.switchTo().alert();
			System.out.println(alert.getText());
			Assert.assertEquals(alert.getText(), "I am a JS Confirm");
			alert.accept();
			Assert.assertEquals(driver.findElement(By.cssSelector("#result")).getText(), "You clicked: Ok");
			
			//Cancel alert------------------------
			driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();
			
			//Wait until alert present
			alert = explicitWait.until(ExpectedConditions.alertIsPresent());
			//alert = driver.switchTo().alert();
			System.out.println(alert.getText());
			Assert.assertEquals(alert.getText(), "I am a JS Confirm");
			alert.dismiss();
			Assert.assertEquals(driver.findElement(By.cssSelector("#result")).getText(), "You clicked: Cancel");
			
		}
		//@Test
		public void TC_03_Promtp_Alert() {
			driver.get("https://automationfc.github.io/basic-form/index.html");
			
			driver.findElement(By.xpath("//button[text()='Click for JS Prompt']")).click();
			
			//Wait until alert present
			alert = explicitWait.until(ExpectedConditions.alertIsPresent());
			//alert = driver.switchTo().alert();
			System.out.println(alert.getText());
			Assert.assertEquals(alert.getText(), "I am a JS prompt");
			String addressName = "ho chi minh";
			alert.sendKeys(addressName);
			sleepInSecond(4);
			alert.accept();
			Assert.assertEquals(driver.findElement(By.cssSelector("#result")).getText(), "You entered: " + addressName);
			
			//cancel alert after enter value
			driver.findElement(By.xpath("//button[text()='Click for JS Prompt']")).click();
			
			//Wait until alert present
			alert = explicitWait.until(ExpectedConditions.alertIsPresent());
			//alert = driver.switchTo().alert();
			System.out.println(alert.getText());
			Assert.assertEquals(alert.getText(), "I am a JS prompt");
			alert.sendKeys(addressName);
			sleepInSecond(4);
			alert.dismiss();
			Assert.assertEquals(driver.findElement(By.cssSelector("#result")).getText(), "You entered: null");
			
			
		}
	
		//@Test
		public void TC_04_Authentication_Alert() {
			String username = "admin";
			String password = "admin";
			String url = "http://" + username + ":" + password + "@" + "the-internet.herokuapp.com/basic_auth";
			driver.get(url);
			Assert.
			assertTrue(driver.findElement(By.xpath("//p[contains(text(),\"Congratulations! You must have the proper credentials.\")]")).isDisplayed());
		}
		
		
		@Test
		public void TC_05_Authentication_Alert() {
			String username = "admin";
			String password = "admin";
			driver.get("https://the-internet.herokuapp.com/");
			
			String basicAuthenLink = driver.findElement(By.xpath("//a[text()='Basic Auth']")).getAttribute("href");
			driver.get(getLinkByUserPass(basicAuthenLink, username, password));
			Assert.assertTrue(driver.findElement(By.xpath("//p[contains(text(),\"Congratulations! You must have the proper credentials.\")]")).isDisplayed());
		
		}
		
		
		public String getLinkByUserPass(String link, String user, String pass) {
			//https://the-internet.herokuapp.com/basic_auth
			String[] links = link.split("//");
			//link[0] = https:
			//link[1] = the-internet.herokuapp.com/basic_auth
			
			return link = links[0] +"//" + user + ":" + pass +"@" + links[1];
		}
		//@Test
		public void TC_06_Authentication_Alert_AutoIT() throws IOException {
			String username = "admin";
			String password = "admin";
			String url = "http://the-internet.herokuapp.com/basic_auth";
			sleepInSecond(3);
			if(driver.toString().contains("chorme")) {
				Runtime.getRuntime().exec(new String[] {authenChromeAutoIT, username, password});
			} else {
				Runtime.getRuntime().exec(new String[] {authenFirefoxAutoIT, username, password});
				
			}
			sleepInSecond(3);
			driver.get(url);
			Assert.assertTrue(driver.findElement(By.xpath("//p[contains(text(),\"Congratulations! You must have the proper credentials.\")]")).isDisplayed());
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
