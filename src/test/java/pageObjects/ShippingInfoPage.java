package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class ShippingInfoPage extends MenuLayer {

	public ShippingInfoPage(WebDriver driver) {
		super(driver);
	}

	//shipping information fields
	@FindBy (css="#first-name")
	WebElement firstNameFld;
	@FindBy (css="#last-name")
	WebElement laseNameFld;
	@FindBy (css="#postal-code")
	WebElement zipCodeFld;
	
	//buttons
	@FindBy (css="#continue")
	WebElement continueBtn;
	@FindBy (css="#cancel")
	WebElement cancelBtn;
	
	@Step("Fill in shipping information in form")
	public void fillShippingInformation(String firstName, String lastName, String zipCode) {
		fillText(firstNameFld,firstName);
		fillText(laseNameFld,lastName);
		fillText(zipCodeFld,zipCode);
	}

	public void clickContinue() {
		click(continueBtn);
	}

	public void clickCancel() {
		click(cancelBtn);
	}


	public boolean isShippingPage() {
		String ExpectedTitle = "Checkout: Your Information";
		return isOnPage(ExpectedTitle);
}
}

