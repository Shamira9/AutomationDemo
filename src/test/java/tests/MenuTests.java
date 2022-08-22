package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.CartPage;
import pageObjects.ItemPage;
import pageObjects.LoginPage;
import pageObjects.ProductsPage;

//Menu Layer Tests:
//Go to cart  
//count cart items� how many items
//Use menu options 
//	All Items
//	About
//	Reset App State
//	Logout
//	negative - invalid option
//Not in scope �
//	Media Links :Facebook, Twitter etc.
//	Add/Remove/Back buttons tested on Items nad Products page
 


public class MenuTests extends BaseTest {
	
	@Test (description = "go to cart page")
	public void tc01_go_to_cart_page() {
		ProductsPage pp = new ProductsPage(driver);
		CartPage cp = new CartPage(driver);
		pp.goToShoppingCart();
		Assert.assertTrue(cp.isOnPage("Your Cart")); 
		cp.clickContinueShopping();
	}

	
	@Test (description = "count products in cart - none")
	public void tc02_Count_product_0() {
		ProductsPage pp = new ProductsPage(driver);
		int actual = pp.productsInCart();
		int expected = 0 ;
		Assert.assertEquals(actual, expected);
		
	}
	
	@Test (description = "count products in cart - 2")
	public void tc03_Count_product_2() {
		ProductsPage pp = new ProductsPage(driver);
		pp.addProduct("Sauce Labs Onesie");
		pp.addProduct("Sauce Labs Bike Light");
		int actual = pp.productsInCart();
		int expected = 2 ;
		Assert.assertEquals(actual, expected);
		
	}
			
	@Test (description = "Menu option   - All items") 
	public void tc04_Menu_all_items() {
		ProductsPage pp = new ProductsPage(driver);
		ItemPage ip = new ItemPage(driver);
		pp.goToItemPage("Sauce Labs Onesie");
		ip.chooseFromSideMenu("All ITEMS");
		Assert.assertTrue(pp.isProductsPage());
		
	}
	
	@Test (description = "Menu option   - About", enabled = true) 
	public void tc05_Menu_About() {
		ProductsPage pp = new ProductsPage(driver);
		String productUrl = driver.getCurrentUrl();
		pp.chooseFromSideMenu("ABOUT");
		pp.sleep(3000);
		String actual = driver.getCurrentUrl();
		String expected = "https://saucelabs.com/";
		Assert.assertEquals(actual, expected); 
		
		driver.get(productUrl);
		pp.sleep(1000);
		
	}
	
	@Test (description = "Menu option   - Reset app") 
	public void tc06_Menu_Reset_app() {
		ProductsPage pp = new ProductsPage(driver);
		String beforeUrl = driver.getCurrentUrl();
		pp.chooseFromSideMenu("RESET APP STATE");
		String actual = driver.getCurrentUrl();
		String expected = beforeUrl;
		Assert.assertEquals(actual, expected); 
				
	}
	
	@Test (description = "Menu option  - invalid option") 
	public void tc07_Menu_invalid_option() {
		ProductsPage pp = new ProductsPage(driver);
		pp.chooseFromSideMenu("InvalidOption");
		
		String actual = pp.getResultString();
		String expected = "InvalidOption not found on side bar menu";
		Assert.assertEquals(actual, expected); 
				
	}
	
	@Test (description = "Menu option   - Logout") 
	public void tc08_Menu_Logout() {
		ProductsPage pp = new ProductsPage(driver);
		
		pp.chooseFromSideMenu("LOGOUT");
		String actual = driver.getCurrentUrl();
		String expected = "https://www.saucedemo.com/";
		Assert.assertEquals(actual, expected); 
		LoginPage lp = new LoginPage(driver);
		lp.sleep(2000);
		String user = "standard_user";
		String password = "secret_sauce";
		lp.login(user, password);
		
				
	}
	
	
	
	
}
