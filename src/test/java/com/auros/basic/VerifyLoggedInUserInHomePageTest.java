package com.auros.basic;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.auros.core.DriverManager;

public class VerifyLoggedInUserInHomePageTest extends DriverManager {

	protected WebDriver driver;
	DriverManager drivermanager = new DriverManager();

	@BeforeTest
	public void preConditions() {
		// DriverManager drivermanager = new DriverManager();
		driver = drivermanager.initilizeBrowser();
	}

	@Test
	public void verifyLoggedInUserInHomePage() {
		try {
			UtilFunctions uFunctions = new UtilFunctions();
			uFunctions.userLogin(driver);
			uFunctions.copSelection(driver);
			String actualUser = driver.findElement(By.xpath("//div[@id='userLinkMenu']")).getText();

			if (!actualUser.equalsIgnoreCase(props.getProperty("expectedloginuser"))) {
				Assert.assertTrue(false, "Logged In user is not matching with the expected user");
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
