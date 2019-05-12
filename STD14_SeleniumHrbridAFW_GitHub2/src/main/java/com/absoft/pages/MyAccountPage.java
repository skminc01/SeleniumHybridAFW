package com.absoft.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MyAccountPage extends PageBase {
	
	// ################ NOTES  ################
	// Generally, we need to find web elements and perform required actions on them as per multiple test cases to test a web page or feature
	// Examples: 1) MyAccountPage => Test login by entering only user name
	// 			 2) MyAccountPage => Test login without user name or password
	// We don’t want our web element => find strategy to be scattered across different methods in a page class
	// We need one single web element instance with find strategy to represent the web element and using that we can perform multiple actons as per differetnt test cases
	// That’s why we have Page Factory
	
	@FindBy(id="login")
	WebElement login;
	
	@FindBy(id="username")
	WebElement username;
	
	@FindBy(id="password")
	WebElement password;
	
	@FindBy(id="user_info")
	WebElement userInfo;
	
	public MyAccountPage(WebDriver driver)
	{
		super(driver);
	}

	public MyAccountPage loginAs(String username, String password)
	{
		logger.debug("Parameters revceived: " + username + "," + password);
		
		this.username.sendKeys(username);
		this.password.sendKeys(password);
		this.login.click();
		
		return new MyAccountPage(driver);
	}
	
	public MyAccountPage tryLoginWithUsernameOnly(String username)
	{
		this.username.sendKeys(username);
		this.login.click();
		
		return new MyAccountPage(driver);
	}
	
	public MyAccountPage tryLoginWithNoInput()
	{
		this.login.click();
		
		return new MyAccountPage(driver);
	}
	
	public boolean isLoginSuccessfulFor(String username)
	{
		return this.userInfo.getText().contains(username);
	}

}
