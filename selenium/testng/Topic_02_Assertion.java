package testng;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

public class Topic_02_Assertion {

	@Test //method
	public void TC_01() {
		// Assert True: mong đợi 1 điều kiện trả về là đúng
					//Tham số nhận vào là boolean
				
				//Assert False:  mong đợi 1 điều kiện trả về là sai
				//Hàm có kiểu dữ liệu trả về là boolean
		//Selenium: isDisplayed, isEnabled, isSelected, isMultiple (BuiltIn)
		//Custom: hàm tự viết có kiểu dữ liệu trả về là boolean
		boolean status = 3>5;
		System.out.println(status);
		//Assert.assertTrue(status);
		Assert.assertFalse(status);
		
		//AssertEqual: mong đợi 2 điều kiện như nhau
		//dùng với các kiểu dữ liệu còn lại: Number/String/List/array/collection
		//Selenium: getText/getTagnam/getAttribute/geturl/getTitle/size (BuiltIn)
	}

	

}
