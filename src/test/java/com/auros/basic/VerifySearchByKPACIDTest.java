package com.auros.basic;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.auros.core.DriverManager;
import com.auros.utils.UserWaits;
import com.auros.utils.Util;

public class VerifySearchByKPACIDTest extends DriverManager {

	protected WebDriver driver;
	DriverManager drivermanager = new DriverManager();

	@BeforeTest
	public void preConditions() {
		// DriverManager drivermanager = new DriverManager();
		driver = drivermanager.initilizeBrowser();
	}

	@Test
	public void VerifySearchByKPACID() {
		try {
			UtilFunctions uFunctions = new UtilFunctions();
			uFunctions.userLogin(driver);
			uFunctions.copSelection(driver);
			Util.switchToFrameByUsingWebElement(driver, driver.findElement(By.xpath("//iframe[@id='activities']")));
			driver.findElement(By.xpath("//span[contains(text(),'Search By')]")).click();
			driver.findElement(By.xpath("//span[@class='level1'][normalize-space()='ID']")).click();
			driver.switchTo().defaultContent();

			Util.switchToFrameByUsingWebElement(driver,
					driver.findElement(By.xpath("//iframe[contains(@src,'/kpac/kpacById?')]")));
			driver.findElement(By.xpath("//input[@name='kpac_id1']")).sendKeys(props.getProperty("expectedkpac"));
			driver.findElement(By.xpath("//input[@name='button']")).click();
			UserWaits.staticWait(1);
			driver.switchTo().defaultContent();
			String expectedGrowlMessage = driver.findElement(By.xpath("//div[@class='notify-message']")).getText();

			if (expectedGrowlMessage.equalsIgnoreCase(props.getProperty("expectedkpacgrowlmessage"))) {
			getShot(driver);
				Assert.assertTrue(false, "The searched K-PAC is not exist. Throwing a warning growl message");
			}

		} finally {
			driver.quit();
		}

	}

	@AfterTest
	public void closeBrowser() {
		drivermanager.closeBrowser();
	}

}
