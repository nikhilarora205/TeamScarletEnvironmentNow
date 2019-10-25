package no.kantega.springandreact;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

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

@RestController
public class HelloController {
    @GetMapping("/api/hello")
    public String hello() {
        return "Hello, the time at the server is now " + new Date() + "\n";
       // String myUrl = "https://api.github.com/repos/nikhilarora205/TeamScarletEnvironmentNow/stats/commit_activity";
        //String to place our result in
      //  String result;
       // HttpGetRequest getRequest = new HttpGetRequest();
       // result = getRequest.execute(myUrl).get();
       // JSONObject myResponse = new JSONObject(result.toString());
       // String total = myResponse.getJSONObject("total").toString();
       // return total;
    }
    
    @GetMapping("/api/AQIData")
    public String getAQIData(String address) throws IOException {
    	// Get zipcode from address
    	address = "100 Orvieto Cove";
    	String zipCode = getLocation(address, 0);
    	
    	// Check if search was specific enough
    	if (zipCode == "Please narrow search") {
    		return "Please narrow search";
    	}
    	
    	// get JSON
    	String url = "https://airnow.gov/index.cfm?action=airnow.local_city&zipcode=" + zipCode + "&submit=Go";
    	Document doc = Jsoup.connect(url).get();
    	Elements test = doc.select("table[class=TblInvisible]");
    	Elements body = test.select("tbody");
    	Elements td = body.select("td");
    	JSONObject json = new JSONObject();
    	//return td.get(2).text();
    	Set<String> possibleValues = new HashSet<String>(Arrays.asList(new String [] {"Good", "Moderate", "Unhealthy for Sensitive Groups", "Unhealthy", "Very Unhealthy", "Hazardous"}));
    	for(int i = 0; i < td.size(); i++) {
    		String currentWord = td.get(i).text();
    		if(possibleValues.contains(currentWord) && i!=td.size()-1) {
    			String currentDetail = td.get(i+1).text();
    			if(currentDetail.equals("Ozone")) {
    				json.put("Ozone", td.get(i+2).text());
    			}else if(currentDetail.equals("Particles (PM2.5)")) {
    				json.put("PM2.5", td.get(i+2).text());
    			}else if(currentDetail.equals("Particles (PM10)")) {
    				json.put("PM10", td.get(i+2).text());
    			}
    		}
    	}
    	return json.toString();
    }   
    @GetMapping("/api/allergenData")
    public String getAllergenData(String address) throws IOException {
    	
    	// WORK ON THIS
    	String url = "https://www.weatherbug.com/life/pollen/76062";
    	Document doc = Jsoup.connect(url).get();
    	Elements div = doc.select("div[class=widget__body widget--index__body]");
    	return doc.toString();
    }
    @GetMapping("/api/waterData")
    public String getWaterData(String address){
    	// Get zipcode from address
    	address = "100 Orvieto Cove";
    	String zipCode = getLocation(address, 0);
    	
    	// Check if search was specific enough
    	if (zipCode == "Please narrow search") {
    		return "Please narrow search";
    	}
		JSONObject responseZip = new JSONObject();
		JSONArray responseContaminants = new JSONArray();
		int count = 0;
		final String url =
				"https://www.ewg.org/tapwater/search-results.php?zip5=" + zipCode + "&searchtype=zip";
		try {
			final Document document = Jsoup.connect(url).get();
			Elements linkToData = document.select(".primary-btn");
			String dataUrl = linkToData.attr("href");
			//testing to see if url is correct
			System.out.println(dataUrl);
			final Document contaminantDoc = Jsoup.connect("https://www.ewg.org/tapwater/" + dataUrl).get();
			//for each grid item (contaminant)
			for (Element item : contaminantDoc.select(".contaminant-name")) {
				JSONObject tempContamObject = new JSONObject();
				String contam = item.select("h3").text();
				String level = item.select(".detect-times-greater-than").text();
				tempContamObject.put("contaminant", contam);
				tempContamObject.put("level", level);
				responseContaminants.put(tempContamObject);
				System.out.println(item.select("h3").text());
				System.out.println(item.select(".detect-times-greater-than").text());    //this number is the # of times over the EWG health guideline limit
			}
			responseZip.put("zipcode", zipCode);
			responseZip.put("contaminants", responseContaminants);
			return responseZip.toString();
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return responseZip.toString();
	}
    @GetMapping("/api/naturalDisasters")
    public String getNaturalDisasterData(String address) throws IOException{
    	try {
    		// Get zipcode from address
        	address = "100 Orvieto Cove";
        	String latLong = getLocation(address, 1);
        	
        	// Check if search was specific enough
        	if (latLong == "Please narrow search") {
        		return "Please narrow search";
        	}
        	/*
        	// Break up string into latString and longString
        	List<String> temp = Arrays.asList(latLong.split(","));
        	String latString = temp.get(0);
        	String longString = temp.get(1);
        	
    		// Plug in coordinates to get request
    		double latitude = Double.parseDouble(latString);
    		double longitude = Double.parseDouble(longString);
			*/
    		String projDir = System.getProperty("user.dir");
	  		System.setProperty("webdriver.chrome.driver", projDir + "//drivers//chromedriver");
	  		ChromeOptions options = new ChromeOptions();
			options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200","--ignore-certificate-errors");
			WebDriver driver = new ChromeDriver(options);
  		
			JSONObject json = new JSONObject();
  			
  	        driver.get("https://www.adt.com/natural-disasters?" + latLong +",9");
  	        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
  	        WebElement map = driver.findElement(By.className("gm-style-pbc"));
  	        Actions action = new Actions(driver);
  	        action.click(map).build().perform();
  	        WebElement table = driver.findElement(By.tagName("table"));
  	        // Fire
  	        WebElement firstCell = driver.findElement(By.xpath("//table/tbody/tr[1]/td[1]"));
  	        WebElement secondCell = driver.findElement(By.xpath("//table/tbody/tr[1]/td[2]"));
  	        json.put(firstCell.getText(), secondCell.getText());
  	        
  	        // Hurricane
  	        WebElement thirdCell = driver.findElement(By.xpath("//table/tbody/tr[2]/td[1]"));
  	        WebElement fourthCell = driver.findElement(By.xpath("//table/tbody/tr[2]/td[2]"));
  	        json.put(thirdCell.getText(),  fourthCell.getText());
  	        
  	        // Severe Storm
  	        WebElement fifthCell = driver.findElement(By.xpath("//table/tbody/tr[3]/td[1]"));
  	        WebElement sixCell = driver.findElement(By.xpath("//table/tbody/tr[3]/td[2]"));
  	        json.put(fifthCell.getText(), sixCell.getText());
  	        
  	        // Flood
  	        WebElement sevenCell = driver.findElement(By.xpath("//table/tbody/tr[4]/td[1]"));
  	        WebElement eightCell = driver.findElement(By.xpath("//table/tbody/tr[4]/td[2]"));
  	        json.put(sevenCell.getText(), eightCell.getText());
  	        
  	        // Drought
  	        WebElement nineCell = driver.findElement(By.xpath("//table/tbody/tr[5]/td[1]"));
  	        WebElement tenCell = driver.findElement(By.xpath("//table/tbody/tr[5]/td[2]"));
  	        json.put(nineCell.getText(), tenCell.getText());
  	        WebElement twelCell = driver.findElement(By.xpath("//table/tbody/tr[6]/td[2]"));
  	        json.put("Total Disasters", twelCell.getText()); 	        
  	        return json.toString();
    	}
    	catch (Exception e) {
            e.printStackTrace();
		}
    	return "Failed during execution";
    }
    @GetMapping("/api/getLocation")
    public String getLocation(String address, Integer zeroForZip ) {
    	try {
            // Use Google GeoCoder to get coordinates
        	// might have to revisit URLEncoder function later on
        	// replace "Austin" with text box from front end
        	address = "100 Orvieto Cove";
        	
        	// Get URL for API Request
            URL url = new URL(
                    "https://maps.googleapis.com/maps/api/geocode/json?address="
                            + URLEncoder.encode(address,java.nio.charset.StandardCharsets.UTF_8.toString()) + "&sensor=true&key=AIzaSyARRJsBkisGqJ5_1Vo2QB_Pk2mIMYQVZlw");
            
            // Connect to URL
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
            
            // Disconnect
            in.close();
            con.disconnect();
            
            // Aquire Content
            String result = content.toString();
            JSONObject myResponse = new JSONObject(result.toString());
 
            //zeroForZip = 1;
            String zip = myResponse.getJSONArray("results").getJSONObject(0).getJSONArray("address_components").getJSONObject(6).get("short_name").toString();
            String latString = myResponse.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").get("lat").toString();
            String longString = myResponse.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").get("lng").toString();
            
            if( zeroForZip == 0 ) {
            	return zip;
            }else {
            	return latString + "," + longString;
            }
    	}catch (Exception e) {
            e.printStackTrace();
		}
    	return "Please narrow search";
    }   
}
