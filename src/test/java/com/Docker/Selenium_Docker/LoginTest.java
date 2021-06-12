package com.Docker.Selenium_Docker;

import java.lang.reflect.Method;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.Utilities.SoftAssertions;

import resource.BaseTest;
import resource.Contants;

public class LoginTest extends BaseTest{

	private Login login;
	
	@BeforeClass()
	public void beforeClass() {
		login = new Login();
	}
	
	@BeforeMethod()
	public void before_Method(Method methodName) {

		//Note: this method will override the method of BaseClass beforeMethod
		System.out.println("before method of Test class");
	}
 
	@Test()
	@Parameters({"sessionFor","username","password","willLogin"})
	public void Login_Test(String sessionFor,@Optional String username ,@Optional String password ,String willLogin) {
		username=(username==null)? Contants.USER:username;
		password=(password==null)?Contants.PASS:password;
		login.user_Login(sessionFor ,username ,password);
		SoftAssertions.softAssertion("SHOULD NOT BE PRESENT ON REPORTING ", "true", null, "true", "should not be on EXTENT REPORT",null);
		SoftAssertions.softAssertion("Test Assertion", "true", null, "false", "Why are you failing",null);
		SoftAssertions.softAssertion("Second Assertion Failure", "true", null, "false", "Check 2nd Failure",null);
		SoftAssertions.softAssertion("Check presence of element on DOM", "present", null,null, "Check Element present in DOM ",By.xpath("//div/span/abc"));
		if(!Boolean.parseBoolean(willLogin.toLowerCase())) {
			Assert.assertTrue(driver.findElement(By.id("error-message")).isDisplayed());
			Assert.assertEquals("Invalid username/password. Please try again.", driver.findElement(By.id("error-message")).getText());	
		}
		else {
			Assert.assertTrue(driver.getCurrentUrl().toLowerCase().contains("home.age"),"Current URL not contain 'home.age'\n Actual URL : "+driver.getCurrentUrl());
		}
	}
}
