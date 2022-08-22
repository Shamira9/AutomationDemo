package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import io.qameta.allure.Step;

public class ProductsPage extends MenuLayer {

	public ProductsPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(css=".inventory_item_name")
	private List<WebElement> productsList;

	@FindBy(css=".inventory_item_description")
	private List<WebElement> productDiscriptionBoxList;
	
	@FindBy(css=".inventory_item_img img")
	private List<WebElement> productsImgList;

	@FindBy (css=".inventory_item_name")
	WebElement itenNameTxt;

	@FindBy (css=".product_sort_container")
	WebElement productSortContainer;
	
	@FindBy (css=".inventory_item_price")
	WebElement productPrice;
	
		
	@FindBy (css=".btn_inventory")
	WebElement actionBtn;
	
	@FindBy (css=".btn_primary.btn_inventory")
	WebElement addBtn;
	
	@FindBy (css=".btn_secondary.btn_inventory")
	WebElement removeBtn;
	
			
	//@FindBy (css=".header_secondary_container .title")
	@FindBy (css=".peek")
	WebElement pagePeekLogo;
	
	@FindBy (css=".inventory_item_img img")
	WebElement productImg;
	
	@FindBy (css=".inventory_item_img [src]")
	WebElement productImgSrc;
	
	
	public void getProductsList() {
		for (WebElement el : productsList) {
			System.out.println(el);
		}
	}

	public void selectItemFromList(int itemIndexNo) {
	System.out.println("inventory items on this page: " + productsList.size());
	int i = 1;
	for (WebElement el : productsList) {
		if (i == itemIndexNo ) {
			el.click();
			break;
		}
		i++;
	}
	}

	public boolean isOnProductsList(String product) {

		boolean found = false;

		for (WebElement el : productsList) {
			if (el.getText().toLowerCase().equalsIgnoreCase(product)) {
				found = true;
				break;
			}
		}
		return found;
	}
	
	public boolean isProductAdded(String product) {

		boolean added = false;

		for (WebElement el : productsList) {
			if (el.getText().toLowerCase().equalsIgnoreCase(product)) {
				// addBtn  .btn_primary.btn_inventory
				String actionBtnTxt = getText(el.findElement(By.cssSelector(".btn_inventory")));
				//if (addBtn.getText().toLowerCase().equalsIgnoreCase("Add to cart")) {
				if (actionBtnTxt.toLowerCase().equalsIgnoreCase("Add to cart")) {
					added = false;
					break;
			}
				else {
					// .btn_inventory = Remove
					if (actionBtnTxt.toLowerCase().equalsIgnoreCase("Remove")) {
						added = true;
					}
				}
			}
		}
		return added;
	}
	
	public double getProductPrice(String product) {

		double price = 0;

		for (WebElement el : productsList) {
			if (el.getText().toLowerCase().equalsIgnoreCase(product)) {
				WebElement parentEl = el.findElement(By.xpath("./ancestor::div[@class='inventory_item_description']")); 
				WebElement priceEl = parentEl.findElement(By.xpath(".//div[@class='inventory_item_price']"));
				String priceTxt = getText(priceEl).trim().replace("$", "");;
				price  = Double.parseDouble(priceTxt);
				break;
			}
		}
		return price;
	}
	
	public String getProductImgSrc(String product) {

		String productImgSc = "";

		for (WebElement el : productsImgList) {
			if (el.getAttribute("alt").toLowerCase().equalsIgnoreCase(product)) {
				productImgSc = el.getAttribute("src");
				break;
			}
			else {
				System.out.println("could not find " + product );
			}
		}
		return productImgSc;
	}
	
	
	//receives product's text as search term

	@Step("Add product to cart")
	public String addProduct(String product) {

		String itemName;
		String result = "";
		
		if (this.isOnProductsList(product)) {
			System.out.println(product + " on product list");
			
			for (WebElement el : productDiscriptionBoxList) {
				itemName = getText(el.findElement(By.cssSelector(".inventory_item_name")));
				
				if (itemName.equals(product)) {
					String buttonAction = getText(el.findElement(By.cssSelector(".btn_inventory")));
					if(buttonAction.equalsIgnoreCase("Add to cart")) {
						click(el.findElement(By.cssSelector(".btn_primary.btn_inventory")));
						System.out.println(product + " --> product added");
						result = ("product added");
					}
					else if(buttonAction.equalsIgnoreCase("Remove")) { 
						result = ("product already in cart");
					}
					break;
					}
					
			}
		}
		else {
			result = ("product not found on page"); 
		}
		
		return result;
	}

	//receives product's text as search term
	@Step("Remove product from cart")
	public String removeProduct(String product) {
		String result = "";
		String itemName;
		
		if (this.isOnProductsList(product)) {
			System.out.println(product + " on product list");
			
			for (WebElement el : productDiscriptionBoxList) {
				itemName = getText(el.findElement(By.cssSelector(".inventory_item_name")));
				
				if (itemName.equals(product)) {
					String buttonAction = getText(el.findElement(By.cssSelector(".btn_inventory")));
					if(buttonAction.equalsIgnoreCase("Remove")) {
						click(el.findElement(By.cssSelector(".btn_secondary.btn_inventory")));
						System.out.println(product + " --> product removed");
						result = ("product removed");
					}
					else if(buttonAction.equalsIgnoreCase("Add to cart")) { 
						result = ("product not in cart");
					}
					break;
					}
					
			}
		}
		else {
			result = ("product not found on page"); 
		}
		
		return result;
	}

	public void sortProductsByValue(String sort)  { 
		
		Select drpSort = new Select(productSortContainer);
		drpSort.selectByValue(sort);
		sleep(1000);

	}

	public void sortProductsByText(String sort)  { 
		Select drpSort = new Select(productSortContainer);
		drpSort.selectByVisibleText(sort);
	}

	public void sortProductsByIndex(int sort)  { 
		Select drpSort = new Select(productSortContainer);
		drpSort.selectByIndex(sort);
	}
	
	public String getSortMode() {
		Select drpSort = new Select(productSortContainer);
		return drpSort.getFirstSelectedOption().getText();
	}
	

	public void verifySortName() {
		listAndPrintElements(".inventory_item_name");
	}

	public void verifySortPrice() {
		listAndPrintElements(".inventory_item_price");
	}

	//	public void goToItemPage(int itemIndexNo) {
	//		driver.findElement(By.cssSelector(".inventory_item_name:nth-child(" + itemIndexNo  + ")")).click();
	//	}

	public void goToItemPage(int itemIndexNo) {
		
		int i = 1;	
		for (WebElement el : productsList) {
			if (i == itemIndexNo ) {
				el.click();
				break;
			}
		}	
	}
	
	public void goToItemPage(String product) {
		 
		for (WebElement el : productsList) {
			if (el.getText().toLowerCase().equalsIgnoreCase(product)) {
				el.click();
				break;
			}
		}
	}
	
	//validations
	
	public boolean isProductsPage() {
		String ExpectedTitle = "PRODUCTS";
		return isOnPage(ExpectedTitle);

	}
	
	public double pageLoadTime()	{
		return this.pageLoadTime(pagePeekLogo);
	}
	
}
