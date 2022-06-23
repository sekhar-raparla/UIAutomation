package com.auros.utils;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.auros.core.DriverManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

public class ListenerManager extends DriverManager implements ITestListener {
	ExtentReports extentReports;
	ExtentTest test;
	private static Logger logs = LogManager.getLogger(ListenerManager.class);

	/**
	 * ThreadLocal class store all ExtentTest class objects, so by using ThreadLocal
	 * parallel execution 100% we can achieve
	 * 
	 * if we don't use ThreadLocal class all parallel test results will copied to
	 * current test object instead of individual test objects, because of that
	 * reports will merged in only one test
	 */
	ThreadLocal<ExtentTest> testThreadManager = new ThreadLocal<ExtentTest>();

	/**
	 * if any @Test started onTestStart method will call, result.getName(); will
	 * give current @Test method name
	 */
	@Override
	public void onTestStart(ITestResult result) {
		String currentTest = result.getName();
		logs.info(currentTest + " started ");
		test = extentReports.createTest(result.getMethod().getMethodName());
		testThreadManager.set(test);
	}

	/**
	 * if any @Test pass , onTestSuccess method will called for
	 * Sequential/series/one by one test case execution. method => #codeStart#
	 * test.pass("test Passed"); #codeEND# can be used, but if your framework need
	 * support parallel execution, then we must call ThreadLocal object reference
	 * with get() method like => #codeStart# testThreadManager.get().pass("test
	 * Passed");#codeEND#
	 */
	@Override
	public void onTestSuccess(ITestResult result) {
		String currentTest = result.getName();
		testThreadManager.get().pass("test Passed");
		logs.info(currentTest + " test passed ");
	}

	/**
	 * if any @Test Fail , onTestFailure method will called for
	 * Sequential/series/one by one test case execution. method => #codeStart#
	 * test.log(Status.FAIL, "test failed"); #codeEND# can be used, but if your
	 * framework need support parallel execution, then we must call ThreadLocal
	 * object reference with get() method like => #codeStart#
	 * testThreadManager.get().log(Status.FAIL, "test Failed");#codeEND#
	 */

	public void onTestFailure(ITestResult result) {
		testThreadManager.get().fail(result.getThrowable());
		String testMethodName = result.getName();
		WebDriver driver = null;
		try {
			driver = (WebDriver) result.getTestClass().getRealClass().getDeclaredField("driver")
					.get(result.getInstance());
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!(driver == null)) {
			System.out.println("from if when Driver is NOT NULL ---------------> " + driver);
			testThreadManager.get().fail(result.getThrowable(),MediaEntityBuilder.createScreenCaptureFromBase64String(getShotAsbase64(driver,testMethodName)).build());
		}

	}

	@Override
	public void onTestSkipped(ITestResult result) {
		String currentSkippedTestName = result.getMethod().getMethodName();
		testThreadManager.get().skip(currentSkippedTestName);
		testThreadManager.get().skip(result.getThrowable());
		logs.error(currentSkippedTestName + " test Skipped: please check full logs");
	}

	@Override
	public void onStart(ITestContext context) {
		extentReports = ReportsManagers.startJsonExtentReports();
		logs.info("extentReports object created ");

	}

	@Override
	public void onFinish(ITestContext context) {
		extentReports.flush();
		logs.info("extent reports created successfully");
		System.out.println(" On Finish ");
		
	}
	
	public String getShotAsbase64(WebDriver driver, String testName) {
		return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
	}

}
