package tests;

import org.testng.Assert;
import org.testng.annotations.Test;


import pageObjects.ItemPage;
import pageObjects.ProductsPage;

//Item pae tests:
//Add to cart button
//Remove button
//Price matches price from products page
//Title, text and photo match 
//Back to products button

public class ItemPageTests extends BaseTest {
	
	//@BeforeClass
	@Test
	public void b11_move_to_item_page(){
		String product = "Sauce Labs Bike Light";
		ProductsPage pp = new ProductsPage(driver);
		ItemPage ip = new ItemPage(driver);
		pp.goToItemPage(product);
		Assert.assertTrue(ip.isOnItemPage());
	
	}

	
	@Test (description = "assert on  item page")
	public void tc01_Assert_on_item_page() {
		ItemPage ip = new ItemPage(driver);
		Assert.assertTrue(ip.isOnItemPage()); 
	}

	
	@Test (description = "Add to cart Button - positive")
	public void tc02_Add_to_cart_button_pos() {
		ItemPage ip = new ItemPage(driver);
		String actual = ip.addProduct();
		String expected = "product added";
		Assert.assertEquals(actual, expected);
		
	}
	
			
	@Test (description = "Add to cart Button - Negative") 
	public void tc03_Add_to_cart_button_already_added() {
		ItemPage ip = new ItemPage(driver);
		String actual = ip.addProduct();
		String expected = "Add to Cart option - not active";
		Assert.assertEquals(actual, expected);
	}
	
	@Test (description = "Remove product - positive")
	public void tc04_Remove_product() {
		ItemPage ip = new ItemPage(driver);
		String actual = ip.removeProduct();
		String expected = "product removed";
		Assert.assertEquals(actual, expected);
	}
	
	@Test (description = "Remove product - negative")
	public void tc05_Remove_product_not_in_cart() {
		ItemPage ip = new ItemPage(driver);
		String actual = ip.removeProduct();
		String expected = "Remove option - not active";
		Assert.assertEquals(actual, expected);
	}
	
	
	@Test (description = "verify product price")
	public void tc06_Product_price() {
		ItemPage ip = new ItemPage(driver);
		//"Sauce Labs Bike Light"	
		double actual = ip.getProductPrice();
		double expected = 9.99;
		Assert.assertEquals(actual, expected);

	}
	
	@Test (description = "verify product image")
	public void tc07_Product_image() {
		ItemPage ip = new ItemPage(driver);
		//"Sauce Labs Bike Light"	
		String actual = ip.getProductImgSrc();
		String expected = "https://www.saucedemo.com/static/media/bike-light-1200x1500.a0c9caae.jpg";
		Assert.assertEquals(actual, expected);

	}
	
	@Test (description = "return back to products page")
	
	public void tc08_back_to_products_page() {
		ProductsPage pp = new ProductsPage(driver);
		ItemPage ip = new ItemPage(driver);
		ip.backToMain();
		Assert.assertTrue(pp.isProductsPage());
		
	}
	
	
}
