package pageObjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

	WebDriver driver;
	Alert alert;
	private Actions a;
	private Select menuObj;
	private WebDriverWait wait;
	

	public BasePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		a = new Actions(driver);
		wait = new WebDriverWait(driver, Duration.ofSeconds(2));


	}

	/**
	 * @return the driver
	 */
	public WebDriver getDriver() {
		return driver;
	}

	/**
	 * @param driver the driver to set
	 */
	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}


	public void fillText(WebElement el, String text) {
		highlightElement(el, "yellow");
		el.clear();
		el.sendKeys(text);
	}

	public void click(WebElement el) {
		highlightElement(el, "yellow");
		el.click();
	}

	public String getText(WebElement el) {
		highlightElement(el, "yellow");
		return el.getText();
	}
	
	public String getValue(WebElement el) {
		highlightElement(el, "yellow");
		return el.getAttribute("value");
	}
	
	public void sleep(long mills) {
		try {
			Thread.sleep(mills);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void alertOK(String text) {
		driver.switchTo().alert().sendKeys(text);
		driver.switchTo().alert().accept();	
	}

//	public List listElements(String CSSterm) {
//
//		List<WebElement> list = driver.findElements(By.cssSelector(CSSterm));
//		return list;
//	}

	public void listAndPrintElements(String CSSterm) {

		List<WebElement> list = driver.findElements(By.cssSelector(CSSterm));

		System.out.println();
		System.out.println("There are " + list.size() +" elements");
		for (WebElement el : list) {
			System.out.println(el.getText());

		}
	}
	
	//mouse 
	public void mouseHover(WebElement el) {
		highlightElement(el, "yellow");
		a.moveToElement(el).build().perform();
	}

	public void mouseClick(WebElement el) {
		highlightElement(el, "yellow");
		a.moveToElement(el).build().perform();
		a.click();
	}

	public void mouseDragAndDrop(WebElement elFrom,WebElement elTo) {
		highlightElement(elFrom, "yellow");
		highlightElement(elTo, "orange");
		a.dragAndDrop(elFrom, elTo).build().perform();
	}

	// Wait
	public void waitForVisibilityOf(WebElement el) {
		wait.until(ExpectedConditions.visibilityOf(el));
	}

	public void waitForInVisibilityOf(WebElement el) {
		wait.until(ExpectedConditions.invisibilityOf(el));
	}

	//	public void waitForPresenceOf(WebElement el) {
	//		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(el));
	//	}
	
	public boolean elementPresent(WebElement el) {
		//isDisplayed ?
		try {
			return (el.isDisplayed());
		} catch (Exception e) {
			
			return false;
		}
				
		}

	//Select from Menu

	public Select setMenu(WebElement menuCSS) {
		menuObj = new Select(menuCSS);
		return menuObj;
	}

	public void setMenu1(WebElement menuCSS) {
		menuObj = new Select(menuCSS);
	}

	public void selectMenuText1(WebElement menuCSS, String option) {
		menuObj = new Select(menuCSS);
		menuObj.selectByVisibleText(option);
	}
	public void selectMenuText(String option) {
		menuObj.selectByVisibleText(option);
	}

	public void selectMenuIndex(int option) {
		menuObj.selectByIndex(option);
	}

	public void selectMenuValue(String option) {
		menuObj.selectByValue(option);
	}

	public void releaseMenu(Select menuObj) {
		menuObj.deselectAll();
	}

	//s


// boolean ddd = x.isDisplay ? true: false 
	/*
	 * Call this method with your element and a color like (red,green,orange etc...)
	 */
	
	protected void highlightElement(WebElement element, String color) {
		// keep the old style to change it back
		String originalStyle = element.getAttribute("style");
		String newStyle = "background-color: yellow; border: 1px solid " + color + ";" + originalStyle;
		JavascriptExecutor js = (JavascriptExecutor) driver;

		// Change the style
		js.executeScript("var tmpArguments = arguments;setTimeout(function () {tmpArguments[0].setAttribute('style', '"
				+ newStyle + "');},0);", element);

		// Change the style back after few miliseconds
		js.executeScript("var tmpArguments = arguments;setTimeout(function () {tmpArguments[0].setAttribute('style', '"
				+ originalStyle + "');},400);", element);

	}
	
	public double pageLoadTime(WebElement el) {
		this.waitForVisibilityOf(el);
		return this.getTime();

	}
	
	public double getTime() {
		return System.currentTimeMillis();
	}
	
	public void goBack() {
	}
	
	
}
