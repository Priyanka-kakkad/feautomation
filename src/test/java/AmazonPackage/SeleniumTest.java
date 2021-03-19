package AmazonPackage;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

class SeleniumTest {
	
	private static final String url = "https://www.amazon.in/";
	private static final String searchKey = "iphone";
	
	public WebDriver driver;
	public Properties obj;
		
	@BeforeSuite
	public void beforeSuite() throws IOException {
		System.setProperty("webdriver.chrome.driver", "//Users//user//Downloads//chromedriver");
		driver = new ChromeDriver();

		
		obj = new Properties();					
	    FileInputStream objfile = new FileInputStream("//Users//user//fe-automation//src//main//resources//ObjectRepository.properties");									
	    obj.load(objfile);		
	    
	    
	    driver.get(url);
	}
	
	@AfterSuite()
	public void afterSuite(){
		driver.quit();
	}

	
	@Test
	public void printProductNameAndPriceTestCase(){
		driver.findElement(By.id(obj.getProperty("SearchTextBox"))).sendKeys(searchKey);
		driver.findElement(By.id(obj.getProperty("SearchTextBox"))).sendKeys(Keys.ENTER);
		
		final WebDriverWait wait = new WebDriverWait(driver,5);
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.className(obj.getProperty("Results")))); 
		
		//Fetch all the Results
		final WebElement number_of_products = driver.findElement(
				By.className(obj.getProperty("Results"))); 
		
		//Fetch all the Product Name from the fetched results
		final List <WebElement> list_of_products = number_of_products.findElements(
				By.xpath(obj.getProperty("ProductName"))); 
		
		//Fetch all the Product Price from the fetched results
		final List <WebElement> list_of_products_price = number_of_products.findElements(
				By.xpath(obj.getProperty("ProductPrice"))); 
		
		
		for (int i=0; i < list_of_products.size(); i++) {
			final String product_name = list_of_products.get(i).getText(); //Iterate and fetch product name
			//Convert to Double
			final double double_product_price = Double.parseDouble(
					list_of_products_price.get(i).getText()
					.replaceAll("[^0-9.]", "")
					); 
			System.out.println(product_name + " -> " + double_product_price);
			
		}
	}
}

