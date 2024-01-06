package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_05_Web_Browser_Method {
	//Interface
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

		//Init browser
		driver = new FirefoxDriver();
		//set timeout to find element
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	@Test
	public void TC_01_Browser() {
		// mở 1 page(URL) ra **
		driver.get("https://www.facebook.com/");
		
		//lấy ra đường dẫn URL của page hiện tại**
		String localPageURL = driver.getCurrentUrl();
		
		//lấy ra tite của page hiện tại**
		driver.getTitle();
		
		//lấy ra HTML của page hiện tại
		driver.getPageSource();
		
		//Xử lí tab/window*
		driver.getWindowHandle();
		driver.getWindowHandles();
		
		//Sẽ học trong phần Framework trong (Share class state)
		//driver.manage().addCookie(cookie);
		
		//chờ cho element được tìm thấy trong vòng xx thời gian**
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		//back về page trước đó
		driver.navigate().back();
		//forward tới page trước đó
		driver.navigate().forward();
		
		//refresh page hiện tại
		driver.navigate().refresh();
		
		//mở ta 1 URL 
		driver.navigate().to("https://www.facebook.com/");
		
		//Khi có duy nhất 1 tab thì quit và close giống nhau, đều đóng browser**
		driver.quit();
		
		//Xử lí window khi switch tab/window
		driver.close();
		//khi có 2 tab thì close sẽ chỉ close tab hiện tại

		//window /tab**
		//Alert**
		//Frame/IFrame**
		driver.switchTo().alert();
		driver.switchTo().frame(1);
		driver.switchTo().window("");
		
		driver.manage().window().fullscreen();
		
		driver.manage().window().maximize();//**
		driver.manage().window().getPosition();
		//driver.manage().window().setPosition(null);
		
		driver.manage().window().getSize();
		//driver.manage().window().setPosition();
		
	
	}
	

}
