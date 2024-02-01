

package testng;

import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_07_Loop {

	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	String emailAddress = "hoaauto";
	String password = "hoaauto";
	
	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Windows")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		}
		/*
		 * driver = new FirefoxDriver(); driver.manage().timeouts().implicitlyWait(30,
		 * TimeUnit.SECONDS); driver.manage().window().maximize();
		 */
	}

	@Test(invocationCount = 10)
	public void TC_01_Register_To_System() {
		//Đăng ký
		//...
		System.out.println(emailAddress + generateEmail() + "@mail.com");
		System.out.println(password);

	}

	public int generateEmail() {
		Random rand = new Random();
		return rand.nextInt(9999);
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
