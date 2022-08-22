package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
//import pageObjects.BasePage;
import pageObjects.LoginPage;
import pageObjects.ProductsPage;

public class LoginTest2 {

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
	
	@Feature("Login user types")
	@Description("Login to site with various user password combinations")
	
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
	
	
	
	@DataProvider
	public Object[][] getFailData(){
		Object[][] myData = {
				{"","","Epic sadface: Username is required"},
				{"standard_user","","Epic sadface: Password is required"},
				//{"","badData","Epic sadface: Password is required"},  - bug on site prevents data deletion from previous test
				{"standard_use","badData","Epic sadface: Username and password do not match any user in this service"},
				{"No_such_user","secrete_sauce","Epic sadface: Username and password do not match any user in this service"},
				{"locked_out_user","secrete_sauce","Epic sadface: Sorry, this user has been locked out."}, //bug due to automation
		};
		return myData;
	}

	// test 4 user types with bad password. 
	//expecting error message text
	@Feature("Login user types")
	@Description("Negative Login to site with various user password combinations")
	@Severity(SeverityLevel.MINOR)
	@Test (dataProvider="getFailData", description = "negative login testing")
	public void TC01_nagative_user_password_testing(String user, String password, String expected) {
		lp.login(user, password);
		String actual = lp.getErrorMsg();
		//expected = expected; 
		Assert.assertEquals(actual,expected);   
	}

		
// logins that succeed - test type of user
	
	@Feature("Login user types")
	@Story("positive Login with standard username {0} with and password {1}")
	@Description("Login to site with Standard_user user and Secret_sause password combination")
	
	@Test (description = "Standard_user , Secret_sause - good password")
	public void TC06_Standard_User() {
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
	
	@Feature("Login user types")
	@Story("positive Login with Problem_user username {0} with and password {1}")
	@Description("Login to site with Problem_user user and Secret_sause password combination")
	
	@Test (description = "Problem_user , Secret_sause - bad image")
	public void TC07_Problem_User() {
		user = "problem_user";
		password = "secret_sauce";
		lp.login(user, password);
		
		String product = "Sauce Labs Backpack";
		String actual = pp.getProductImgSrc(product);
		String expected = "https://www.saucedemo.com/static/media/sl-404.168b1cce.jpg";
		Assert.assertEquals(actual,expected);  
		pp.chooseFromSideMenu("LOGOUT");
	}
	
	@Feature("Login user types")
	@Story("positive Login with performance_glitch_user username {0} with and password {1}")
	@Description("Login to site with performance_glitch_user user and Secret_sause password combination")
	
	
	@Test (description = "performance_glitch_user , Secret_sause - good password")
	public void TC08_performance_glitch_user() {
		user = "performance_glitch_user";
		password = "secret_sauce";
		double startTime = lp.getTime();
		lp.login(user, password);
		
		double delayedLoadTime = pp.pageLoadTime() - startTime;
		System.out.println("regular load time = : "  + loadTime);
		System.out.println("glitch load time = : "  + delayedLoadTime); 
		Assert.assertTrue(delayedLoadTime > 2 * loadTime ); 
		//Assert.assertTrue(pp.isProductsPage()); 
		pp.chooseFromSideMenu("LOGOUT");  
	}
	
	
	@AfterClass
	public void tearDown() throws InterruptedException	{
		Thread.sleep(3000);
		driver.quit();
		System.out.println("End");
	}



}





