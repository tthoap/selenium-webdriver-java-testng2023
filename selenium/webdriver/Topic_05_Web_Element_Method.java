package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_05_Web_Element_Method {
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

		//Mở ra trình duyệt
		driver = new FirefoxDriver();
		//set timeout to find element
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		//Launch the app
		driver.get("https://demo.nopcommerce.com/");
	}
	
	@Test
	public void TC_01_Web_Element() {
		//Muốn thao tác với element thì phải tìm element trước
		//tìm 1 element
		driver.findElement(By.id(""));
		
		//Tìm nhiều element
		driver.findElements(By.id(""));
		
		//Nếu chỉ thao tác 1 lần vs element thì ko cần khai báo biến
		driver.findElement(By.id("small-searchterms")).sendKeys("Apple");
		
		//Nếu cần thao tác với element nhiều lần thì nên khái báo biến
		
		WebElement searchTextBox = driver.findElement(By.id("small-searchterms"));
		searchTextBox.clear();
		searchTextBox.sendKeys("Nokia");
		searchTextBox.getAttribute("value");
		
		//Đếm xem có bao nhiều element thỏa mãn  điều kiện
		//Verify số lượng element trả về như mong đợi
		//Thao tác với all loại element giống nhau trong 1 page
		List<WebElement> checkboxes = driver.findElements(By.xpath("//div[@class='inputs']/input[not(@type='checkbox')]"));
		
		//Verify có đúng 6 textbox lại form đăng ký
		Assert.assertEquals(checkboxes.size(), 6);
		
		WebElement singleElement = driver.findElement(By.className(""));
		
		//Textbox/ TextArea/Editable dropdown
		//để dữ liệu được toàn vẹn
		singleElement.clear();
		singleElement.sendKeys("");
		
		//Button/ Link/ Radio/Checkbox/ Custom Dropdown/..
		singleElement.click();
		
		//Các hàm có tiền tố bắt đầu bằng get luôn trả về dữ liệu
		//getTitle/ getText/ getElement/ getPgeSource/ getAttribute/ getCssValue/getText
		
		singleElement = driver.findElement(By.xpath("//input[@='small-searchterms']"));
		singleElement.getAttribute("placeholder");
		
		//Lấy ra thuộc tính của Css, để test UI
		singleElement = driver.findElement(By.cssSelector(".search-box-button"));
		singleElement.getCssValue("background-color");
		
		singleElement.getLocation(); 
		
		//Chụp hình lỗi, đưa vào HTML report
		singleElement.getScreenshotAs(OutputType.FILE);
		
		//khi find element by Id, Name, Class, Css
		//Từ 1 element ko bít tag Name, lấy ra TagName để truyền vào cho 1 locator khác
		singleElement = driver.findElement(By.cssSelector(".search-box-button"));
		String searchButtonTagName = singleElement.getTagName();
		searchTextBox = driver.findElement(By.xpath("//" + searchButtonTagName + "[@id='small-searchterms']"));
		
		//Lấy ra text của element (Header. Link/ Message/ ..)
		singleElement.getText();
		
		//Các hàm có tiền tố iXX thì trả về kiểu boolean(100%)
		singleElement.isDisplayed();
		singleElement.isEnabled();
		singleElement.isSelected();
		
		singleElement.submit();
		
		singleElement = driver.findElement(By.cssSelector(".search-box-button"));
		singleElement.submit();
			
	}

}
