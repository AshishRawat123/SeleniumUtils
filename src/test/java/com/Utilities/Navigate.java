package com.Utilities;

import java.util.ArrayList;

import org.openqa.selenium.WindowType;

import resource.BaseTest;

public class Navigate extends BaseTest {
	
	public Navigate (){
		super();
	}
	
	public static void open_And_Switch_To_New_TAB() {
		driver.switchTo().newWindow(WindowType.TAB);
	}
	
	public static void switch_To_TAB(int index) {
		ArrayList<String> tab = new ArrayList<String> (driver.getWindowHandles());
		if(tab.size()>=index-1)
		driver.switchTo().window(tab.get(index));
		else
			//LOG Needed if there is no such window
			System.out.println("");
	}
	
	public static void close_Current_TAB() {
		//Only close the TAB if there is more than 1 TAB
		if(driver.getWindowHandles().size()>0)
			driver.close();
		
		ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
		int tabSize= tabs.size();
		driver.switchTo().window(tabs.get(tabSize-1));
	}
	
	public static void close_All_TAB_Except_Current() {
		String currentTab = driver.getWindowHandle();
		ArrayList<String> windowList = get_TABs_List();
		for(String s: windowList) {
			if(!s.equals(currentTab)) {
				driver.switchTo().window(s);
				driver.close();
			}
		}
		driver.switchTo().window(currentTab);
	}
	
	public static void navigate_To_URL(String URL) {
		driver.navigate().to(URL);
		Visible.page_To_Load();
	}
	
	public static void page_Refresh() {
		driver.navigate().refresh();
		Visible.page_To_Load();
	}
	
	public static void navigate_forward() {
		driver.navigate().forward();
		Visible.page_To_Load();
	}
	
	public static void navigate_Back() {
		driver.navigate().back();
		Visible.page_To_Load();
	}
	
	public static ArrayList<String> get_TABs_List() {
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		return tabs;
	}
	
	public static void switch_To_First_TAB() {
		ArrayList<String> tabs= new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(0));		
	}

}
