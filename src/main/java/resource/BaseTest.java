package resource;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
	
	public static WebDriver driver;
	public static SoftAssert softAssert;
	public static ITestContext testContext;
	
	public BaseTest() {
	}
	
	@SuppressWarnings({ "unused", "deprecation" })
	@BeforeTest()
	@Parameters({"skipTest"})
	public void beforeTest(@Optional String skipTest , ITestContext testContext){
		Contants.CURRENT_TEST_NAME = testContext.getName();
		testContext.setAttribute("Current_Test_Name", testContext.getName());
		BaseTest.testContext= testContext;
		if(skipTest!=null && skipTest.equals("yes"))
			throw new SkipException("Skipping "+testContext.getName()+" Test for now");
		softAssert= new SoftAssert();
		if(Contants.LOCAL_BROWSER.equals("chrome"))
			chromeDriverSetup();
		else
			firefoxDriverSetup();
		driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.MINUTES);
		driver.manage().window().maximize();
	}
	
	@AfterTest()
	public void afterTest(){
		driver.quit();
		softAssert.assertAll();
	}

	public void chromeDriverSetup() {
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_settings.popups",0);
		//Select download directory of Current Project to download files
		chromePrefs.put("download.default_directory",System.getProperty("user.dir")+"//download");
		ChromeOptions  options = new ChromeOptions();
		options.addArguments("disable-infobar");
		options.setExperimentalOption("prefs", chromePrefs);
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();	
		System.out.println("CHROME LAUNCHED");

	}
	
	public void firefoxDriverSetup() {
		FirefoxOptions options = new FirefoxOptions();
		options.addArguments("disable-infobar");
		options.addPreference("browser.download.dir",System.getProperty("user.dir")+"//download");
		WebDriverManager.firefoxdriver().setup();
		driver = new FirefoxDriver();
		System.out.println("FIREFOX LAUNCHED");
	}
}
