package com.absoft.tests;
//com.absoft.tests.MyAccountPageTest
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.absoft.pages.HomePage;
import com.absoft.util.DataDrivenManager;

public class MyAccountPageTest extends TestBase {
	
	// ################ NOTES  ################
	// Data Driven Framework or Data Driven Testing refers to running the same test multiple times with different input datasets.
	// These datasets are often stored in external sources like excel file, xml file, text file, and database.
	// The Data Driven Framework provides mainly following benifits. It reduces maintenance and improves test coverage :
	// 	1) Repeat the test script for different test datasets
	// 	2) Reuse the test code
	
	@Test
	public void testPageTitle()
	{
		String actualTitle =  homePG.clickMyAccountLink()
									.getTitle();
		
		String expectedTitle = "My Account | ABSoft Trainings – E-Commerce test web site";
		
		Assert.assertEquals(actualTitle, expectedTitle, "ERROR: Page Title is NOT Correct");
	}
	
	@Test(dataProvider = "dataProvider" )
	public void testSuccessfulLogin(String username, String password)
	{
		logger.info("Verifying the login for account: " + username + "," + password);
		
		
		boolean isLoginSuccessful = homePG
											.clickMyAccountLink()
											.loginAs(username, password)
											.isLoginSuccessfulFor(username);
		erTest.info("S1 - Try login for account: " + username + "," + password);
		erTest.info("S2 - This is test step");
		// 1) Customer 2) Store Admin 3) Store Manager 4) Store Editor
		
		// ################ Implementing Data Driven Framework ################
		// 1) Create test data sets in Excel File
		// 2) Get the test data in our test case from Excel File
		// 3) Apply the test data to the test case
		// 				a) Correct number of parameters
		// 				b) Correct type of parameters
		// 				c) Applying at correct place
		// 4) Run the test case and verify Data Driven Framework

		
		Assert.assertTrue(isLoginSuccessful, "ERROR: Login is NOT Successful");
		
	}
	
}
