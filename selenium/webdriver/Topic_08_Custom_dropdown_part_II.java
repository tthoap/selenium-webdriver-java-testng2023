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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_08_Custom_dropdown_part_II {
	WebDriver driver;
	WebDriverWait explicitWait;
	JavascriptExecutor jsExecutor;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	String[] firstMonth = { "January", "July", "October" };
	String[] secondMonth = { "January", "July", "October", "August" };

//edit
	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Windows")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		}

		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		explicitWait = new WebDriverWait(driver, 15);
		jsExecutor = (JavascriptExecutor) driver;
		// driver.manage().window().maximize();
		// driver.get("");
	}

	// @Test
	public void TC_01_Angular() {
		driver.get("https://valor-software.com/ng2-select/");
		selecItemInCustomDropdown("//tab[@heading='Single']//i[@class='caret pull-right']",
				"//a[@class='dropdown-item']/div", "Riga");
		sleepInSecond(3);
		Assert.assertEquals(driver.findElement(By.xpath(
				"//h3[text()='Select a single city']/following-sibling::ng-select//span[contains(@class,'ui-select-allow-clear')]"))
				.getText(), "Riga");

	}

	// @Test
	public void TC_02_Editable_01() {
		driver.get("https://valor-software.com/ng2-select/");
		enterAndSelectItemInCustomDropdown("//tab[@heading='Single']//i[@class='caret pull-right']",
				"//tab[@heading='Single']//input", "//a[@class='dropdown-item']/div", "Paris");
		sleepInSecond(3);
		Assert.assertEquals(driver.findElement(By.xpath(
				"//h3[text()='Select a single city']/following-sibling::ng-select//span[contains(@class,'ui-select-allow-clear')]"))
				.getText(), "Paris");

	}

	// @Test
	public void TC_03_Editable_02() {
		driver.get("https://react.semantic-ui.com/maximize/dropdown-example-search-selection/");

		enterAndTabToCustomDropdown("//input[@class='search']", "Angola");
		sleepInSecond(1);

		enterAndTabToCustomDropdown("//input[@class='search']", "Bernin");
		sleepInSecond(1);
	}

	@Test
	public void TC_04_multiple_dropdown() {
		driver.get("https://multiple-select.wenzhixin.net.cn/templates/template.html?v=189&url=basic.html");

		selectMultiItemInDropdown("(//button[@class='ms-choice'])[1]",
				"//div[@class='form-group row'][2]//div[@class='ms-drop bottom']//li//span", firstMonth);
		sleepInSecond(1);
		Assert.assertTrue(areItemSelected(firstMonth));
		
		driver.navigate().refresh();
		selectMultiItemInDropdown("(//button[@class='ms-choice'])[1]",
				"//div[@class='form-group row'][2]//div[@class='ms-drop bottom']//li//span", secondMonth);
		sleepInSecond(1);
		Assert.assertTrue(areItemSelected(secondMonth));
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
		driver.findElement(By.xpath(parentXpath)).click();
		List<WebElement> allItems = explicitWait
				.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(childXpath)));
		for (WebElement item : allItems) {
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

	public void enterAndSelectItemInCustomDropdown(String parentXpath, String textBoxXpath, String childXpath,
			String epxectedItem) {
		driver.findElement(By.xpath(parentXpath)).click();
		sleepInSecond(1);
		driver.findElement(By.xpath(textBoxXpath)).sendKeys(epxectedItem);
		sleepInSecond(1);
		List<WebElement> allItems = explicitWait
				.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(childXpath)));
		for (WebElement item : allItems) {
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

	public void enterAndTabToCustomDropdown(String textBoxXpath, String epxectedItem) {
		driver.findElement(By.xpath(textBoxXpath)).click();
		sleepInSecond(1);

		driver.findElement(By.xpath(textBoxXpath)).sendKeys(Keys.TAB);
		sleepInSecond(1);
	}

	public void selectMultiItemInDropdown(String parentXpath, String childXpath, String[] expectedValueItem) {
		// 1. click vào cai dropd-down cho nó sổ hết các giá trị ra
		driver.findElement(By.xpath(parentXpath)).click();

		// 2. Chờ cho tất cả các giá trị trong dropdown dc load ra thành công
		List<WebElement> allItems = explicitWait
				.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(childXpath)));

		// Duyệt qua hết tất cả các phần tử cho tới khi thõa mãn điều kiện
		for (WebElement childElement : allItems) {
			// ví dụ chọn "January", "July", "October"
			for (String item : expectedValueItem) {
				if (childElement.getText().equals(item)) {
					// 3. Scroll đến item cần chọn(nếu item cần chọn có thể nhìn thấy thì ko cần
					// scroll)
					jsExecutor.executeScript("arguments[0].scrollIntoView(true);", childElement);
					sleepInSecond(1);

					// 4. Click vào item cần chọn
					childElement.click();
					sleepInSecond(1);

					List<WebElement> itemSelected = driver.findElements(By.xpath("//li[@class='selected']//input"));
					System.out.println("Item selected = " + itemSelected.size());
					if (expectedValueItem.length == itemSelected.size()) {
						break;
					}
				}

			}
		}

	}

	public boolean areItemSelected(String[] months) {
		List<WebElement> itemSelected = driver.findElements(By.xpath("//li[@class='selected']//input"));
		int numberItemSelected = itemSelected.size();

		String allItemSelectedText = driver.findElement(By.xpath("(//button[@class='ms-choice']/span)[1]")).getText();
		System.out.println("Months selected are: " + allItemSelectedText);

		if (numberItemSelected <= 3 && numberItemSelected > 0) {
			boolean status = true;
			for (String item : months) {
				if (!allItemSelectedText.contains(item)) {
					status = false;
					return status;
				}
			}
			return status;
		} else if (numberItemSelected == 12) {
			System.out.println("=12");
			return driver.findElement(By.xpath("//button[@class='ms-choice']/span[text()='All selected']")).isDisplayed();
		}
		else if (numberItemSelected > 3 && numberItemSelected < 12) {
			System.out.println(">3 & <12");
			return driver.findElement(By.xpath("//button[@class='ms-choice']/span[text()='" + numberItemSelected + " of 12 selected']")).isDisplayed();
		
		}else 
			return false;

	}
}
