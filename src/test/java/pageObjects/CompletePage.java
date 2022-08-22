package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CompletePage extends MenuLayer {

	public CompletePage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy (css="#checkout_complete_container > h2")
	WebElement checkoutComplete;
	
	@FindBy (css="#back-to-products")
	WebElement backToMainBtn;
	
	
	public void backToMain( ) {
		click(backToMainBtn);  
		}
		
	public void verifyComplete() {
		String verifyText = checkoutComplete.getText();
		System.out.println(verifyText);
	}
	
	public boolean isCompletePage() {
		String ExpectedTitle = "Checkout: Complete!";
		return isOnPage(ExpectedTitle);
	
	}
	
		
}


