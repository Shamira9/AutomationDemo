package tests;

import java.io.File;
import java.io.IOException;


import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.apache.commons.io.FileUtils;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import io.qameta.allure.Attachment;
import io.qameta.allure.Description;
//import pageObjects.BasePage;
import pageObjects.LoginPage;
import pageObjects.MenuLayer;
import utils.Config;



public class BaseTest {
	WebDriver driver;
	@BeforeClass
	public void b01_setupDriver(){
		
		System.setProperty("webdriver.chrome.driver", "C:\\automation\\drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();

		String url = Config.readKey("url");
//		System.out.println("url = " + url);
		//temp override
		url = "https://www.saucedemo.com/";

		driver.get(url);

		//all tests on this page will use the LoginPage
	}
	
	@BeforeClass (description = "move from Landing to Login Page")
	public void b02_login(){
		
		LoginPage lp = new LoginPage(driver);
		String user = Config.readKey("user");	
		String password = Config.readKey("password");
		// temporarly set values here.
		user = "standard_user";
		password ="secret_sauce";
		lp.login(user, password);
	}

	
	@Description("Take screen shot if test fails")
	@Attachment 
	@AfterMethod
		public void failedTest(ITestResult result) {
		  //check if the test failed - if so take screen shot
			if (result.getStatus() == ITestResult.FAILURE ){
				TakesScreenshot ts = (TakesScreenshot)driver;
				File srcFile = ts.getScreenshotAs(OutputType.FILE);
				try {
					FileUtils.copyFile(srcFile, new File("./ScreenShots/"+result.getName()+".jpg"));
				} catch (IOException e) {
					//  Auto-generated catch block
					e.printStackTrace();
				}
				//result.getname() method will give you current test case name. 
				//./ScreenShots/ tell you that, in your current directory, create folder ScreenShots. dot represents current directory
			}
		}
	
	@AfterClass (description= "logout after test",enabled = true)
	public void z01_logout() throws InterruptedException	{
		MenuLayer mn = new MenuLayer(driver);
		mn.chooseFromSideMenu("LOGOUT");
	}

	@AfterClass
	public void z02_tearDown() throws InterruptedException	{
		Thread.sleep(1000);
		driver.quit();
	}
}
