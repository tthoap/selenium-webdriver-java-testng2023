package webdriver;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_08_Custom_dropdown_part_I {
	WebDriver driver;
	// Thư viện dùng để wait
	WebDriverWait explicitWait;

	// Thư viện dùng để inject 1 đoạn javascript code
	JavascriptExecutor jsExecutor;

	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");

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

		// ép kiểu ngầm định : nhỏ -> lớn
		int price = 156000;
		float size = price;

		// ép kiểu tường minh : lớn về nhỏ
		short sPrice = (short) price;
		// ép kiểu tường minh
		jsExecutor = (JavascriptExecutor) driver;
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		// driver.manage().window().maximize();

	}

	// @Test
	public void TC_01_JQeuery() {
		driver.get("https://jqueryui.com/resources/demos/selectmenu/default.html");
		selecItemInCustomDropdown("//span[@id='number-button']/span[contains(@class,'ui-selectmenu-icon')]",
				"//ul[@id='number-menu']//div", "5");
		sleepInSecond(3);
		Assert.assertTrue(driver
				.findElement(By.xpath("//span[@id='number-button']/span[@class='ui-selectmenu-text' and text()='5']"))
				.isDisplayed());

		selecItemInCustomDropdown("//span[@id='number-button']/span[contains(@class,'ui-selectmenu-icon')]",
				"//ul[@id='number-menu']//div", "15");
		sleepInSecond(3);
		Assert.assertTrue(driver
				.findElement(By.xpath("//span[@id='number-button']/span[@class='ui-selectmenu-text' and text()='15']"))
				.isDisplayed());

		selecItemInCustomDropdown("//span[@id='number-button']/span[contains(@class,'ui-selectmenu-icon')]",
				"//ul[@id='number-menu']//div", "3");
		sleepInSecond(3);
		Assert.assertTrue(driver
				.findElement(By.xpath("//span[@id='number-button']/span[@class='ui-selectmenu-text' and text()='3']"))
				.isDisplayed());

	}

	// @Test
	public void TC_02_React() {
		driver.get("https://react.semantic-ui.com/maximize/dropdown-example-selection/");
		selecItemInCustomDropdown("//i[@class='dropdown icon']", "//div[@role='option']//span", "Matt");
		sleepInSecond(2);
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='divider text' and text()='Matt']")).isDisplayed());

		selecItemInCustomDropdown("//i[@class='dropdown icon']", "//div[@role='option']//span", "Jenny Hess");
		sleepInSecond(2);
		Assert.assertTrue(
				driver.findElement(By.xpath("//div[@class='divider text' and text()='Jenny Hess']")).isDisplayed());

		selecItemInCustomDropdown("//i[@class='dropdown icon']", "//div[@role='option']//span", "Justen Kitsune");
		sleepInSecond(2);
		Assert.assertTrue(
				driver.findElement(By.xpath("//div[@class='divider text' and text()='Justen Kitsune']")).isDisplayed());

	}

	@Test
	public void TC_03_VueJS() {
		driver.get("https://mikerodham.github.io/vue-dropdowns/");
		selecItemInCustomDropdown("//span[@class='caret']", "//ul[@class='dropdown-menu']//a", "Second Option");
		sleepInSecond(3);
		Assert.assertTrue(
				driver.findElement(By.xpath("//li[@class='dropdown-toggle' and contains(text(),'Second Option')]"))
						.isDisplayed());
		selecItemInCustomDropdown("//span[@class='caret']", "//ul[@class='dropdown-menu']//a", "First Option");
		sleepInSecond(3);
		Assert.assertTrue(
				driver.findElement(By.xpath("//li[@class='dropdown-toggle' and contains(text(),'First Option')]"))
						.isDisplayed());
		selecItemInCustomDropdown("//span[@class='caret']", "//ul[@class='dropdown-menu']//a", "Third Option");
		sleepInSecond(3);
		Assert.assertTrue(
				driver.findElement(By.xpath("//li[@class='dropdown-toggle' and contains(text(),'Third Option')]"))
						.isDisplayed());

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

	public void selecItemInCustomDropdown(String parentXpath, String childXpath, String epxectedItem) {
		// - CLick vào 1 element để cho xổ hết các item trong dropdow ra => là parent
		// element
		driver.findElement(By.xpath(parentXpath)).click();

		// - chờ cho tất cả các item dc load thành công => child elements
		// - Chờ xong lấy hết all item lưu vào List element
		List<WebElement> allItems = explicitWait
				.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(childXpath)));

		// Cần phải duyệt qua từng item,
		for (WebElement item : allItems) {

			// get text của item đó ra và kiểm tra xem có item có bằng cái mong muốn ko
			if (item.getText().trim().equals(epxectedItem)) {
				if (!item.isDisplayed()) {
					System.out.println("---------scroll to element------");
					jsExecutor.executeScript("arguments[0].scrollIntoView(true);", item);
					sleepInSecond(1);
				}
				item.click();
				break;
			}
		}

	}
}
