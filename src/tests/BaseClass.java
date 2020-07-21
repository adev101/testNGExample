package tests;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseClass {

	public WebDriver driver;
	
	@Parameters("browsername")
	@BeforeMethod
	public void launchBrowser(@Optional("Chrome") String browsername) {
		System.out.println("Before Method called");
		//String browsername="chrome";
		System.out.println("Launching Browser");
		if(browsername.equalsIgnoreCase("chrome"))
		{
			System.setProperty("webdriver.chrome.driver","D:\\java classes\\selenium\\drivers\\chromedriver.exe");
			driver=new ChromeDriver();
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			driver.get("https://www.google.com");
		}
		else if(browsername.equalsIgnoreCase("firefox"))
		{
			System.setProperty("webdriver.gecko.driver","D:\\java classes\\selenium\\drivers\\geckodriver.exe");
			driver=new FirefoxDriver();
			driver.get("https://www.google.com");
		}
	}
	
	@AfterMethod(alwaysRun=true)
	public void closeBrowser() {
		System.out.println("After Method called");
		driver.close();
	}
	
	@BeforeSuite
	public void methodBeforeSUite() {
		System.out.println("Starting test suite");
	}

	@AfterSuite
	public void methodAfterSUite() {
		System.out.println("Ending test suite");
	}
}
