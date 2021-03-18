package TestPackage;


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

class SeleniumTest {
	
		public static WebDriver driver;
		String url = "https://www.amazon.in/";
		String searchKey = "iphone";
	
		
		@BeforeSuite
		public void beforeSuite()
		{
			System.setProperty("webdriver.chrome.driver", "//Users//user//Downloads//chromedriver");
			driver = new ChromeDriver();

			driver.get(url);
		}
		
		
		@AfterSuite()
		public void afterSuite()
		{
			driver.quit();
		}
	
		
		@Test
		public void testcase1()
		{
			driver.findElement(By.id("twotabsearchtextbox")).sendKeys(searchKey);
			driver.findElement(By.id("twotabsearchtextbox")).sendKeys(Keys.ENTER);
			
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			
			List <WebElement> list_of_products = driver.findElements(By.cssSelector(".a-size-medium.a-color-base.a-text-normal")); //Fetch all the Product Name
			
			List <WebElement> list_of_products_price = driver.findElements(By.className("a-price-whole")); //Fetch all the Product Price
			
			//Use of HashMaop to store Products and their prices (after conversion to Integer)
			String product_name;
			String product_price;
			int int_product_price;
			
			HashMap<String, Integer> map_final_products = new HashMap<String, Integer>();
			
			for (int i=0; i < list_of_products.size(); i++) 
			{
				product_name = list_of_products.get(i).getText(); //Iterate and fetch product name
				product_price = list_of_products_price.get(i).getText(); //Iterate and fetch product price
				product_price = product_price.replaceAll("[^0-9]", ""); //Replace anything other than numbers with space
				int_product_price = Integer.parseInt(product_price); //Convert to Integer
				map_final_products.put(product_name, int_product_price); //Add product and price in HashMap
			}
			
			Set set = map_final_products.entrySet();
			Iterator it = set.iterator();
			while(it.hasNext())
			{
				Map.Entry mp = (Map.Entry)it.next();
				System.out.println(mp.getKey() + " -> " + mp.getValue()); //Print Product name and Price
			}
			
			
			//Reporter.log("Product Name and price fetched from UI and saved in HashMap as:" + map_final_products.toString() + "<br>",true);

		}

}



