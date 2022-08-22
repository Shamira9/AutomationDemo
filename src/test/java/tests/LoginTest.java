package tests;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

//import pageObjects.BasePage;
import pageObjects.LoginPage;
import pageObjects.ProductsPage;

public class LoginTest {

	// login to the site
	// users: standard_user, locked_out_user,  problem_user, performance_glitch_user
	// Password: secret_sauce

	//set variables
	String user; 
	String password;
	double loadTime;
	private WebDriver driver;
	private LoginPage lp;
	private ProductsPage pp;
	//set testNG variables


	@BeforeClass 
	public void setup(){
		System.setProperty("webdriver.chrome.driver", "C:\\automation\\drivers\\chromedriver.exe");
		driver = new ChromeDriver();

		driver.manage().window().maximize();
		driver.get("https://www.saucedemo.com/");
		//all tests on this page will use the LoginPage
		lp = new LoginPage(driver);
		pp = new ProductsPage(driver);

	}

// test 4 user types with bad password. 
//expecting error message text
	
	@Test (description = "empty user, empty password")
	public void TC01_noUser_noPassword() {
		user = "";
		password = "";
		lp.login(user, password);

		String actual = lp.getErrorMsg();
		String expected = "Epic sadface: Username is required";
		Assert.assertEquals(actual,expected);   
	}
	
	@Test (description = "good user, empty password")
	public void TC02_noPassword() {
		user = "standard_user";
		password ="";
		lp.login(user, password);
		String actual = lp.getErrorMsg();
		String expected = "Epic sadface: Password is required";
		Assert.assertEquals(actual,expected);   
	}

	@Test (description = "empty user, any password",enabled = false)
	//bug causes user filed to be re-populated with previous text
	public void TC03_noUser() {
		user = "";
		password = "any";
		lp.login(user, password);

		String actual = lp.getErrorMsg();
		String expected = "Epic sadface: Username is required";
		Assert.assertEquals(actual,expected); 
		lp.sleep(2000);
	}

	@Test (description = "good user, bad password")
	public void TC04_badPassword() {
		user = "standard_user";
		password ="not_sauce";
		lp.login(user, password);
		String actual = lp.getErrorMsg();
		String expected = "Epic sadface: Username and password do not match any user in this service";
		Assert.assertEquals(actual,expected);   
	}

	@Test (description = "no existent user")
	public void TC05_badUsedr() {
		//LoginPage lp = new LoginPage(driver);
		user = "No_such_user";
		password = "secrete_sauce";
		lp.login(user, password);

		String actual = lp.getErrorMsg();
		String expected = "Epic sadface: Username and password do not match any user in this service";
		Assert.assertEquals(actual,expected);   
	}

	@Test (description = "Locked_out_user ")
	public void TC06_locked_out_user() {
		//LoginPage lp = new LoginPage(driver);
		user = "locked_out_user";
		password = "secret_sauce";
		lp.login(user, password);

		String actual = lp.getErrorMsg();
		String expected = "Epic sadface: Sorry, this user has been locked out.";
		Assert.assertEquals(actual,expected);   
	}
	
// logins that succeed - test type of user	
	
	@Test (description = "Standard_user ")
	public void TC07_Standard_User() {
		//LoginPage lp = new LoginPage(driver);
		user = "standard_user";
		password = "secret_sauce";
		double startTime = lp.getTime();
		lp.login(user, password);
		loadTime = pp.pageLoadTime() - startTime;
		String product = "Sauce Labs Backpack";
		String actual = pp.getProductImgSrc(product);
		String expected = "https://www.saucedemo.com/static/media/sauce-backpack-1200x1500.34e7aa42.jpg";
		Assert.assertEquals(actual,expected);
		pp.chooseFromSideMenu("LOGOUT");
	}
	
	
	@Test (description = "Problem_user , results in bad image")
	public void TC08_Problem_User() {
		user = "problem_user";
		password = "secret_sauce";
		lp.login(user, password);
		
		String product = "Sauce Labs Backpack";
		String actual = pp.getProductImgSrc(product);
		String expected = "https://www.saucedemo.com/static/media/sl-404.168b1cce.jpg";
		Assert.assertEquals(actual,expected);  
		pp.chooseFromSideMenu("LOGOUT");
	}
	
	
	@Test (description = "performance_glitch_user , results in delayed response")
	public void TC09_performance_glitch_user() {
		user = "performance_glitch_user";
		password = "secret_sauce";
		double startTime = lp.getTime();
		lp.login(user, password);
		
		double delayedLoadTime = pp.pageLoadTime() - startTime;
		System.out.println("regular load time = : "  + loadTime);
		System.out.println("glitch load time = : "  + delayedLoadTime); 
		Assert.assertTrue(delayedLoadTime > 1.5 * loadTime ); 
		//Assert.assertTrue(pp.isProductsPage()); 
		pp.chooseFromSideMenu("LOGOUT");  
	}
	
	@AfterMethod
	public void failedTest(ITestResult result) {
	  //check if the test failed - if so take screen shot
		if (result.getStatus() == ITestResult.FAILURE ){
			TakesScreenshot ts = (TakesScreenshot)driver;
			File srcFile = ts.getScreenshotAs(OutputType.FILE);
			try {
				FileUtils.copyFile(srcFile, new File("./ScreenShots/"+result.getName()+".jpg"));
			} catch (IOException e) {
				// Auto-generated catch block
				e.printStackTrace();
			}
			//result.getname() method will give you current test case name. 
			//./ScreenShots/ tell you that, in your current directory, create folder ScreenShots. dot represents current directory
		}
	}
	
	@AfterClass
	public void tearDown() throws InterruptedException	{
		Thread.sleep(3000);
		driver.quit();
		System.out.println("End");
	}



}





