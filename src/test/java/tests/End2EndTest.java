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

import io.qameta.allure.Description;
import io.qameta.allure.Feature;

import io.qameta.allure.Story;
import pageObjects.CartPage;
import pageObjects.Checkout2Page;
import pageObjects.CheckoutPage;
import pageObjects.CompletePage;
import pageObjects.ItemPage;
//import pageObjects.BasePage;
import pageObjects.LoginPage;
import pageObjects.ProductsPage;

public class End2EndTest {

	//updated
	
	// login to the site
	//	add a product via products page
	//	add a product via item page
	//	return to products page
	//	go to cart
	//	checkout


	//set variables
	String user; 
	String password;
	double loadTime;
	private WebDriver driver;
	private LoginPage lp;
	private ProductsPage pp;
	private ItemPage ip;
	private CartPage cp;
	private CheckoutPage chp;
	private Checkout2Page chp2;
	private CompletePage comp;
	//set testNG variables
	
//	Allure annotations
//	@Epic, @Features and  @Stories like in agile 
//	@Epic
//	@Features
//	@Stories/@Story - can be a test
//	@Severity(SeverityLevel.BLOCKER)
//	@Severity BLOCKER, CRITICAL, NORMAL, MINOR, TRIVIAL.
//	@Issue
//	@Step ("verify sign in with username {0} and password {1}") 
//	@STEP annotation appears in page level, where the methods are
//	@Parameters({ "browserType", "appURL" }) - such as in BEFORECLASS
//	@Attachment  - an annotation for a method that creates and attachment - such as screenshot
//	@Link

	@Description("setup driver, site and login")
	@BeforeClass 
	public void setup(){
		System.setProperty("webdriver.chrome.driver", "C:\\automation\\drivers\\chromedriver.exe");
		driver = new ChromeDriver();

		driver.manage().window().maximize();
		driver.get("https://www.saucedemo.com/");
		//all tests on this page will use the LoginPage
		lp = new LoginPage(driver);
		pp = new ProductsPage(driver);
		ip = new ItemPage(driver);
		cp = new CartPage(driver);
		chp = new CheckoutPage(driver);
		chp2 = new Checkout2Page(driver);
		comp = new CompletePage(driver);
			

	}


	@Feature("Login to site")
	@Story("Login with standard username {0} with and password {1}")
	@Description("Login to site")
	//@Features("  ")
	@Test (description = "Standard_user ", enabled = true)
	public void TC01_Standard_User() {
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
		//pp.chooseFromSideMenu("LOGOUT");
	}
	
	@Description("Add products to cart")
	@Feature("Button fauncionality")
	@Test (description = "add products to cart" , enabled = true)
	public void tc02_add_products_from_products() {
		pp.addProduct("Sauce Labs Onesie");
		pp.addProduct("Sauce Labs Bike Light");
		int actual = pp.productsInCart();
		int expected = 2 ;
		Assert.assertEquals(actual, expected);
		
	}
	
	@Description("Count products in cart")
	@Test (description = "count products in cart - 2" , enabled = true)
	public void tc03_Count_product_2() {
		int actual = pp.productsInCart();
		int expected = 2 ;
		Assert.assertEquals(actual, expected);
		
	}
	
	@Description("Add items to cart from item page")
	@Feature("Button fauncionality")
	@Test (description = "add item from item page" , enabled = true)
	public void tc04_add_item_from_item_page() {
		
		String product = "Sauce Labs Fleece Jacket";
		System.out.println("product name: = " + product);
		pp.goToItemPage(product);
		ip.addProduct();
		int actual = ip.productsInCart();
		int expected = 3 ;
		Assert.assertEquals(actual, expected);
	
	}
	
	@Description("Click go to cart button")
	@Feature("Button fauncionality")
	@Test (description = "go to cart" , enabled = true)
	public void tc05_go_to_cart() {
		
		ip.goToShoppingCart();
		Assert.assertTrue(cp.isOnPage("Your Cart"));
	
	}

	@Description("Remove items from cart")
	@Feature("Button fauncionality")
	@Test (description = "remove item from cart " , enabled = true)
	public void tc06_remove_item_from_cart() {
		

		String product = "Sauce Labs Fleece Jacket";
		cp.removeProductFromCart(product);
		Assert.assertFalse(cp.isOnProductsList(product));
	}
	
	@Description("Click continue shopping button")
	@Feature("Button fauncionality")
	@Test (description = "continue shopping " , enabled = true)
	public void tc07_continue_shopping() {
		
		cp.clickContinueShopping();
		Assert.assertTrue(pp.isProductsPage());
		cp.sleep(3000);
		
		String product = "Sauce Labs Fleece Jacket";
		pp.addProduct(product);
		pp.goToShoppingCart();
		
	
	}
	
	@Description("Click checkout button")
	@Feature("Button fauncionality")
	@Test (description = "checkout ", enabled = true)
	public void tc08_checkout() {
		
		cp.clickCheckout();
		Assert.assertTrue(chp.isCheckoutPage());
					
	}
	
	@Description("Fill out checkout form")
	@Feature("Form fauncionality")
	@Test (description = "checkout - fill form ", enabled = true)
	public void tc09_checkout() {
		String first = "first";
		String last = "last";
		String post = "12240";
		
		chp.fillCheckoutForm(first, last, post);
			
		String expected = "first last 12240";
		String actual = chp.getFirst() + " " + chp.getLast() + " " + chp.getPost();
		Assert.assertEquals(actual, expected);
					
	}
	@Description("fill out checkout stge two")
	@Feature("Button fauncionality")
	@Test (description = "checkout ", enabled = true)
	public void tc10_checkout() {
		
		chp.clickContinue();
		Assert.assertTrue(chp2.isCheckout2Page());
	}
	
	@Description("Click finish button")
	@Feature("Button fauncionality")
	@Test (description = "finish ", enabled = true)
	public void tc11_finish_checkout() {
		chp2.clickFinish();
		Assert.assertTrue(comp.isCompletePage());
						
	}
	
	@Description("Click back home button")
	@Feature("Button fauncionality")
	@Test (description = "back home ", enabled = true)
	public void tc12_confirmation_back_to_main() {
		
		comp.backToMain();
		Assert.assertTrue(pp.isProductsPage());
	}
	
	@Description("Take screen shot in case of failed test")
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
	@Description("Close browser")
	@AfterClass
	public void tearDown() throws InterruptedException	{
		Thread.sleep(1000);
		driver.quit();
		System.out.println("End");
	}



}





