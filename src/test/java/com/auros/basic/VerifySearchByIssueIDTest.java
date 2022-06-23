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

public class VerifySearchByIssueIDTest extends DriverManager {

	protected WebDriver driver;
	DriverManager drivermanager = new DriverManager();

	@BeforeTest
	public void preConditions() {
		// DriverManager drivermanager = new DriverManager();
		driver = drivermanager.initilizeBrowser();
	}

	@Test
	public void VerifySearchByIssueID() throws InterruptedException {
		try {
			UtilFunctions uFunctions = new UtilFunctions();
			uFunctions.userLogin(driver);
			uFunctions.copSelection(driver);
			UserWaits.staticWait(2);
			
			driver.findElement(By.xpath("//a[@data-title='Issues']")).click();
			Util.switchToFrameByUsingWebElement(driver, driver.findElement(By.xpath("//iframe[@id='activities']")));
			driver.findElement(By.xpath("//span[contains(text(),'Search By')]")).click();
			driver.findElement(By.xpath("//span[@class='level1'][normalize-space()='ID']")).click();
			driver.switchTo().defaultContent();

			Util.switchToFrameByUsingWebElement(driver,
					driver.findElement(By.xpath("//iframe[contains(@src,'/jsp/IssueSearchByID.jsp?')]")));
			driver.findElement(By.xpath("//input[@id='issueId']")).sendKeys(props.getProperty("expectedissue"));
			driver.findElement(By.xpath("//input[@value='View Issue']")).click();
			UserWaits.staticWait(2);
			uFunctions.switchToChildWindow(driver);
			UserWaits.staticWait(1);
			String expectedIssueMessage = driver.findElement(By.xpath("//div[@class='text-center']")).getText().trim();
            System.out.println(expectedIssueMessage);
			if (expectedIssueMessage.equalsIgnoreCase("Issue"+" "+props.getProperty("expectedissue")+" "+"does not exist.")) {
				getShot(driver);
				Assert.assertTrue(false, "The searched Issue is not exist. Throwing a message");
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
