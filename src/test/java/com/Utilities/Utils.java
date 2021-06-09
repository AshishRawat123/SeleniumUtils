package com.Utilities;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;

import resource.BaseTest;
import resource.Contants;

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
				System.out.println("All AJAX Request not Completed in 60  Seconds");
			}
	}
	
	public static boolean is_Downloaded(String path, String File_Name) {
		
		path = (path==null)?Contants.Download_Folder:path;
		File file = new File(path);
	    String[] fileList = file.list();
	    for(String filez : fileList) {
	      if(filez.equalsIgnoreCase(File_Name)) {
	    	  //Delete File for future purpose
	    	  new File(path+"//"+filez).delete();
	    	  return true;
	    	  }
	    }
		return false;
	}
	
}
