package com.absoft.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.absoft.util.DataDrivenManager;


public class ShopPageTest extends TestBase {
	
	//@Test
	public void testPageTitle()
	{
		
		String actualTitle = homePG.clickShopLink().getTitle();
		
		Assert.assertEquals(actualTitle, 
				"Products | ABSoft Trainings – E-Commerce test web site",
				"ERROR: Title is wrong");
	}

	  @Test(dataProvider="dataProvider")
	  public void testApplyingSortOrder(String sortOrder)
	  {
		  
		  String actualSortOrder =  homePG.clickShopLink()
							  			.setSortOrder(sortOrder)
							  			.getSortOrder();
		  
		  Assert.assertTrue(actualSortOrder.equals(sortOrder), 
				  				"ERROR: Sort order is not applied properly");
	  }
	  
	  
	 
	  
}
