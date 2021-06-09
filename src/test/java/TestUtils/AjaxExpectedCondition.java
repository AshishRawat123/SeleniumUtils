package TestUtils;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.tools.ant.taskdefs.WaitFor.Unit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.Utilities.Visible;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AjaxExpectedCondition {

	public static WebDriver driver=null;

	@BeforeMethod
	public void launchBrowser() throws InterruptedException {
		if(driver==null) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		}
		driver.get("http://www.uitestpractice.com/Students/Contact");
		driver.manage().window().maximize();
	}


	@Test
	public void caustomCondition() throws InterruptedException, IOException {

		driver.findElement(By.linkText("This is a Ajax link")).click();

		try {
			new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='Resu']//p")));
		}
		catch(Exception e) {System.out.println(("element not present hence returning false"));
		}

		WebDriverWait wait = new WebDriverWait(driver, 7);
		wait.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				List<WebElement> el = driver.findElements(By.xpath("//div[@id='Result']//p"));
				if(el.get(0).getText().contains("Please")) {
					System.out.println("CHECKINGGGGGGG");
					return false;
				}
				else
				{
					System.out.println(el.get(0).getText());
					return true;
				}
			}
		});
	}
	@Test
	public void waitForAjaxCalls() throws InterruptedException, IOException {

		driver.findElement(By.linkText("This is a Ajax link")).click();
		Thread.sleep(2000);
		WebDriverWait wait = new WebDriverWait(driver, 7);
		wait.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
				System.out.println(((JavascriptExecutor)driver).executeScript("return document.readyState").toString());
				return ((JavascriptExecutor)driver).executeScript("return document.readyState").toString().contains("interactive");
			}
		});
	}

	@Test()
	public void check_Presence() {
		driver.findElement(By.linkText("This is a Ajax link")).click();
		Visible.is_Present(By.xpath("//div[@id='Result']//p"), 3);
	}
	
	@Test()
	public void check_Till_TextPresent() {
		driver.findElement(By.linkText("This is a Ajax link")).click();
		Visible.element_Contains_Text((By.xpath("//div[@id='Result']//p")), "Sxxenium");
		}
	
}
