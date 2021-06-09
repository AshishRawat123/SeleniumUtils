package com.Utilities;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import resource.BaseTest;

public class Text extends BaseTest{
	
	public Text() {
		super();
	}

	public static String javascript_GetText(By locator){

		String elementText = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].innerHTML",driver.findElement(locator));
		return elementText.trim();
	}
	
	public static String javascript_GetOuterHTML(By locator){
		
		String elementText = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].outerHTML",driver.findElement(locator));
		return elementText.trim();
	}
	
	public static String getText_ByAttribute(By locator , String attribute){
		String text = driver.findElement(locator).getAttribute(attribute).trim();
		return text;
	}
	
	public static ArrayList<String> getElements_Text(By locator) {

		ArrayList<String> data = new ArrayList<String>();
		List<WebElement>  webList = driver.findElements(locator);
		for(WebElement el : webList)
		{
			data.add(el.getText().trim());
		}
		return data;
	}
	
	public static String  action_getText(By by) throws UnsupportedFlavorException, IOException{
		Actions a = new Actions(driver);
		a.click(driver.findElement(by)).build().perform();
		a.keyDown(Keys.CONTROL).sendKeys("a").perform();
		a.keyUp(Keys.CONTROL).perform();
		a.keyDown(Keys.CONTROL).sendKeys("c").perform();
		a.keyUp(Keys.CONTROL).perform();
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		return (String) clipboard.getData(DataFlavor.stringFlavor);
	}
	
	
}
