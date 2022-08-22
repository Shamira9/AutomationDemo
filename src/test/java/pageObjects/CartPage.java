package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class CartPage extends MenuLayer {

	public CartPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(css=".inventory_item_name")
	private List<WebElement> productsList;


	@FindBy (css=".checkout_button")
	WebElement checkoutBtn;
	@FindBy (css="#continue-shopping")
	WebElement contShoppingBtn;

	public boolean isOnProductsList(String product) {
		boolean found = false;
		//System.out.println("isOnProductsList CartPage routine " + productsList.size() );
		for (WebElement el : productsList) {
//			System.out.println(product + "  " + el.getText());
			//if (el.getText().toLowerCase().equals(product)) {
			if (el.getText().equals(product)) {
				found = true;
				break;
			}
		}
		if (found) {
			System.out.println("Chosen product found in cart");	
		}
		else {
			System.out.println("Chosen product not found in cart");
		}
		return found;
	}
	

	//receives product's text as search term
	@Step("Remove product from cart")
	public void removeProductFromCart(String productName) {
		for (WebElement el : productsList) {
			if (el.getText().equalsIgnoreCase(productName)) {
				
				WebElement parentEl = el.findElement(By.xpath("./ancestor::div[@class='cart_item_label']")); 
				WebElement priceEl = parentEl.findElement(By.xpath(".//div[@class='item_pricebar']"));
				WebElement buttonEl = priceEl.findElement(By.cssSelector(".cart_button"));
				//WebElement buttonEl = parentEl.findElement(By.xpath(".//button[@class='cart_button']"));
				click(buttonEl);
				//click(el.findElement(By.cssSelector(".cart_button")));
				break;
			}

		}
	}

	//receives product's text as search term
	public void removeFirstProduct() throws InterruptedException {
		System.out.println("removeFirstProduct CartPage routine");
		if (shoppingCartBadge.isDisplayed()) {
			click(cartBtn);
			Thread.sleep(1000);
		}

	}

	public void clickCheckout() {
		click(checkoutBtn);
	}

	public void clickContinueShopping() {
		click(contShoppingBtn);
	}
	public int howManyProductsInCart() {

		return productsInCart();
		
	}
}


