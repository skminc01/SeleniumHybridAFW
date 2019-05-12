package com.absoft.util;

// DataDrivenManager class to get the test data sets from excel file
// This class inherits from ExcelManager class to reuse all code in ExcelManager class
public class DataDrivenManager extends ExcelManager
{
	// Specify test name column
	// We are using a variable for test name column so that if column changes in future, we just need to change this variable only
	public final static int testNameColumn =1;
	
	// Specify test data start column
	// We are using a variable for test data start column so that if column changes in future, we just need to change this variable only
	public final static int testDataStartColumn=2;

	// DataDrivenManager constructor which takes Excel file path and then passes to super class ExcelManager 
	public DataDrivenManager(String filePath) {
		super(filePath);
	}
	
	// Returns 2 dimensional object array representing test data sets when sheet and test names provided
	public Object[][] getTestCaseDataSets(String sheetName, String testName)
	{
		// Get test case row number and test data start row number
		int testRowNumber = getRowNumber(sheetName, testNameColumn, testName);
		int testDataStartRow = testRowNumber +1;
		
		// Calculate test data row count
		int testDataRows = 0;
		for(int i=testDataStartRow; getCellData(sheetName, testNameColumn, i).equals(testName); i++ )
		{
			testDataRows++;
		}
		
		// Calculate test data column count
		int testDataCols = getCellCount(sheetName, testRowNumber) - testDataStartColumn+1;
		
		// Define 2 dimensional object array to hold test data sets
		Object[][] testCaseDataSets = new Object[testDataRows][testDataCols];
		
		// Read test data cells from Excel file and assign into Object[][] testCaseDataSets
		for(int i=0;i<testDataRows;i++)
		{
			for(int j=0;j<testDataCols;j++)
			{
				testCaseDataSets[i][j] = getCellData(sheetName, testDataStartColumn+j, testDataStartRow+i);
			}
		}
		
		return testCaseDataSets;
		
	}
	
	
	

}
