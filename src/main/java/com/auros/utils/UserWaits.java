package com.auros.utils;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UserWaits {

	public static void waitForElementToBeClickable(WebDriver driver, WebElement element, int waitPeriodInSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, waitPeriodInSeconds);
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

/*	public static void waitForElementToBeClickableApproachTwo(WebDriver driver, WebElement element,
			int waitPeriodInSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, waitPeriodInSeconds);
		wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState")
				.equals("complete"));
	}*/

	public static void waitForVisibilityOfAllElements(WebDriver driver, WebElement element, int waitPeriodInSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, waitPeriodInSeconds);
		wait.until(ExpectedConditions.visibilityOfAllElements(element));
	}
	
	
	public static void staticWait(long waitPeriodInSeconds) {
		try {
			Thread.sleep(waitPeriodInSeconds*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void waitForElementToBeClickableUsingWebElementAndOnceAvailableClickOnIt(WebDriver driver,
			WebElement webElement, int waitPeriodInSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, waitPeriodInSeconds);
		wait.until(ExpectedConditions.elementToBeClickable(webElement));
		webElement.click();
	}

	public static void waitForElementToVisibil(WebDriver driver, WebElement webElement, int waitPeriodInSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, waitPeriodInSeconds);
		wait.until(ExpectedConditions.visibilityOf(webElement));
	}

	public static void waitForalertToPresent(WebDriver driver, int waitInSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, waitInSeconds);
		wait.until(ExpectedConditions.alertIsPresent());
	}
	
	public static void waitForFrametoLoad(WebDriver driver, By locator, int waitInSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, waitInSeconds);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
	}

	public static void WaitForvisibilityOfAllElementsLocated(WebDriver driver, By locator, int waitPeriodInSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, waitPeriodInSeconds);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}

	public static void WaitForvisibilityOfElementsLocated(WebDriver driver, By locator, int waitPeriodInSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, waitPeriodInSeconds);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public static void waitForLoadingIconToDisapperApproachTwo(WebDriver driver, int waitPeriodInSeconds) {
		long start = System.currentTimeMillis();
		List<WebElement> loadingIcon = driver.findElements(By.cssSelector("div.pageloading-mask"));
		int count = 0;
		while (loadingIcon.size()!=0 && count < waitPeriodInSeconds) {
			try {
				Thread.sleep(1000);
				count++;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		long end = System.currentTimeMillis();
		long elapsedTime = end - start;
		long seconds = (elapsedTime / 1000) % 60;
		System.out.println("in seconds -> " + seconds);
	}

}
