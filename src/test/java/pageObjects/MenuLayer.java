package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MenuLayer extends BasePage{

	public MenuLayer(WebDriver driver) {
		super(driver); 
	}
	
	//shopping cart top right
	@FindBy (css=".shopping_cart_link")
	WebElement shoppingCartLink;
	@FindBy (css=".shopping_cart_badge")
	WebElement shoppingCartBadge;
	@FindBy (css=".cart_button")
	WebElement cartBtn;
	
	//add remover buttons
	@FindBy (css=".btn_primary.btn_inventory")
	WebElement addToCartBtn;
	@FindBy (css=".btn_secondary.btn_inventory")
	WebElement removetBtn;
	@FindBy (css=".btn_inventory")
	WebElement actionBtn;

	

	//side-bar menu options
	@FindBy (css="#react-burger-menu-btn")
	WebElement sideMenuBtn;
	@FindBy (css="#inventory_sidebar_link")
	WebElement SidebarLnk;
	@FindBy (css="#about_sidebar_link")
	WebElement aboutLnk;
	@FindBy (css="#logout_sidebar_link")
	WebElement logoutLnk;
	@FindBy (css="#reset_sidebar_link")
	WebElement resetLnk;
	@FindBy (css=".bm-cross-button")
	WebElement closeSideMenuBtn;
	
	 

	//bottom of page - menu options
	@FindBy (css="#back-to-products")
	WebElement backBtn;
	@FindBy (css=".social_twitter")
	WebElement twitterLnk;
	@FindBy (css=".social_facebook")
	WebElement facebookLnk;
	@FindBy (css=".social_linkedin")
	WebElement linkedinLnk;

	//page title
	@FindBy (css=".title")
	WebElement pageTitle;

	
	String resultString = "";
	
	
	public void goToShoppingCart() {
		if (shoppingCartLink.isDisplayed()) {
		click(shoppingCartLink);
		}
		
	}

	//why is shoppingCartBadge.isDisplayed() failing??
	public int productsInCart() {
		if (elementPresent(shoppingCartBadge)) {
			return Integer.parseInt(getText(shoppingCartBadge));
		}
		else {
			return 0;
		}
	}
	
	//add/remove functions
	
	public String addProduct() {
						
		String buttonAction = getText(actionBtn);
		String result = "";
		
		if(buttonAction.equalsIgnoreCase("Add to cart")) {
			click(actionBtn);
			result = "product added";
		}
		else if(buttonAction.equalsIgnoreCase("Remove")) { 
			result = "Add to Cart option - not active";
		}
		return result;

	}

	//remover product from cart using REMOVER FROM CART button
	public String removeProduct() {
		
		String result = "";
		String buttonAction = getText(actionBtn);
		
		if(buttonAction.equalsIgnoreCase("Remove")) {
			click(actionBtn);
			result = "product removed";
		}
		else if(buttonAction.equalsIgnoreCase("Add to cart")) { 
			result = "Remove option - not active";
		}
		return result;
	}
	
	// Is this the correct page?
	public boolean isOnPage(String ExpectedTitle) {
		String thePageTitle =  getText(pageTitle);
		if (thePageTitle.equalsIgnoreCase(ExpectedTitle)) {
			return true;
		}
		else {
			return false;
		}

	}
	

	//use left menu react-burger-menu-btn
		public void chooseFromSideMenu(String choice) {
			resultString = "";
			click(sideMenuBtn); 
			waitForVisibilityOf(closeSideMenuBtn);
			sleep(500);

			switch(choice) {
			case "All ITEMS":
				click(SidebarLnk);
				break;

			case "ABOUT":
				click(aboutLnk); 
				break;

			case "LOGOUT":
				click(logoutLnk); 
				break;

			case "RESET APP STATE":
				click(resetLnk);
				click(closeSideMenuBtn);
				break; 

			default:
				resultString =   choice + " not found on side bar menu";
				click(closeSideMenuBtn);
			}
		}
		
		public String getResultString() {
			return this.resultString;
		}
		
		public void closeSideMenu() {
			click(closeSideMenuBtn);
			sleep(1000);
		}
		

	//bottom of page options 
		
		public void backToMain() {
			click(backBtn);
		}

		public void twitter() {
			click(twitterLnk);
		}

		public void facebook() {
			click(facebookLnk);
		}

		public void linkedin() {
			click(linkedinLnk);
		}

}
