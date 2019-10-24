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
    public String getAQIData(String url) throws IOException {
    	//url = "https://airnow.gov/index.cfm?action=airnow.local_city&zipcode=78642&submit=Go";
    	url = "https://airnow.gov/index.cfm?action=airnow.local_city&mapcenter=1&cityid=472";
    	Document doc = Jsoup.connect(url).get();
    	Elements rating = doc.select("table.AQData");
    	ArrayList<String> toReturn = new ArrayList<>(); 
    	Elements test = doc.select("table[class=TblInvisible]");
    	Elements body = test.select("tbody");
    	Elements td = body.select("td");
    //	return td.toString();
    	JSONObject json = new JSONObject();
    	String testReturn = "";
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
    	//return td.toString();
    	//return "nothing found";
    //	Elements body = test.select("tbody");
    //	Elements rows = body.select("tr");
    //	for(Element row : rows) {
    		//toReturn.add(row.select("th").text());
   // 		toReturn.add(row.select("td").text());
    //	}
    	//System.out.println(rating.toString());
    	//return toReturn;
    }
    
    @GetMapping("/api/allergenData")
    public String getAllergenData(String url) throws IOException {
    	//remove this line after testing stage
    	url = "https://www.pollen.com/forecast/current/pollen/78642";
    	Document doc = Jsoup.connect(url).get();
    	Elements div = doc.select("div[class=no-padding col-xs-6 chart-col-border]");
    	Elements p = div.select("p");
    	return doc.toString();
    }
    
    @GetMapping("/api/waterData")
    public String getWaterData(){

		JSONObject responseZip = new JSONObject();
		JSONArray responseContaminants = new JSONArray();

		//Temporary zip code, should call Google api for location specifics
		String zipCode = "78705";
		int count = 0;

		final String url =
				"https://www.ewg.org/tapwater/search-results.php?zip5="+zipCode+"&searchtype=zip";

//		final String url =
//				"https://mytapwater.org/zip/"+zipCode+"/";

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

		}
		catch (Exception e){
			e.printStackTrace();
		}

		System.out.println(responseZip.toString());

		return responseZip.toString();

	}
    
    @GetMapping("/api/naturalDisasters")
    public String getNaturalDisasterData() throws IOException{
    	
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
         
            JSONObject myResponse = new JSONObject(content.toString());
         
            System.out.println(myResponse);
          String result = content.toString();
            System.out.println(result);

          JSONObject myResponse1 = new JSONObject(result.toString());

         
          String testing = myResponse1.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").toString();
          String latString = myResponse1.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").get("lat").toString();
          String longString = myResponse1.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").get("lng").toString();
            System.out.println(testing);
            System.out.println(latString+","+longString);
      
          // String humid = myResponse.getJSONObject("currently").getString("humidity");
          // String wind = myResponse.getJSONObject("currently").getString("windSpeed");
          // String precip = myResponse.getJSONObject("currently").getString("precipProbability");

          // Plug in coordinates to get request
          double latitude = Double.parseDouble(latString);
          double longitude = Double.parseDouble(longString);
          
          
          
          String projDir = System.getProperty("user.dir");
  		System.setProperty("webdriver.chrome.driver", projDir + "//drivers//chromedriver");
  		WebDriver driver = new ChromeDriver();
  		
  		JSONObject json = new JSONObject();
  			
  	        driver.get("https://www.adt.com/natural-disasters?30.307983936955342,-97.75340139999997,9");
  	        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
  	        WebElement map = driver.findElement(By.className("gm-style-pbc"));
  	        Actions action = new Actions(driver);
  	        action.click(map).build().perform();
  	        WebElement table = driver.findElement(By.tagName("table"));
//  	        System.out.println(table.getText());
  	        //WebElement table = driver.findElement(By.className("table table-sm table-striped"));
  	        
  	        // Fire
  	        WebElement firstCell = driver.findElement(By.xpath("//table/tbody/tr[1]/td[1]"));
//  	        System.out.println(firstCell.getText());
  	        WebElement secondCell = driver.findElement(By.xpath("//table/tbody/tr[1]/td[2]"));
//  	        System.out.println(secondCell.getText());
  	        
  	        json.put("Fire", secondCell.getText());
  	        
  	        // Hurricane
  	        WebElement thirdCell = driver.findElement(By.xpath("//table/tbody/tr[2]/td[1]"));
//  	        System.out.println(thirdCell.getText());
  	        WebElement fourthCell = driver.findElement(By.xpath("//table/tbody/tr[2]/td[2]"));
//  	        System.out.println(fourthCell.getText());
  	        
  	        json.put("Hurricane",  fourthCell.getText());
  	        
  	        // Severe Storm
  	        WebElement fifthCell = driver.findElement(By.xpath("//table/tbody/tr[3]/td[1]"));
//  	        System.out.println(fifthCell.getText());
  	        WebElement sixCell = driver.findElement(By.xpath("//table/tbody/tr[3]/td[2]"));
//  	        System.out.println(sixCell.getText());
  	        
  	        json.put("Severe Storm", sixCell.getText());
  	        
  	        // Flood
  	        WebElement sevenCell = driver.findElement(By.xpath("//table/tbody/tr[4]/td[1]"));
//  	        System.out.println(sevenCell.getText());
  	        WebElement eightCell = driver.findElement(By.xpath("//table/tbody/tr[4]/td[2]"));
//  	        System.out.println(eightCell.getText());
  	        
  	        json.put("Flood", eightCell.getText());
  	        
  	        // Drought
  	        WebElement nineCell = driver.findElement(By.xpath("//table/tbody/tr[5]/td[1]"));
//  	        System.out.println(nineCell.getText());
  	        WebElement tenCell = driver.findElement(By.xpath("//table/tbody/tr[5]/td[2]"));
//  	        System.out.println(tenCell.getText());
  	        
  	        json.put("Drought", tenCell.getText());
  	        
  	        //WebElement elevenCell = driver.findElement(By.xpath("//table/tbody/tr[6]/td[1]"));
  	        //System.out.println(elevenCell.getText());
  	        //eleventh cell is empty
  	        WebElement twelCell = driver.findElement(By.xpath("//table/tbody/tr[6]/td[2]"));
//  	        System.out.println("Total Disasters:" + twelCell.getText());
  	        
  	        json.put("Total Disasters", twelCell.getText());
  	        
  	        
  	        //WebElement tableBody = table.findElement(By.tagName("tbody"));
  	        //System.out.println(tableBody.getText());
  	        //List<WebElement> cells = tableBody.findElements(By.tagName("td"));
  	        //System.out.println(cells.get(0).getText() + cells.get(1).getText());
  	        
  	        return json.toString();
    	}
    	
    	catch (Exception e) {
            e.printStackTrace();
		}
    	
    	return "Failed during execution";
    	
    }


}
