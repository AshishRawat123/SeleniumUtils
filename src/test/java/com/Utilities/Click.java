package com.Utilities;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import resource.BaseTest;
import resource.Contants;

public class Click extends BaseTest{

	protected static WebDriverWait wait;

	protected static void click(By by) {
		driver.findElement(by).click();
	}

	@SuppressWarnings("deprecation")
	protected static void click_And_WaitFor(By click_On, By waitFor) {
		driver.findElement(click_On).click();
		Visible.page_To_Load();
		wait = new WebDriverWait(driver, Contants.Expicit_wait_time);
		wait.until(ExpectedConditions.presenceOfElementLocated(waitFor));		
	}

	@SuppressWarnings("deprecation")
	protected static void Webdriver_click_Link_Text(String linkText){	
		driver.findElement(By.linkText(linkText)).click();
		(new WebDriverWait(driver, Contants.Expicit_wait_time)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//body")));	
		Visible.page_To_Load();
	}

	@SuppressWarnings("deprecation")
	protected static void Webdriver_click_PartialLink_Text(String linkText){
		driver.findElement(By.partialLinkText(linkText)).click();
		(new WebDriverWait(driver, Contants.Expicit_wait_time)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//body")));
		Visible.page_To_Load();
	}

	public static void click_WebElemets(By by) {
		List<WebElement> elementList = driver.findElements(by);
		for(WebElement ele : elementList) {
			ele.click();
		}}

	public static void javaScript_Click(By by) {
		WebElement element = driver.findElement(by);
		((JavascriptExecutor)driver).executeScript("arguments[0].click()", element);
		Visible.page_To_Load();
	}

	public static void action_Click() {
		Actions act = new Actions(driver);
		act.click().perform();Visible.page_To_Load();
	}

	public static void action_DoubleClick() {
		Actions act = new Actions(driver);
		act.doubleClick().perform();Visible.page_To_Load();
	}
	
	@SuppressWarnings("deprecation")
	public static void Webdriver_Link_In_New_TAB(By by)
	{
		
		(new WebDriverWait(driver, Contants.Expicit_wait_time)).until(ExpectedConditions.presenceOfElementLocated(by));
		(new WebDriverWait(driver, Contants.Expicit_wait_time)).until(ExpectedConditions.elementToBeClickable(driver.findElement(by)));
		
		driver.findElement(by).sendKeys(Keys.chord(Keys.CONTROL,Keys.RETURN));
		ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
		int tabSize= tabs.size();
		driver.switchTo().window(tabs.get(tabSize-1));
		Visible.page_To_Load();
	}

}
