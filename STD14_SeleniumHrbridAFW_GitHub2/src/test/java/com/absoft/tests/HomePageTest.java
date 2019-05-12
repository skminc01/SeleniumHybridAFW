package com.absoft.tests;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.absoft.pages.HomePage;

public class HomePageTest extends TestBase {
	
	
	@Test(priority=1)
	public void testPageTitle()
	{
		String actualTitle =  homePG
									.getTitle();

		String expectedTitle = "ABSoft Trainings – E-Commerce test web site | Home Page";
		
		Assert.assertEquals(actualTitle, expectedTitle, "ERROR: Page Title is NOT Correct");
	}
	
	@Test
	public void testHomePageLink1()
	{
		
	}

}
