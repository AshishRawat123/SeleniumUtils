package com.Docker.Selenium_Docker;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Optional;

import resource.BaseTest;
import resource.Contants;

public class Login extends BaseTest
{
	public static WebDriverWait wait;

	@SuppressWarnings("deprecation")
	public Login() {
		super();
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, 20);
	}

	@FindBy(xpath="//label[normalize-space(text())='Username:']/following-sibling::input")
	private WebElement userName;

	@FindBy(xpath="//label[normalize-space(text())='Password:']/following-sibling::input")
	private WebElement password;

	@FindBy(id="loginButton")
	private WebElement submitButton;

	@FindBy(xpath = "//i[@class='icon-user small']")
	private WebElement userLoginIcon;
	

	private static String session_Location= "//ul[@id='sessionLocation']/li[text()='sessionFor']";

	public void user_Login(String sessionFor,String username ,String passWord) {
		driver.get(Contants.LOGIN_PAGE);
		this.userName.sendKeys(username);
		this.password.sendKeys(passWord);
		driver.findElement(By.xpath(session_Location.replace("sessionFor", sessionFor))).click();
		this.submitButton.click();
	}


}
