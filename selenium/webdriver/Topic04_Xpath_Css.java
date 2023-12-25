package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic04_Xpath_Css {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");

	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Windows")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		}

		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get("https://alada.vn/tai-khoan/dang-ky.html");
	}

	@Test
	public void TC_01_Reg_with_empty_data() {
		// step1 access to the link
		driver.get("https://alada.vn/tai-khoan/dang-ky.html");
		// click to Button Dang Ky
		driver.findElement(By.xpath("//button[@class='btn_pink_sm fs16']")).click();
		Assert.assertTrue(driver.findElement(By.id("txtFirstname-error")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.id("txtEmail-error")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.id("txtCEmail-error")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.id("txtPassword-error")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.id("txtCPassword-error")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.id("txtPhone-error")).isDisplayed());

	}

	@Test
	public void TC_02_Reg_with_invalid_email() throws InterruptedException {
		driver.navigate().refresh();
		driver.findElement(By.name("txtFirstname")).sendKeys("Hoa tran");
		driver.findElement(By.name("txtEmail")).sendKeys("Hoa@tran@1");
		driver.findElement(By.name("txtCEmail")).sendKeys("Hoa@tran@1");
		driver.findElement(By.name("txtPassword")).sendKeys("12345678");
		driver.findElement(By.name("txtCPassword")).sendKeys("12345678");
		driver.findElement(By.name("txtPhone")).sendKeys("0971809071");

		// click to Button Dang Ky
		driver.findElement(By.xpath("//button[@class='btn_pink_sm fs16']")).click();
		// Verify by get error msg
		Assert.assertEquals(driver.findElement(By.xpath("//label[@id='txtEmail-error']")).getText(),
				"Vui lòng nhập email hợp lệ");
		Assert.assertEquals(driver.findElement(By.xpath("//label[@id='txtCEmail-error']")).getText(),
				"Email nhập lại không đúng");
		Thread.sleep(300);
	}

	@Test
	public void TC_03_Reg_with_incorrect_confirm_Email() {
		driver.navigate().refresh();
		driver.findElement(By.name("txtFirstname")).sendKeys("Hoa tran");
		driver.findElement(By.name("txtEmail")).sendKeys("Hoa@gmail.com");
		driver.findElement(By.name("txtCEmail")).sendKeys("Hoatran@1");
		driver.findElement(By.name("txtPassword")).sendKeys("12345678");
		driver.findElement(By.name("txtCPassword")).sendKeys("12345678");
		driver.findElement(By.name("txtPhone")).sendKeys("0971809071");
		// click to Button Dang Ky
		driver.findElement(By.xpath("//button[@class='btn_pink_sm fs16']")).click();
		// Verify by get error msg
		Assert.assertEquals(driver.findElement(By.xpath("//label[@id='txtCEmail-error']")).getText(),
				"Email nhập lại không đúng");
	}

	@Test
	public void TC_04_Reg_with_Invalid_Password() {
		driver.navigate().refresh();
		driver.findElement(By.name("txtFirstname")).sendKeys("Hoa tran");
		driver.findElement(By.name("txtEmail")).sendKeys("Hoa@gmail.com");
		driver.findElement(By.name("txtCEmail")).sendKeys("Hoa@gmail.com");
		driver.findElement(By.name("txtPassword")).sendKeys("1234");
		driver.findElement(By.name("txtCPassword")).sendKeys("1234");
		driver.findElement(By.name("txtPhone")).sendKeys("0971809071");
		Assert.assertEquals(driver.findElement(By.xpath("//label[@id='txtPassword-error']")).getText(),
				"Mật khẩu phải có ít nhất 6 ký tự");
		Assert.assertEquals(driver.findElement(By.xpath("//label[@id='txtCPassword-error']")).getText(),
				"Mật khẩu phải có ít nhất 6 ký tự");

	}

	@Test
	public void TC_05_Reg_with_incorrect_confirmPW() {
		driver.navigate().refresh();
		driver.findElement(By.name("txtFirstname")).sendKeys("Hoa tran");
		driver.findElement(By.name("txtEmail")).sendKeys("Hoa@gmail.com");
		driver.findElement(By.name("txtCEmail")).sendKeys("Hoa@gmail.com");
		driver.findElement(By.name("txtPassword")).sendKeys("123456");
		driver.findElement(By.name("txtCPassword")).sendKeys("123498");
		driver.findElement(By.name("txtPhone")).sendKeys("0971809071");
		Assert.assertEquals(driver.findElement(By.xpath("//label[@id='txtCPassword-error']")).getText(),
				"Mật khẩu bạn nhập không khớp");
	}

	@Test
	public void TC_06_Reg_with_invaid_Phonenumber() throws InterruptedException {
		driver.navigate().refresh();
		driver.findElement(By.name("txtFirstname")).sendKeys("Hoa tran");
		driver.findElement(By.name("txtEmail")).sendKeys("Hoa@gmail.com");
		driver.findElement(By.name("txtCEmail")).sendKeys("Hoa@gmail.com");
		driver.findElement(By.name("txtPassword")).sendKeys("123456");
		driver.findElement(By.name("txtCPassword")).sendKeys("1234");
		driver.findElement(By.name("txtPhone")).sendKeys("84971808");
		// click to Button Dang Ky
		driver.findElement(By.xpath("//button[@class='btn_pink_sm fs16']")).click();
		Assert.assertEquals(driver.findElement(By.xpath("//label[@id='txtPhone-error']")).getText(),
				"Số điện thoại bắt đầu bằng: 09 - 03 - 012 - 016 - 018 - 019 - 088 - 03 - 05 - 07 - 08");
		Thread.sleep(1000);
		driver.findElement(By.name("txtPhone")).clear();
		driver.findElement(By.name("txtPhone")).sendKeys("09718090");
		// click to Button Dang Ky
		driver.findElement(By.xpath("//button[@class='btn_pink_sm fs16']")).click();
		Thread.sleep(1000);
		Assert.assertEquals(driver.findElement(By.xpath("//label[@id='txtPhone-error']")).getText(),
				"Số điện thoại phải từ 10-11 số."); 

	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}
