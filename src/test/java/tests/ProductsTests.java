package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.ItemPage;
import pageObjects.ProductsPage;

//Test products page:
//		Add to Cart button
//		Remove from Cart Button
// 		Verify price
//		Sort items by drop-down: names a-z , price high to low
//		Select an item and move to item page. 
//		Receive title from item and verify text.
//		Receive text from item and verify text.
//		


public class ProductsTests extends BaseTest {

	@Test (description = "assert on proructs page")
	public void tc01_On_Products_page() {
		
		ProductsPage pp = new ProductsPage(driver);
		Assert.assertTrue(pp.isProductsPage()); 

	}
	
	@Test (description = "Add to cart Button - positive")
	public void tc02_Add_to_cart_button_pos() {
		
		String product = "Sauce Labs Onesie";
		ProductsPage pp = new ProductsPage(driver);
		String actual = pp.addProduct(product);
		String expected = "product added";
		Assert.assertEquals(actual, expected);
		
	}
	
	@Test (description = "Add to cart Button - Negative1")
	public void tc03_Add_to_cart_button_not_found() {
		
		String product = "No such item";
		ProductsPage pp = new ProductsPage(driver);
		String actual = pp.addProduct(product);
		String expected = "product not found on page";
		Assert.assertEquals(actual, expected);
	}
	
	@Test (description = "Add to cart Button - Negative2") 
	public void tc04_Add_to_cart_button_already_added() {
		
		String product = "Sauce Labs Onesie";
		ProductsPage pp = new ProductsPage(driver);
		String actual = pp.addProduct(product);
		String expected = "product already in cart";
		Assert.assertEquals(actual, expected);
	}
	
	@Test (description = "Remove product - positive")
	public void tc05_Remove_product() {
		
		String product = "Sauce Labs Onesie";
		ProductsPage pp = new ProductsPage(driver);
		String actual = pp.removeProduct(product);
		String expected = "product removed";
		Assert.assertEquals(actual, expected);
	}
	
	@Test (description = "Remove product - neg 1 ")
	public void tc06_Remove_product_not_found() {
		
		String product = "no such product";
		ProductsPage pp = new ProductsPage(driver);
		String actual = pp.removeProduct(product);
		String expected = "product not found on page";
		Assert.assertEquals(actual, expected);
	}
	
	@Test (description = "Remove product - neg 2")
	public void tc07_Remove_product_not_in_cart() {
		
		String product = "Sauce Labs Onesie";
		ProductsPage pp = new ProductsPage(driver);
		String actual = pp.removeProduct(product);
		String expected = "product not in cart";
		Assert.assertEquals(actual, expected);
	}
	
	
	@Test (description = "verify product price")
	public void tc08_Product_price() {
		
		String product = "Sauce Labs Bike Light";
		ProductsPage pp = new ProductsPage(driver);
		double actual = pp.getProductPrice(product);
		double expected = 9.99;
		Assert.assertEquals(actual, expected);

	}
	@Test (description = "sort items using drop-down - by Z-A")
	public void tc09_sort_a_to_z() {
		
		ProductsPage pp = new ProductsPage(driver);
		pp.sortProductsByText("Name (Z to A)");
		String actual = pp.getSortMode();
		String expected = "Name (Z to A)";
		Assert.assertEquals(actual, expected);

	}
	
	@Test (description = "sort items using dropdown - price high > low")
	public void tc10_sort_price_Dessending() {
		
		ProductsPage pp = new ProductsPage(driver);
		pp.sortProductsByIndex(3);
		String actual = pp.getSortMode();
		String expected = "Price (high to low)";
		Assert.assertEquals(actual, expected);
		
	}
	
	@Test (description = "move to item page")
	public void tc11_move_to_item_page() {
		
		String product = "Sauce Labs Bike Light";
		ProductsPage pp = new ProductsPage(driver);
		pp.goToItemPage(product);
		ItemPage ip = new ItemPage(driver);
		Assert.assertTrue(ip.isOnItemPage()); 
	
	}
	

}
