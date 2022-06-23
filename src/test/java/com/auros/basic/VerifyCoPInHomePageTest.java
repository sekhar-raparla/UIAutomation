package com.auros.basic;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.auros.core.DriverManager;
import com.auros.utils.Util;

public class VerifyCoPInHomePageTest extends DriverManager {

	protected WebDriver driver;
	DriverManager drivermanager = new DriverManager();

	@BeforeTest
	public void preConditions() {
		// DriverManager drivermanager = new DriverManager();
		driver = drivermanager.initilizeBrowser();
	}

	@Test
	public void VerifyCoPInHomePage() {
		try {
			UtilFunctions uFunctions = new UtilFunctions();
			uFunctions.userLogin(driver);
			uFunctions.copSelection(driver);

			Util.switchToFrameByUsingWebElement(driver,
					driver.findElement(By.xpath("//iframe[contains(@src,'/kpac/kpacById?')]")));
			String actualCoP = driver.findElement(By.xpath("//span[@class='cop-name']")).getText();

			if (!actualCoP.equalsIgnoreCase(props.getProperty("expectedcop"))) {
				Assert.assertTrue(false, "Actual CoP is not matching with the expected CoP");
			}

		getShot(driver);
		} finally {
			driver.quit();
		}

	}

	@AfterTest
	public void closeBrowser() {
		drivermanager.closeBrowser();
	}

}
