package tests;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import dataProvider.MyDataProvider;

public class tests extends BaseClass{
	
	static String url= null, urlA=null, currURL=null;
	static HttpURLConnection huc = null;
    static int respCode = 200;
    static int brokenUrlCount=0;
	
  @BeforeClass
  public void startMessage() {
		System.out.println("Starting the test class");
	}

  
  @Test (priority=1, groups="main test", dataProvider="SearchString", dataProviderClass=MyDataProvider.class)
  public void googleSearch(String str) throws InterruptedException {
	 
	  WebElement element;
	  String strToSearch=str;
	  boolean strText;
	  
	  element=driver.findElement(By.xpath("//*[@id=\"tsf\"]/div[2]/div[1]/div[1]/div/div[2]/input"));
	  
	  element.sendKeys(strToSearch);
	  element.sendKeys(Keys.ENTER);
	  Thread.sleep(5000);
	  
	  element=driver.findElement(By.xpath("//*[@id=\"rso\"]/div[1]/div/div[1]/a/h3"));
	  
	  strText=element.getText().toLowerCase().contains(strToSearch.toLowerCase());
	  
	  //assertTrue(strText);
	  //assertfalse
	  //assertNull
	  //assertNotNull
	  //assertEquals
	  //assertNotEquals
	  //assertSame
	  //assertNotSame
	  
	  SoftAssert sa= new SoftAssert();
	  sa.assertTrue(strText, "Test case 1 failed");
	  
  }
  
  @Test(dependsOnMethods="googleSearch", priority=2,groups= {"main test", "other test"})
  public void googleBrokenLink() {
	  List<WebElement> ls;
	  int count=0;
	  
	  ls=driver.findElements(By.xpath("//a[@href]"));
	  
	  count=checkBrokenLinks(ls);
	  
	  Assert.assertEquals(count, 0);
	  
  }
  
  
  @Test (priority=3,groups="other test")
  public void checkLanguages() {
	  System.out.println("Sample test case just for checking groups");
  
  }
  
  @Test (priority=4, enabled=false)
  public void checkDisabled() {
	  System.out.println("Sample test case just for checking enabled=false");
  
  }
  
  @AfterClass
  public void endMessage() {
		System.out.println("Completed the test class");
	}
  
  @BeforeTest
  public void startTest() {
	  System.out.println("Starting tests now");
  }
  
  
  @AfterTest
  public void endTest() {
	  System.out.println("Ending tests now");
  }
  
  @AfterMethod
  public void getStatus(ITestResult mytestresult) {
	  if(mytestresult.getStatus()==ITestResult.SUCCESS) {
		  System.out.println("PASS- From ITest result. method passed is"+ mytestresult.getMethod().getMethodName());
	  }else if(mytestresult.getStatus()==ITestResult.FAILURE) {
		  System.out.println("FAILED- From ITest result. method failed is"+ mytestresult.getMethod().getMethodName());
	  }else if(mytestresult.getStatus()==ITestResult.SKIP) {
		  System.out.println("SKIP- From ITest result. method skipped is"+ mytestresult.getMethod().getMethodName());
	  }
  }

  
  public static int checkBrokenLinks(List<WebElement> element) {
		
		try {
			Iterator<WebElement> it= element.iterator();
			while(it.hasNext()){
			    
			    urlA = it.next().getAttribute("href");
			    
			    //System.out.println(urlA);
			
			    if(urlA == null || urlA.isEmpty() || urlA.startsWith("javascript")){
			    	//System.out.println("URL is either not configured for anchor tag or it is empty");
			        continue;
			    }
			
			    huc = (HttpURLConnection)(new URL(urlA).openConnection());
			    
			    huc.setRequestMethod("HEAD");
			    
			    huc.connect();
			    
			    respCode = huc.getResponseCode();
			    
			    if(respCode == 400 ||respCode ==401 || respCode ==500){
			    	brokenUrlCount++;
			        //System.out.println(urlA+" is a broken link");
			    }
			}	
		    System.out.println("Total Broken Links: "+ brokenUrlCount);
		    
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return brokenUrlCount;
		
	}
  
  
}
