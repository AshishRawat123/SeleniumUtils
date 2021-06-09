package com.Docker.Selenium_Docker;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
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
 
	@Test()
	@Parameters({"sessionFor","username","password","willLogin"})
	public void Login_Test(String sessionFor,@Optional String username ,@Optional String password ,String willLogin) {
		username=(username==null)? Contants.USER:username;
		password=(password==null)?Contants.PASS:password;
		login.user_Login(sessionFor ,username ,password);
		if(!Boolean.parseBoolean(willLogin.toLowerCase())) {
			Assert.assertTrue(driver.findElement(By.id("error-message")).isDisplayed());
			Assert.assertEquals("Invalid username/password. Please try again.", driver.findElement(By.id("error-message")).getText());	
		}
		else {
			Assert.assertTrue(driver.getCurrentUrl().toLowerCase().contains("home.age"),"Current URL not contain 'home.page'\n Actual URL : "+driver.getCurrentUrl());
		}
		SoftAssertions.softAssertion("Failing of Assertion", "true", null, "false", "Why are you failing",null);
	}
}
