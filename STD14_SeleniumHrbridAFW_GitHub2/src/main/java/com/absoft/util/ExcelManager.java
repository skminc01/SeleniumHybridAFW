package com.absoft.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

// Reusable Excel Manager Class to work with and read/write from Excel files
public class ExcelManager {
	
	// ################ NOTES  ################
	// To work with excel files, there are 2 types of class series in Apache POI API
	//		1) HSSF Classes - To work with Excel 97-2003 file formats - .xls files
	//		2) XSSF Classes - To work with Excel 2007+ file formats - .xlsx files
	// We have SS User Model interfaces which provides implementation details/contract
	// We should write our code using SS User Model and it uses correct HSSF or XSSF class series based on file type
	
	private String filePath; // File path of the Excel file
	private FileInputStream fis; // File input stream to read data based on excel file path provided
	private Workbook workbook; // Excel workbook variable to work with Excel file
	
	// ExcelManager constructor which takes file path and initialises member variables
	public ExcelManager(String filePath)
	{			
		this.filePath = filePath;
		try 
		{
			fis = new FileInputStream(filePath);
			// Create a Apache POI workbook object for the excel file path provided
			workbook = WorkbookFactory.create(fis);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}
	
	public String getFilePath()
	{
		return filePath;
	}
	
	// Get total number of rows for a given Excel sheet
	public int getRowCount(String sheetName)
	{
		try
		{
			return workbook.getSheet(sheetName).getPhysicalNumberOfRows();
		}
		catch(NullPointerException e)
		{
			return 0;
		}
	}
	
	public int getCellCount(String sheetName, int rowNumber)
	{
		try
		{
		return workbook.getSheet(sheetName).getRow(rowNumber-1).getPhysicalNumberOfCells();
		}
		catch(NullPointerException e)
		{
			return 0;
		}
	}
	
	// Get cell data when Excel sheet, row number and column number are provided
	// Return "" (blank string) when specified cell doesn't exist
	public String getCellData(String sheetName, int columnNumber, int rowNumber)
	{
		try
		{
			return workbook.getSheet(sheetName).getRow(rowNumber-1).getCell(columnNumber-1).toString();
		}
		catch(NullPointerException e)
		{
			// Catch NullPointerException exception and return blank string when specified cell doesn't exist
			return "";
		}
	}
	
	public void setCellData(String sheetName, int columnNumber, int rowNumber, String cellValue)
	{
		Cell cell;
		Row row;
		try
		{
		cell = workbook.getSheet(sheetName).getRow(rowNumber-1).createCell(columnNumber-1);
		}
		catch(NullPointerException e)
		{
			row = workbook.getSheet(sheetName).createRow(rowNumber-1);
			cell = row.createCell(columnNumber-1);
		}
		
		cell.setCellValue(cellValue);
		try 
		{
		FileOutputStream fso = new FileOutputStream(filePath);
		workbook.write(fso);
		fso.close();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	// Get row number based on cell value in specified column
	// Return -1 if there is no row with that cell value in specified column
	public int getRowNumber(String sheetName, int columnNumber, String cellValue)
	{
		Iterator<Row> itr = workbook.getSheet(sheetName).rowIterator();
		while(itr.hasNext())
		{
			Row row =itr.next();
			try{
			if (row.getCell(columnNumber-1).toString().equals(cellValue))
			{
				return (row.getRowNum()+1);
			}
			}
			catch(NullPointerException e)
			{
				continue;
			}
		}
			
		return -1;
	}
	
	// Get column number based on cell value in specified row
	// Return -1 if there is no column with that cell value in specified row
	public int getColumnNumber(String sheetName, int rowNumber, String cellValue)
	{
		try
		{
		Row row = workbook.getSheet(sheetName).getRow(rowNumber-1);
		
		Iterator<Cell> itr = row.cellIterator();
		while(itr.hasNext())
		{
			Cell cell =itr.next();
			if (cell.toString().equals(cellValue))
			{
				return (cell.getColumnIndex()+1);
			}
			
		}
		return -1;
		}
		catch(NullPointerException e)
		{
			return -1;
		}
	}
	
	
	

	
}
