package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class CheckoutPage extends MenuLayer {

	public CheckoutPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(css="#first-name")
	private WebElement firstNameFld;
	
	@FindBy(css="#last-name")
	private WebElement lastNameFld;
	
	@FindBy(css="#postal-code")
	private WebElement postCodeFld;
	
	@FindBy(css=".error-message-container")
	private WebElement errorMsg;

	@FindBy(css="#cancel")
	private WebElement cancelBtn;
	
	@FindBy(css="#continue")
	private WebElement continueBtn;
	
	public void putFirst(String first) {
		this.fillText(firstNameFld, first);
	}
	
	public void putLast(String last) {
		this.fillText(lastNameFld, last);
	}
	
	public void putPostCode(String post) {
		this.fillText(postCodeFld, post);
	}
	
	@Step("Fill out checkout form")
	public void fillCheckoutFormAndContinue(String first, String last, String post) {
		this.putFirst(first);
		this.putLast(last);
		this.putPostCode(post);
		clickContinue();
	}
	@Step("Fill out checkout form")
	public void fillCheckoutForm(String first, String last, String post) {
		this.putFirst(first);
		this.putLast(last);
		this.putPostCode(post);
	}
	
	public String getFirst() {
		return this.getValue(firstNameFld);
	}
	
	public String getLast() {
		return this.getValue(lastNameFld);
		
	}
	
	public String getPost() {
		return this.getValue(postCodeFld);
	}
	
	
	public void clickContinue() {
		click(continueBtn);
	}

	public void clickCancel() {
		click(cancelBtn);
	}

	public boolean isCheckoutPage() {
		String ExpectedTitle = "Checkout: Your Information";
		return isOnPage(ExpectedTitle);

	}

	public String getErrorMsg() {
		String errorMsgTxt = getText(errorMsg);
		return errorMsgTxt;
	}

}


