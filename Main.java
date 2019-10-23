package textInputSubmitJava;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

import java.util.*;
import java.io.*;
import java.net.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities; 
import org.openqa.selenium.htmlunit.HtmlUnitDriver;	

public class Main {

	public static void main(String[] args) {
		
		try {

            // Use Google GeoCoder to get coordinates
        	// might have to revisit URLEncoder function later on
        	// replace "Austin" with text box from front end
        	String address = "Austin";
            URL url = new URL(
                    "https://maps.googleapis.com/maps/api/geocode/json?address="
                            + URLEncoder.encode(address,java.nio.charset.StandardCharsets.UTF_8.toString()) + "&sensor=true&key=AIzaSyARRJsBkisGqJ5_1Vo2QB_Pk2mIMYQVZlw");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/json");
    
            if (con.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + con.getResponseCode());
            }

            // building JSON response
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();
            
//            JSONObject myResponse = new JSONObject(content.toString());
            
//            System.out.println(myResponse);
            String result = content.toString();
//            System.out.println(result);

            JSONObject myResponse = new JSONObject(result.toString());

            
            String testing = myResponse.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").toString();
            String latString = myResponse.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").get("lat").toString();
            String longString = myResponse.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").get("lng").toString();
//            System.out.println(testing);
//            System.out.println(latString+","+longString);
            
            // String humid = myResponse.getJSONObject("currently").getString("humidity");
            // String wind = myResponse.getJSONObject("currently").getString("windSpeed");
            // String precip = myResponse.getJSONObject("currently").getString("precipProbability");

            // Plug in coordinates to get request
            double latitude = Double.parseDouble(latString);
            double longitude = Double.parseDouble(longString);

            // https://www.adt.com/natural-disasters?30.307983936955342,-97.75340139999997,9 --> Austin

            Document doc = Jsoup.connect("https://www.adt.com/natural-disasters?" +latitude+","+longitude+",9").get();
            System.out.println(doc);

            // parse data after getting table populated

        } 
        catch (Exception e) {
            e.printStackTrace();
		}
		
	}
	
	public void submittingForm() throws Exception {		    
		//System.setProperty("webdriver.gecko.driver","C:\\Users\\Sammy\\eclipse-workspace\\textInputSubmitJava\\drivers\\geckodriver.exe");
		//DesiredCapabilities capabilities = DesiredCapabilities.firefox();
		//capabilities.setCapability("marionette",true);
	        //WebDriver driver = new FirefoxDriver();
	        //driver.manage().window().maximize();
		
		String projDir = System.getProperty("user.dir");
		System.setProperty("webdriver.chrome.driver", projDir + "\\drivers\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
			
	        driver.get("https://www.adt.com/natural-disasters?30.307983936955342,-97.75340139999997,9");
	        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	        WebElement map = driver.findElement(By.className("gm-style-pbc"));
	        Actions action = new Actions(driver);
	        action.click(map).build().perform();
	        WebElement table = driver.findElement(By.tagName("table"));
	        System.out.println(table.getText());
	        //WebElement table = driver.findElement(By.className("table table-sm table-striped"));
	        WebElement firstCell = driver.findElement(By.xpath("//table/tbody/tr[1]/td[1]"));
	        System.out.println(firstCell.getText());
	        WebElement secondCell = driver.findElement(By.xpath("//table/tbody/tr[1]/td[2]"));
	        System.out.println(secondCell.getText());
	        WebElement thirdCell = driver.findElement(By.xpath("//table/tbody/tr[2]/td[1]"));
	        System.out.println(thirdCell.getText());
	        WebElement fourthCell = driver.findElement(By.xpath("//table/tbody/tr[2]/td[2]"));
	        System.out.println(fourthCell.getText());
	        WebElement fifthCell = driver.findElement(By.xpath("//table/tbody/tr[3]/td[1]"));
	        System.out.println(fifthCell.getText());
	        WebElement sixCell = driver.findElement(By.xpath("//table/tbody/tr[3]/td[2]"));
	        System.out.println(sixCell.getText());
	        WebElement sevenCell = driver.findElement(By.xpath("//table/tbody/tr[4]/td[1]"));
	        System.out.println(sevenCell.getText());
	        WebElement eightCell = driver.findElement(By.xpath("//table/tbody/tr[4]/td[2]"));
	        System.out.println(eightCell.getText());
	        WebElement nineCell = driver.findElement(By.xpath("//table/tbody/tr[5]/td[1]"));
	        System.out.println(nineCell.getText());
	        WebElement tenCell = driver.findElement(By.xpath("//table/tbody/tr[5]/td[2]"));
	        System.out.println(tenCell.getText());
	        //WebElement elevenCell = driver.findElement(By.xpath("//table/tbody/tr[6]/td[1]"));
	        //System.out.println(elevenCell.getText());
	        //eleventh cell is empty
	        WebElement twelCell = driver.findElement(By.xpath("//table/tbody/tr[6]/td[2]"));
	        System.out.println("Total Disasters:" + twelCell.getText());
	        
	        
	        //WebElement tableBody = table.findElement(By.tagName("tbody"));
	        //System.out.println(tableBody.getText());
	        //List<WebElement> cells = tableBody.findElements(By.tagName("td"));
	        //System.out.println(cells.get(0).getText() + cells.get(1).getText());
	    
	}
}
