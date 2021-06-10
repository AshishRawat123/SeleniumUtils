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
			String assertion_Test_Details = "Test Name Failed : "+Contants.CURRENT_TEST_NAME+
					"<br>Failed while Validating  : "+UniqueName+
					"</br>Class Failed : "+errorStack.getClassName().toString()+
					"<br>METHOD FAILED & and Line number : "+errorStack.getMethodName().toString()+" -- "+errorStack.getLineNumber()+
//					"</br>In Line number : "+errorStack.getLineNumber()+
					"</br><br>Failure Message  : "+failure_message+
					"</br>Assertion Fail in URL : <a href='"+driver.getCurrentUrl()+"' target='_blank'>Click here</br>\n";

			switch(type) {
			case "equals" :
				softAssert.assertEquals(actual, expected,assertion_Test_Details);
				break;

			case  "notequals" :
				softAssert.assertNotEquals(actual, expected ,assertion_Test_Details);
				break;

			case "true" :
				softAssert.assertTrue(Boolean.parseBoolean(actual),assertion_Test_Details);
				break;

			case "contains" :
				softAssert.assertTrue(actual.contains(expected),assertion_Test_Details);
				break;	

			case "false" :
				softAssert.assertFalse(Boolean.parseBoolean(actual),assertion_Test_Details);
				break;

			case "displayed" :
				softAssert.assertTrue(driver.findElement(locator).isDisplayed(), assertion_Test_Details);
				break;

			case "not-displayed" :
				softAssert.assertFalse(driver.findElement(locator).isDisplayed(), assertion_Test_Details);
				break;

			case "enabled" :
				softAssert.assertTrue(driver.findElement(locator).isEnabled(), assertion_Test_Details);
				break;

			case "present" :
				softAssert.assertTrue(Visible.is_Present(locator, 0), assertion_Test_Details);
				break;

			}
		}
	}
}

