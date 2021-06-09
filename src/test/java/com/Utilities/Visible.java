package com.Utilities;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import resource.BaseTest;
import resource.Contants;

public class Visible extends BaseTest{

	public static WebDriverWait wait;
	public Visible() {
		super();
	}

	@SuppressWarnings("deprecation")
	public static void page_To_Load() {
		wait = new WebDriverWait(driver, 15);
		wait.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
				return (Boolean) ((JavascriptExecutor)driver).executeScript("return document.readyState==complete");
			}
		});
	}

	@SuppressWarnings("deprecation")
	public static boolean is_Present(By by, int waitForTime) {	
		try {
			waitForTime = (waitForTime==0)? Contants.Expicit_wait_time:waitForTime;
			wait = new WebDriverWait(driver, waitForTime);
			wait.until(ExpectedConditions.presenceOfElementLocated(by));
			return true;
		}catch(Exception e) 
		{
			System.out.println("Element not present hence returning false");
			return false;}

	}

	public static void Scroll_View(By by) {
		WebElement scroll = driver.findElement(by);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView()", scroll);
	}
	public static void Scroll_Views(By by) {
		WebElement scrollToElement = driver.findElement(by);
		Actions actions = new Actions(driver);
		actions.moveToElement(scrollToElement);
		actions.perform();
	}
	
	//Required to Test
	@SuppressWarnings("deprecation")
	public static boolean element_Contains_Text(By by, String validate_Text) {
		try {
		new WebDriverWait(driver, 10).until(
					new ExpectedCondition<Boolean>() {			
			@Override
			public Boolean apply(WebDriver driver) {
					WebElement el = driver.findElement(by);
					return el.getText().contains(validate_Text);
				}
					});
		}
		catch (Exception e) {
			System.out.println("Element is not present with Text "+validate_Text+"/nBy :"+by.toString());return false;
	}
		return false;
	}

}
