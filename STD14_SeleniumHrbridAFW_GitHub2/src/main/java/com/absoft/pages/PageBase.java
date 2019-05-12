package com.absoft.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class PageBase {
	
	WebDriver driver;
	Logger logger;
	
	public PageBase(WebDriver driver)
	{
		logger = Logger.getLogger(this.getClass().getSimpleName());
		
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public String getTitle()
	{
		return driver.getTitle();
	}

}
