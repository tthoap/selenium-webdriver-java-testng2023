package testng;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;

public class Topic_04_Priority_Skip_Description {

	

	@Test(priority = 5, description= "JIRA#1234 - Create new user verify success")
	public void User_01_Create_New_User() {
		System.out.println("Run TC 01");
	}
	
	@Test(priority = 2 )
	public void User__View_User() {
		System.out.println("Run TC 02");
	}
	
	@Test(priority = 3, enabled = false)
	public void User__Edit_User() {
		System.out.println("Run TC 03");
	}
	
	@Test(priority = 4)
	public void User__Move_User() {
		System.out.println("Run TC 04");
	}
	
	@Test(priority = 1)
	public void User_05_Delete_User() {
		System.out.println("Run TC 05");
	}

	

}
