package com.absoft.tests;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

import com.absoft.pages.HomePage;
import com.absoft.util.DataDrivenManager;
import com.absoft.util.ExtentManager;
import com.absoft.util.WebDriverManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class TestBase {
	
	WebDriver driver;
	HomePage homePG;
	// Make testConfig static so that it can be initialized only once in beforeSuite()
	// and it can be shared across all test classes/ cases
	static Properties testConfig;
	Logger logger;
	
	protected static ExtentReports extent;
	protected static ThreadLocal parentTestThread = new ThreadLocal();
	protected static ThreadLocal testThread = new ThreadLocal();
	protected ExtentTest erParentTest; // for test class
	protected ExtentTest erTest;  // for test method
	
	String testFailureScreenshotPath;
	
	@BeforeSuite
	public void beforeSuite() throws FileNotFoundException, IOException
	{
		PropertyConfigurator.configure("log4j.properties");
		
		// Get Test Config
		testConfig = new Properties();

		testConfig.load(new FileInputStream("testconfig.properties"));
		
		String extentReportFilePath = "ExtentHtmlReport.html";
		extent = ExtentManager.createInstance(extentReportFilePath );
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(extentReportFilePath);
		extent.attachReporter(htmlReporter);
	}
	
	@BeforeMethod
	public void testSetup(Method method)
	{
		logger = Logger.getLogger(this.getClass().getSimpleName());
		logger.info("################# Starting " + method.getName() + " #################");
		
		driver = WebDriverManager.createDriver(testConfig.getProperty("browser"));
		driver.get(testConfig.getProperty("baseurl"));
		
		homePG = new HomePage(driver);
	}
	
	@AfterMethod
	public void testCleanUp(ITestResult result) throws InterruptedException, IOException
	{
		// Capture screenshot when test fails
		captureTestFailureScreenshot(result);
		
		WebDriverManager.quitDriver(driver);
		
		logger.info("################# Ending " + result.getName() + " #################");
	}
	
	public void captureTestFailureScreenshot(ITestResult result) throws IOException
	{
		if (result.getStatus() == ITestResult.FAILURE)
		{
			// Gives path like TestFailureScreenshots\com.absoft.tests.HomePageTest.testPageTitle.png
			testFailureScreenshotPath = "TestFailureScreenshots/" 
													+ getClass().getName() // full class name - com.absoft.tests.HomePageTest
													+ "." 
													+ result.getName() // test method name - testPageTitle
													+ ".png";
			
			// Files, Paths classes are provided by java.nio.file package
			// Create the directory if doesn't exist
			if(Files.notExists(Paths.get("TestFailureScreenshots")))
			{
				Files.createDirectory(Paths.get("TestFailureScreenshots"));
			}
			
			// Delete the old file if exists
			Files.deleteIfExists(Paths.get(testFailureScreenshotPath));
			
			// Create new test failure screenshot file
			WebDriverManager.getScreenshot(driver, testFailureScreenshotPath);
		}
	}
	
	@DataProvider
	public Object[][] dataProvider(Method method)
	{
		DataDrivenManager ddm = new DataDrivenManager(testConfig.getProperty("testdatafile"));
		Object[][] TestData = ddm.getTestCaseDataSets(testConfig.getProperty("testdatasheet"), method.getName());
		
		return TestData;
	}
	
	@BeforeClass
    public synchronized void extentReportBeforeClass() {
		// Creating extent reports test class for every TestNG test class
		erParentTest = extent.createTest(getClass().getName());
		parentTestThread.set(erParentTest);
      }

    @BeforeMethod
    public synchronized void extentReportBeforeMethod(Method method) {
    	// In ER test class, create test node based TestNG test method
        erTest =  erParentTest.createNode(method.getName());
        testThread.set(erTest);
    }

    @AfterMethod(dependsOnMethods = "testCleanUp")
    public synchronized void extentReportAfterMethod(ITestResult result) throws IOException {
    	
        if (result.getStatus() == ITestResult.FAILURE)
        	// Fail the erTest when TestNG test is failed
            erTest.fail(result.getThrowable(), 
            						MediaEntityBuilder
            						.createScreenCaptureFromPath(testFailureScreenshotPath)
            						.build());
        else if (result.getStatus() == ITestResult.SKIP)
        	// Skip the erTest when TestNG test is skipped
            erTest.skip(result.getThrowable(),MediaEntityBuilder
												.createScreenCaptureFromPath(testFailureScreenshotPath)
												.build());
        else
        	// Pass the erTest when TestNG test is passed
           erTest.pass("Test passed");

        extent.flush();
    }

}
