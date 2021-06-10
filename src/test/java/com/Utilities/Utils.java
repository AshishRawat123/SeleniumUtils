package com.Utilities;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;

import resource.BaseTest;

public class Utils extends BaseTest{

	public Utils() {
		super();
	}

	public static void waitUntil_Ajax_complete() {

		@SuppressWarnings("deprecation")
		WebDriverWait wait = new WebDriverWait(driver, 60);
		try {
			wait.until(new Function<WebDriver,Boolean>() {
				@Override
				public Boolean apply(WebDriver driver) {
					JavascriptExecutor je = (JavascriptExecutor) driver;
					if(Boolean.parseBoolean(String.valueOf(je.executeScript(" return jQuery.active === 0")))) {
						return Boolean.parseBoolean(String.valueOf(je.executeScript(" return jQuery.active === 0")
								));
					}
					return false;
				}
			});	}catch (Exception e) {
				//logger
				System.out.println("AJAX Request not completed in 60 seconds");
			}
	}

}
