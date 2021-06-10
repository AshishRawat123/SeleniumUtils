package com.Utilities;

import java.util.HashMap;

import org.openqa.selenium.By;

import resource.BaseTest;
import resource.Contants;

public class SoftAssertions extends BaseTest{

	private static HashMap<String, String> hmap = new HashMap<String, String> ();
	public static void softAssertion(String UniqueName,String type, String expected, String actual, String failure_message,By locator)
	{
		if(!hmap.containsKey(UniqueName)) {
			StackTraceElement errorStack = new Exception().getStackTrace()[1];
			String class_method_Details = "Test Name Failed : "+Contants.CURRENT_TEST_NAME+
					"\nFailed while Validating  : "+UniqueName+
					"\nClass Failed : "+errorStack.getClassName().toString()+
					"\nMETHOD FAILED  : "+errorStack.getMethodName().toString()+
					"\nIn Line number : "+errorStack.getLineNumber()+
					"\nAssertion Fail in URL : "+driver.getCurrentUrl();

			switch(type) {
			case "equals" :
				softAssert.assertEquals(actual, expected,class_method_Details+"\nFailure Message  : "+failure_message);
				break;

			case  "notequals" :
				softAssert.assertNotEquals(actual, expected ,class_method_Details+"\nFailure Message  : "+failure_message);
				break;

			case "true" :
				softAssert.assertTrue(Boolean.parseBoolean(actual),class_method_Details+"\nFailure Message  : "+failure_message);
				break;

			case "contains" :
				softAssert.assertTrue(actual.contains(expected),class_method_Details+"\nFailure Message  : "+failure_message);
				break;	

			case "false" :
				softAssert.assertFalse(Boolean.parseBoolean(actual),class_method_Details+"\nFailure Message  : "+failure_message);
				break;

			case "displayed" :
				softAssert.assertTrue(driver.findElement(locator).isDisplayed(), class_method_Details+"\nFailure Message  : "+failure_message);
				break;

			case "not-displayed" :
				softAssert.assertFalse(driver.findElement(locator).isDisplayed(), class_method_Details+"\nFailure Message  : "+failure_message);
				break;

			case "enabled" :
				softAssert.assertTrue(driver.findElement(locator).isEnabled(), class_method_Details+"\nFailure Message  : "+failure_message);
				break;

			case "present" :
				softAssert.assertTrue(Visible.is_Present(locator, 0), class_method_Details+"\nFailure Message  : "+failure_message);
				break;

			}
		}
	}
}

