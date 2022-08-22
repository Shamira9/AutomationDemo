package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Checkout2Page extends MenuLayer {

	public Checkout2Page(WebDriver driver) {
		super(driver);
	}

	@FindBy(css="#finish")
	private WebElement finishBtn;

	@FindBy(css="#cancel")
	private WebElement cancelBtn;

	
	public void clickFinish() {
		click(finishBtn);
	}

	public void clickCancel() {
		click(cancelBtn);
	}

	public boolean isCheckout2Page() {
		String ExpectedTitle = "Checkout: Overview";
		return isOnPage(ExpectedTitle);

	}


}


