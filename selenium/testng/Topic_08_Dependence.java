

package testng;

import static org.testng.Assert.assertTrue;

import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import listener.ExtendReport;

@Listeners(ExtendReport.class)
public class Topic_08_Dependence {


	@Test
	public void TC_01_Add_Account() {
		
		System.out.println("Add account");
		assertTrue(false);
	}
	
	@Test(dependsOnMethods = "TC_01_Add_Account")
	public void TC_02_Edit_Account() {
		
		System.out.println("Edit");
	}
	
	@Test(dependsOnMethods = "TC_01_Add_Account")
	public void TC_03_View_Account() {
		
		System.out.println("View account");
	}
	
	@Test(dependsOnMethods = "TC_01_Add_Account")
	public void TC_04_Delete_Account() {
		System.out.println("Delete");
	}



}
