package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import io.qameta.allure.Step;

public class LoginPage extends BasePage {

	public LoginPage(WebDriver driver) {
		super(driver); 
	}

	@FindBy(css="#user-name")
	private WebElement userNameFld;
	@FindBy(css="#password")
	private WebElement passwordFld;
	@FindBy(css="#login-button")
	private WebElement loginBtn;

	//@FindBy(css=".error-message-container > h3")
	@FindBy(css=".error-message-container  h3")
	private WebElement errorMsgTxt;

	@Step("Loging to site")
	public void login(String user,String password) {
		fillText(userNameFld,user);
		fillText(passwordFld, password);
		click(loginBtn);
		sleep(1000);

	}

//	public void login(String user,String password) {
//		userNameFld.clear();
//		passwordFld.clear();
//		System.out.println("user = " + getValue(userNameFld));
//		System.out.println("password = " + getValue(passwordFld));
//		fillText(userNameFld,user);
//		fillText(passwordFld, password);
//		System.out.println("user sent :" + user);
//		System.out.println("Passowrd sent " + password);
//		System.out.println("user = " + getValue(userNameFld));
//		System.out.println("password = " + getValue(passwordFld));
//		
//		click(loginBtn);
//		sleep(1000);
//
//	}

	public String getErrorMsg() {
		String errorMsg = getText(errorMsgTxt);
		return errorMsg;
	}

	public boolean isLoginPage() {
		if (loginBtn.isDisplayed()) {
			return true;
		}
		else {
			return false;
		}
	}
}

