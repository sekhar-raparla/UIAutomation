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

public class VerifySearchByACIDTest extends DriverManager {

	protected WebDriver driver;
	DriverManager drivermanager = new DriverManager();

	@BeforeTest
	public void preConditions() {
		// DriverManager drivermanager = new DriverManager();
		driver = drivermanager.initilizeBrowser();
	}

	@Test
	public void VerifySearchByACID() throws InterruptedException {
		try {
			UtilFunctions uFunctions = new UtilFunctions();
			uFunctions.userLogin(driver);
			uFunctions.copSelection(driver);
			UserWaits.staticWait(2);
			
			driver.findElement(By.xpath("//a[@data-title='Assessments']")).click();
			Util.switchToFrameByUsingWebElement(driver, driver.findElement(By.xpath("//iframe[@id='activities']")));
			driver.findElement(By.xpath("//span[contains(text(),'Search By')]")).click();
			driver.findElement(By.xpath("//span[@class='level1'][normalize-space()='ID']")).click();
			driver.switchTo().defaultContent();

			Util.switchToFrameByUsingWebElement(driver,
					driver.findElement(By.xpath("//iframe[contains(@src,'/ac/searchById?')]")));
			driver.findElement(By.xpath("//input[@id='acId']")).sendKeys(props.getProperty("expectedac"));
			driver.findElement(By.xpath("//input[@value='View Assessment']")).click();
			UserWaits.staticWait(2);
			uFunctions.switchToChildWindow(driver);
			UserWaits.staticWait(1);
			String expectedACMessage = driver.findElement(By.xpath("//p[normalize-space()='Assessment"+" "+props.getProperty("expectedac")+" "+"does not exist']")).getText();

			System.out.println(expectedACMessage);
			if (expectedACMessage.equalsIgnoreCase("Assessment"+" "+props.getProperty("expectedac")+" "+"does not exist")) {
				getShot(driver);
				Assert.assertTrue(false, "The searched AC is not exist. Throwing a message");
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
