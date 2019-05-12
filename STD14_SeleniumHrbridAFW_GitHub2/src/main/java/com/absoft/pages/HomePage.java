package com.absoft.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends PageBase {
	
	@FindBy(linkText="My Account")
	WebElement myAccountLink;
	
	@FindBy(linkText = "Shop")
	WebElement shopLink;
	
	public HomePage(WebDriver driver)
	{
		super(driver);
	}
	
	public MyAccountPage clickMyAccountLink()
	{
		this.myAccountLink.click();
		return new MyAccountPage(driver);
	}
	
	public ShopPage clickShopLink()
	{
		this.shopLink.click();
		return new ShopPage(driver);
	}

}
