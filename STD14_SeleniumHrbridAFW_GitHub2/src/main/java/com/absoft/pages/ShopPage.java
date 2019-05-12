package com.absoft.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class ShopPage extends PageBase {
	
	public ShopPage(WebDriver driver)
	{
		super(driver);
	}

	public ShopPage setSortOrder(String sortOrder)
	{
		Select sortList = new Select(driver.findElement(By.name("orderby")));
		sortList.selectByVisibleText(sortOrder);
		return new ShopPage(driver);
	}
	
	public String getSortOrder()
	{
		Select sortList = new Select(driver.findElement(By.name("orderby")));
		
		 return sortList.getFirstSelectedOption().getText();
	}

}
