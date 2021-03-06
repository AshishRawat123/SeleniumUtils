package resource;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
	
	public static WebDriver driver;
	public static SoftAssert softAssert;
	public static ITestContext testContext;
	private ExtentHtmlReporter extentHTMLReporter;
	private static ExtentReports extentReport;
	public static ExtentTest logger;
	private boolean  isLogged= false;
	
	@BeforeSuite()
	public void before_Suite(ITestContext context){
		
		Contants.Download_Folder = System.getProperty("user.dir")+"\\download";	
		extentHTMLReporter = new ExtentHtmlReporter(System.getProperty("user.dir")+"/ExtentReport/"+context.getSuite().getName()+System.currentTimeMillis()+".html");
		extentHTMLReporter.config().setTheme(Theme.DARK);	
		extentReport = new ExtentReports();  
		extentReport.attachReporter(extentHTMLReporter);
		extentReport.setSystemInfo("Host Name", "QA");
		extentReport.setSystemInfo("User Name", "Ashish Rawat");
	
	}
	
	@SuppressWarnings({ "deprecation" })
	@BeforeTest()
	@Parameters({"skipTest"})
	public void beforeTest(@Optional String skipTest , ITestContext testContext){
		//Add Code to log Test Name to extent Reporting file at Test level
		Contants.CURRENT_TEST_NAME = testContext.getName();
		testContext.setAttribute("Current_Test_Name", testContext.getName());
		logger = extentReport.createTest(Contants.CURRENT_TEST_NAME);
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
	
	@BeforeMethod()
	public void before_Method(Method methodName) {
		//If tests are methods then use below code
		/*
		 logger = extentReport.createTest(methodName.getName());
		 softAssert = new SoftAssert();
		*/
		System.out.println("before method of base class");
	}
	
	@AfterMethod(alwaysRun = true)
	public void tearDown(ITestResult result , ITestContext testContext, Method method) throws IOException, ParseException {
		//   Add ScreenShot on failure
		if (result.getStatus() == ITestResult.FAILURE) {	
			String screenshotName= method.getName()+System.currentTimeMillis()+".png";
			logger.log(Status.FATAL, MarkupHelper.createLabel("<a href='"+driver.getCurrentUrl()+"' target='_blank'>Test Failed URL</a>", ExtentColor.LIME));
			String scrnshtpath= screenShot(screenshotName);
			logger.addScreenCaptureFromPath("."+scrnshtpath);
			try {
				softAssert.assertAll();
				}
				catch (AssertionError e) {
					if(!isLogged) {
					logger.log(Status.WARNING, MarkupHelper.createLabel(e.getMessage(), ExtentColor.PURPLE));
					isLogged=true;
					}
				}
			logger.log(Status.FAIL, MarkupHelper.createLabel(result.getThrowable() + " - Test Case Failed", ExtentColor.RED));
		}	
		/*
		 * SKIP=3
		 * SUCESS=1
		 * FAILURE = 2
		 * */
		else if(result.getStatus()==3) {
			logger.log(Status.SKIP, MarkupHelper.createLabel(result.getThrowable() + " - Test Case Failed", ExtentColor.ORANGE));
		}
	}   
	
	
	@AfterTest(alwaysRun = true)
	public void afterTest(){
		driver.quit();
		//softAssert.assertAll();
		try {
		softAssert.assertAll();
		}
		catch (AssertionError e) {
			if(!isLogged)
			logger.log(Status.INFO, MarkupHelper.createLabel(e.getMessage(), ExtentColor.PURPLE));
		}
		isLogged=false;
		extentReport.flush();
	}
	
	@AfterSuite(alwaysRun=true)
	public void after_Suite(){
		System.out.println("AFTER SUITEEE");
	}

	public void chromeDriverSetup() {
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_settings.popups",0);
		//Select download directory of Current Project to download files
		chromePrefs.put("download.default_directory",Contants.Download_Folder);
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
		options.addPreference("browser.download.dir",Contants.Download_Folder);
		WebDriverManager.firefoxdriver().setup();
		driver = new FirefoxDriver();
		System.out.println("FIREFOX LAUNCHED");
	}
	
	public static String screenShot(String methodFailed) throws IOException {
		TakesScreenshot scrShot =((TakesScreenshot)driver);
		File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
		String path = ".\\screenshot\\"+methodFailed;
		File DestFile=new File(path);
		FileUtils.copyFile(SrcFile, DestFile);
		return path;
	}
	
//	public static long convertCurrentTimeToMS() throws ParseException {
//		Date dateobj = new Date();
//		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
//		Date dateAndTimeInDateFormat = sdf.parse(sdf.format(dateobj));
//		Calendar calObj = Calendar.getInstance();
//		calObj.setTime(dateAndTimeInDateFormat);
//		return  dateAndTimeInDateFormat.getTime();
//	}
}
