package com.auros.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Coordinates;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.auros.core.DriverManager;

public class Util extends DriverManager{
	public static long PAGE_LOAD_TIMEOUT = 60;
	public static long IMPLICAT_TIMEOUT = 10;
	public static Properties prop;
	public static String configFileWithLocation;

		
	public static void jsClickByWebElement(WebDriver driver, WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", element);
	}
	
	
	public static void jsSendKeys(WebDriver driver, WebElement element, String data) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;  
		jsExecutor.executeScript("arguments[0].value='"+data+"'", element);
		jsExecutor.executeScript("return arguments[0].value", element);  
	}

	public static void jsClickByLocator(WebDriver driver, By locator) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		WebElement toBeClickElement = driver.findElement(locator);
		executor.executeScript("arguments[0].click();", toBeClickElement);
	}

	
	public static void scrollToDown(WebDriver driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
	}

	public static void scrollToSpecificCoordinates(WebDriver driver, int x_coordinate, int y_coordinate) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("scrollTo(" + x_coordinate + "," + y_coordinate + ")");
		System.out.println("scrolled To Specific Coordinates Successfully");
	}

	public static void scrollToElement(WebDriver driver, WebElement element) {
		Coordinates coordinate = ((Locatable) element).getCoordinates();
		coordinate.onPage();
		coordinate.inViewPort();
		System.out.println("scrolled To Element Successfully");
	}

	public static void selectByValueFromStaticDropDown(WebElement element, String enterDropDownValueToStelect) {
		Select select = new Select(element);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		select.selectByValue(enterDropDownValueToStelect);
	}

	public static void selectByIndexFromStaticDropDown(WebElement element, int enterDropDownIndexToStelect) {
		Select dropDownlist = new Select(element);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//dropDownlist.selectByIndex(0);
			dropDownlist.selectByIndex(enterDropDownIndexToStelect);

	}

	public static void selectByVisibleTextFromStaticDropDown(WebElement element,
			String enterDropDownVisibleTextToStelect) {
		Select select = new Select(element);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		select.selectByVisibleText(enterDropDownVisibleTextToStelect);
	}

	
	
	public static String genericWindowhandle(WebDriver driver, WebElement element) {

		UserWaits.waitForElementToBeClickable(driver, element, 5);
		// Store the ID of the original window
		String originalWindow = driver.getWindowHandle();

		// Check we don't have other windows open already
		assert driver.getWindowHandles().size() == 1;

		// Click the link which opens in a new window
		element.click();

		WebDriverWait wait = new WebDriverWait(driver, 60);
		// Wait for the new window or tab
		wait.until(ExpectedConditions.numberOfWindowsToBe(2));

		// Loop through until we find a new window handle
		for (String windowHandle : driver.getWindowHandles()) {
			if (!originalWindow.contentEquals(windowHandle)) {
				driver.switchTo().window(windowHandle);
				break;
			}
		}
		return originalWindow;
	}
	
	public static void getCordinates(WebDriver driver, WebElement element) {
		Point point = element.getLocation();
		int xcord = point.getX();
		int ycord = point.getY();
		System.out.println(element + " == x Cordinates ==> " + xcord + " y Cordinates ==> " + ycord);
	}

	public static void moveElementByCordinates(WebDriver driver, WebElement previousElement, WebElement elementToMove) {
		Point point = previousElement.getLocation();
		int xcord = point.getX();
		int ycord = point.getY();
		//System.out.println(previousElement + " == x Cordinates ==> " + xcord + " y Cordinates ==> " + ycord);
		Actions action = new Actions(driver);
		action.dragAndDropBy(elementToMove, xcord, ycord + 25).click().build().perform();
	}
	
	public static void staticDropDownhandeler(WebElement element, String textToSelect) {
		Select select = new Select(element);
		select.selectByVisibleText(textToSelect);
	}

	public static void selectMultipleValues(WebDriver driver, WebElement previousElement, WebElement elementToMove) {
		Actions action = new Actions(driver);
		action.keyDown(Keys.CONTROL).build().perform();
	}
	
	public static String uniqeDate(String userDefineFormat) {
		Date date = new Date();
		SimpleDateFormat spm = new SimpleDateFormat(userDefineFormat);
		String uniqeDate = spm.format(date);
		System.out.println("uniqeDate"+uniqeDate);
		return uniqeDate;
	}
	
	public static void switchToFrameByUsingWebElement(WebDriver driver, WebElement element) {
		driver.switchTo().frame(element);
	}

	public static void switchToFrameByNameOrID(WebDriver driver, String frameID_or_FrameName) {
		driver.switchTo().frame(frameID_or_FrameName);
	}

	public static void switchToDefaultView(WebDriver driver) {
		driver.switchTo().defaultContent();
	}
	
	public static void pressTabOnKeyBoardAndSendText(WebDriver driver, String textToAdd) {
		Actions actions = new Actions(driver);
		actions.sendKeys(Keys.TAB);
		actions.sendKeys(textToAdd);
		actions.build().perform();
	}
	
	public static void actionsClassSendKeys(WebDriver driver, String textToAdd) {
		Actions actions = new Actions(driver);
		actions.sendKeys(textToAdd).perform();
	}
	
	public static void actionsClassDoubleClick(WebDriver driver, WebElement element) {
		Actions actions = new Actions(driver);
		actions.doubleClick(element).perform();
	}

}
