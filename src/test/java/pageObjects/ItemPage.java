package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ItemPage extends MenuLayer{

	public ItemPage(WebDriver driver) {
		super(driver);
	}

	//main functions
	@FindBy(css=".inventory_item_name")
	WebElement  itemName;

	@FindBy (css="[data-test='back-to-products']")
	WebElement backtoProductsBtn;
	

	@FindBy (css=".inventory_details_name")
	WebElement productNameLbl;
	
	@FindBy (css=".inventory_details_img")
	WebElement productImg;
	
	@FindBy (css=".inventory_details_img [src]")
	WebElement productImgSrc;
	
	@FindBy (css=".inventory_details_price")
	WebElement productPrice;
	
//	inventory_details
//	
//	
//	inventory_details_desc_container
//	


	//verify you are on Item page

	public boolean isOnItemPage() {

		if (backtoProductsBtn.isDisplayed()) {
			return true;
		}
		else {
			return false;
		}

	}
	
	public double getProductPrice() {

		String priceTxt = getText(productPrice).trim().replace("$", "");;
		double	price  = Double.parseDouble(priceTxt);
		return price;
	}
	
	public String getProductImgSrc() {
	
		return	productImg.getAttribute("src");
			
	}

		
}
