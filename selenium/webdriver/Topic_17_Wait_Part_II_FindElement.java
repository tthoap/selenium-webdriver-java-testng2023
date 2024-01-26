package webdriver;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
public class Topic_17_Wait_Part_II_FindElement {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
//	WebDriverWait explicitwait;
//edit
	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Windows")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		}

		driver = new FirefoxDriver();
		//explicitwait = new WebDriverWait(driver, 20);
		driver.get("http://live.techpanda.org/");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
	}

	@Test
	public void TC_01_Find_Element() {
		//Case 1: Tìm thấy 1 element
		System.out.println("case1");
		WebElement link = driver.findElement(By.xpath("//span[text()='Subscribe']"));
		System.out.println(link.getText());
		
		//Case 2: Không tìm thấy element
		System.out.println("case2");

		//WebElement noLink = driver.findElement(By.xpath("//span[text()='Hoa']"));
		
		//Case 3: Thấy nhiều element
		System.out.println("case3");
		link = driver.findElement(By.xpath("//a[text()='My Account']"));
		//link.click();
		System.out.println(link.getText());
		
	}
	
	@Test
	public void TC_02_Find_Elements() {
		//Case 1: Tìm thấy 1 element
		System.out.println("case1");
		List<WebElement> links = driver.findElements(By.xpath("//span[text()='Subscribe']"));
		System.out.println(links.get(0).getText());
		System.out.println("Size of links: \n" + links.size());
		
		//Case 2: Không tìm thấy element
		System.out.println("case2");
		List<WebElement> noLinks = driver.findElements(By.xpath("//span[text()='Hoa']"));
		//System.out.println(noLinks.get(0).getText());
		System.out.println("Size of noLink is: " + noLinks.size());
		
		//Case 3: Tìm thấy nhiều element
		System.out.println("case3");
		links = driver.findElements(By.xpath("//a[text()='My Account']"));
		System.out.println("First link text is: " + links.get(0).getText() +"\n");
		System.out.println("Second link text is: " + links.get(1).getText() +"\n");
		System.out.println("Size of links: \n" + links.size());
		
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
