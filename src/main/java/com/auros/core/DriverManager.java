package com.auros.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverManager {
	public WebDriver driver;
	public static Properties props;

	public DriverManager() {

	}

	/**
	 * initilizeBrowser WILL TRY TO SETUP BROWSER library configuration
	 * automatically
	 */
	public WebDriver initilizeBrowser() {
		basePropertiesloader();
		String nameOfTheBrowser = props.getProperty("browser");
		String browserMode = props.getProperty("headlessMode");
		if (nameOfTheBrowser.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			if (browserMode.equalsIgnoreCase("true")) {
				options.addArguments("--headless");
				options.addArguments("window-size=1920,1080");
			}
			options.addArguments("chrome.switches", "--disable-extensions");
			driver = new ChromeDriver(options);
			driver.manage().window().maximize();
		} 
		
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Long.parseLong(props.getProperty("implicitly_wait")),
				TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(Long.parseLong(props.getProperty("page_load_time_out")),
				TimeUnit.SECONDS);
		driver.get(props.getProperty("base_url"));
		return driver;
	}

	public void closeBrowser() {
		driver.quit();
	}

	public static void basePropertiesloader() {
		FileInputStream fis;
		try {
			fis = new FileInputStream(new File(System.getProperty("user.dir") + "\\src\\test\\resources\\AurosProperties.properties"));
			props = new Properties();
			props.load(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void getShot(WebDriver driver) {
		/*
		 * maybe StackTraceElement e = stacktrace[3] this number needs to be corrected
		 * (current method->parent method->grandParent Method name it will give you
		 */
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
		StackTraceElement e = stacktrace[2];
		String methodName = e.getMethodName();
	//	System.out.println("getShot methodName " + methodName);
		TakesScreenshot scrShot = ((TakesScreenshot) driver);
		File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY_MM_dd_HHmmss");
		String patternType = dateFormat.format(date);
		String location = System.getProperty("user.dir") + "/target/" + methodName;
		//System.out.println("screenshots Locations used ================> " + location);
		File DestFile = new File(location + "/" + patternType + ".png");
		try {
			FileUtils.copyFile(SrcFile, DestFile);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
