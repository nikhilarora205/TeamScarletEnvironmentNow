package no.kantega.springandreact;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

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

@RestController
public class HelloController {
    @GetMapping("/api/hello")
    public String hello() {
        return "Hello, the time at the server is now " + new Date() + "\n";
    }   
   
    
    @GetMapping("/api/AQIData/{address}")
    public String getAQIData(@PathVariable String address) throws IOException {
    	String zipCode = getLocation(address.replaceAll("%20", " "), 0);
    	System.out.println(zipCode);
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
    
    @GetMapping("/api/barChart1/{address}")
    public String getBarChart1Data(@PathVariable String address) throws IOException {
    	// Variables
    	String barChart1 = new String();
    	String zipCode = getLocation(address.replaceAll("%20", " "), 0);
    	String url = "https://airnow.gov/index.cfm?action=airnow.local_city&zipcode=" + zipCode + "&submit=Go";
    	// JSOUP
    	Document doc = Jsoup.connect(url).get();
    	Elements test = doc.select("table[class=TblInvisible]");
    	Elements body = test.select("tbody");
    	Elements td = body.select("td");
    	// SCRAPING AND STORING
    	Set<String> possibleValues = new HashSet<String>(Arrays.asList(new String [] {"Good", "Moderate", "Unhealthy for Sensitive Groups", "Unhealthy", "Very Unhealthy", "Hazardous"}));
    	JSONArray jsonArray = new JSONArray();
    	
    	for(int i = 0; i < td.size(); i++) {
    		String currentWord = td.get(i).text();
    		if(possibleValues.contains(currentWord) && i!=td.size()-1) {
    			String currentDetail = td.get(i+1).text();
    			if(currentDetail.equals("Ozone")) {
    				JSONObject tempson = new JSONObject();
    				barChart1 = "{\"y\": " + Integer.parseInt(td.get(i+2).text()) + ", \"x\": \"Ozone\"}";
    				tempson.put("y",Integer.parseInt(td.get(i+2).text()));
    				tempson.put("x", "Ozone");
    				jsonArray.put(tempson);
    			}else if(currentDetail.equals("Particles (PM2.5)")) {
    				JSONObject tempson = new JSONObject();
    				barChart1 = barChart1 +  ",\n{\"y\": " + Integer.parseInt(td.get(i+2).text()) + ", \"x\": \"PM2.5\"}";
    				tempson.put("y",Integer.parseInt(td.get(i+2).text()));
    				tempson.put("x", "PM2.5");
    				jsonArray.put(tempson);
    			}else if(currentDetail.equals("Particles (PM10)")) {
    				JSONObject tempson = new JSONObject();
    				tempson.put("y",Integer.parseInt(td.get(i+2).text()));
    				tempson.put("x", "PM10");
    				jsonArray.put(tempson);
    			}
    		}
    	}
    	JSONObject response = new JSONObject();
    	response.put("bar1Data", jsonArray);
    	return response.toString();
    }
    

    @GetMapping("/api/waterData/{address}")
    public String getWaterData(@PathVariable String address){
    	String zipCode = getLocation(address.replaceAll("%20", " "), 0);
		JSONObject responseZip = new JSONObject();
		JSONArray responseContaminants = new JSONArray();
		final String url =
				"https://www.ewg.org/tapwater/search-results.php?zip5=" + zipCode + "&searchtype=zip";
		try {
			final Document document = Jsoup.connect(url).get();
			Elements linkToData = document.select(".primary-btn");
			String dataUrl = linkToData.attr("href");
			//testing to see if url is correct
			final Document contaminantDoc = Jsoup.connect("https://www.ewg.org/tapwater/" + dataUrl).get();
			//for each grid item (contaminant)
			for (Element item : contaminantDoc.select(".contaminant-name")) {
					JSONObject tempContamObject = new JSONObject();
					String contam = item.select("h3").text();
					String level = item.select(".detect-times-greater-than").text();
					tempContamObject.put("contaminant", contam);
					tempContamObject.put("level", level);
					responseContaminants.put(tempContamObject);
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
    
    @GetMapping("/api/barChart2/{address}")
    public String getBarChart2Data(@PathVariable String address){
    	String zipCode = getLocation(address.replaceAll("%20", " "), 0);
    	JSONObject response = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		final String url =
				"https://www.ewg.org/tapwater/search-results.php?zip5=" + zipCode + "&searchtype=zip";
		try {
			final Document document = Jsoup.connect(url).get();
			Elements linkToData = document.select(".primary-btn");
			String dataUrl = linkToData.attr("href");
			final Document contaminantDoc = Jsoup.connect("https://www.ewg.org/tapwater/" + dataUrl).get();
			
			for (Element item : contaminantDoc.select(".contaminant-name")) {
				String contam = item.select("h3").text();
				String level = item.select(".detect-times-greater-than").text();
				
				if(!level.isEmpty()) {
					
					//Debugging
					System.out.println(level);
					System.out.println(level.replace("x", ""));
					System.out.println(contam);
					level = level.replace("x", "");
					
					if(level.contains(".")) {
						level = level.substring(0,level.indexOf("."));
					}
					
					System.out.println(level);
					Integer tempLevel = Integer.parseInt(level);
					JSONObject tempContamObject = new JSONObject();
					
					try {
						tempContamObject.put("y", tempLevel);
						tempContamObject.put("x",contam.substring(0, 4));
						jsonArray.put(tempContamObject);
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
			}

	    	response.put("bar2Data", jsonArray);
	    	System.out.println(response.toString());
			return response.toString();
		}
		catch (Exception e){
			e.printStackTrace();
		}
		System.out.println("It is not getting bar2Data: " + response.toString());
		return response.toString();
	}

    @GetMapping("/api/naturalDisasters/{address}")
    public String getNaturalDisasterData(@PathVariable String address) throws IOException{
    	try {
    		MongoClientURI uri = new MongoClientURI(
    				"mongodb://nikhilarora:soft461datatest@cluster0-shard-00-00-kvrlc.gcp.mongodb.net:27017,cluster0-shard-00-01-kvrlc.gcp.mongodb.net:27017,cluster0-shard-00-02-kvrlc.gcp.mongodb.net:27017/test?ssl=true&replicaSet=Cluster0-shard-0&authSource=admin&retryWrites=true&w=majority");
    		MongoClient mongoClient = new MongoClient(uri);
    		@SuppressWarnings("deprecation")
    		DB database = mongoClient.getDB("environmentnow");
    		DBCollection collection = database.getCollection("disasters");
    		
    		// address= "100 Orvieto Cove";
    		
    		String location = reverseLocation(address);
    		BasicDBObject query = new BasicDBObject("Location", location);
    		query.toJson();
    		DBCursor test = collection.find(query);
    		int storm = 0;
    		int earthquake = 0;
    		int wildfire = 0;
    		int flood = 0;
    		int drought = 0;
    		int extremeTemp = 0;
    		int landslide = 0;
    		int volcanicActivity = 0;
    		int epidemic = 0;
    		while(test.hasNext()){
    			String document = test.next().toString();
    			if(document.contains("Storm")) {
    				storm++;
    			}
    			if(document.contains("Earthquake")) {
    				earthquake++;
    			}
    			if(document.contains("Wildfire")) {
    				wildfire++;
    			}
    			if(document.contains("Flood")) {
    				flood++;
    			}
    			if(document.contains("Drought")) {
    				drought++;
    			}
    			if(document.contains("Extreme temperature")) {
    				extremeTemp++;
    			}
    			if(document.contains("Landslide")) {
    				landslide++;
    			}
    			if(document.contains("Volvanic activity")) {
    				volcanicActivity++;
    			}
    			if(document.contains("Epidemic")) {
    				epidemic++;
    			}
    		}
    		
			String returnString = "";
			JSONObject json = new JSONObject();
			json.put("Storm", storm);
			json.put("Earthquake", earthquake);
			json.put("Wildfire", wildfire);
			json.put("Flood", flood);
			json.put("Drought", drought);
			json.put("Extreme Temperature", extremeTemp);
			json.put("Land Slide", landslide);
			json.put("Volcanic Activity", volcanicActivity);
			json.put("Epidemic", epidemic);
			// return returnString;
			return json.toString();
    	}
    	catch (Exception e) {
            e.printStackTrace();
		}
    	return "Failed to query disaster data";
	}
    
    
    @GetMapping("/api/barChart3/{address}")
    public String getBarChart3Data(@PathVariable String address) throws IOException{
    	try {
    		MongoClientURI uri = new MongoClientURI(
    				"mongodb://nikhilarora:soft461datatest@cluster0-shard-00-00-kvrlc.gcp.mongodb.net:27017,cluster0-shard-00-01-kvrlc.gcp.mongodb.net:27017,cluster0-shard-00-02-kvrlc.gcp.mongodb.net:27017/test?ssl=true&replicaSet=Cluster0-shard-0&authSource=admin&retryWrites=true&w=majority");
    		MongoClient mongoClient = new MongoClient(uri);
    		@SuppressWarnings("deprecation")
    		DB database = mongoClient.getDB("environmentnow");
    		DBCollection collection = database.getCollection("disasters");
    		String location = reverseLocation(address);
    		BasicDBObject query = new BasicDBObject("Location", location);
    		query.toJson();
    		DBCursor test = collection.find(query);
    		int storm = 0;
    		int earthquake = 0;
    		int wildfire = 0;
    		int flood = 0;
    		int drought = 0;
    		int extremeTemp = 0;
    		int landslide = 0;
    		int volcanicActivity = 0;
    		int epidemic = 0;
    		while(test.hasNext()){
    			String document = test.next().toString();
    			if(document.contains("Storm")) {
    				storm++;
    			}
    			if(document.contains("Earthquake")) {
    				earthquake++;
    			}
    			if(document.contains("Wildfire")) {
    				wildfire++;
    			}
    			if(document.contains("Flood")) {
    				flood++;
    			}
    			if(document.contains("Drought")) {
    				drought++;
    			}
    			if(document.contains("Extreme temperature")) {
    				extremeTemp++;
    			}
    			if(document.contains("Landslide")) {
    				landslide++;
    			}
    			if(document.contains("Volvanic activity")) {
    				volcanicActivity++;
    			}
    			if(document.contains("Epidemic")) {
    				epidemic++;
    			}
    		}
    		
			JSONArray jsonArray = new JSONArray();
			JSONObject json = new JSONObject();
			
			if(storm!=0) {
				JSONObject tempson = new JSONObject();
				tempson.put("y",storm);
				tempson.put("x", "Storm");
				jsonArray.put(tempson);
			}
			
			if(earthquake!=0) {
				JSONObject tempson = new JSONObject();
				tempson.put("y",earthquake);
				tempson.put("x", "Earthquake");
				jsonArray.put(tempson);
			}
			
			if(wildfire!=0) {
				JSONObject tempson = new JSONObject();
				tempson.put("y",wildfire);
				tempson.put("x", "Wildfire");
				jsonArray.put(tempson);
			}
			
			if(flood!=0) {
				JSONObject tempson = new JSONObject();
				tempson.put("y",flood);
				tempson.put("x", "Flood");
				jsonArray.put(tempson);
			}
			
			if(drought!=0) {
				JSONObject tempson = new JSONObject();
				tempson.put("y",drought);
				tempson.put("x", "Drought");
				jsonArray.put(tempson);
			}
			
			if(extremeTemp!=0) {
				JSONObject tempson = new JSONObject();
				tempson.put("y",extremeTemp);
				tempson.put("x", "XTemp");
				jsonArray.put(tempson);
			}
			
			if(landslide!=0) {
				JSONObject tempson = new JSONObject();
				tempson.put("y",landslide);
				tempson.put("x", "Landslide");
				jsonArray.put(tempson);
			}
			
			if(volcanicActivity!=0) {
				JSONObject tempson = new JSONObject();
				tempson.put("y",volcanicActivity);
				tempson.put("x", "Volcano Act.");
				jsonArray.put(tempson);
			}
			
			if(epidemic!=0) {
				JSONObject tempson = new JSONObject();
				tempson.put("y",epidemic);
				tempson.put("x", "Epidemic");
				jsonArray.put(tempson);
			}
    		JSONObject response = new JSONObject();
    		response.put("bar3Data", jsonArray);
    		return response.toString();
    	}
    	catch (Exception e) {
            e.printStackTrace();
		}
    	return "Failed to query disaster data";
	}
    
	
    @GetMapping("/api/validAddress/{address}")
	public String validAddress(@PathVariable String address){

		try {
			// Use Google GeoCoder to get coordinates
			// might have to revisit URLEncoder function later on
			// replace "Austin" with text box from front end
			// address = "100 Orvieto Cove";
			// String address = "xyz";

			// Get URL for API Request
			URL url = new URL(
					"https://maps.googleapis.com/maps/api/geocode/json?address="
							+ URLEncoder.encode(address, java.nio.charset.StandardCharsets.UTF_8.toString()) + "&sensor=true&key=AIzaSyARRJsBkisGqJ5_1Vo2QB_Pk2mIMYQVZlw");

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
			try {
				if (myResponse.getJSONArray("results").getJSONObject(0).get("partial_match").equals("true")) {
					return "false";
				}
			} catch (Exception e) {
				return "true";
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return "false";

	}   
	
    public String getLocation(String address, Integer zeroForZip ) {
    	try {
            // Use Google GeoCoder to get coordinates
        	// might have to revisit URLEncoder function later on
        	// replace "Austin" with text box from front end
//        	address = "100 Orvieto Cove";
        	
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
    @GetMapping("/api/reverseLocation")
    public String reverseLocation(String address) {

    	try {
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
            String state = myResponse.getJSONArray("results").getJSONObject(0).getJSONArray("address_components").getJSONObject(4).get("long_name").toString();
            return state;
    		}catch (Exception e) {
            e.printStackTrace();
    	}
    	return "Please narrow search";
    }
    
    
    /*
     public static boolean activityExists(MongoDatabase db, ObjectId id) {
	    FindIterable<Document> iterable = db.getCollection("activity").find(new Document("_id", id));
	    return iterable.first() != null;
	} 	
     
     */
    
    
    @GetMapping("/api/checkAirExist/{address}")
    public Integer countAirDB(@PathVariable String address) {
		MongoClientURI uri = new MongoClientURI(
				"mongodb://nikhilarora:soft461datatest@cluster0-shard-00-00-kvrlc.gcp.mongodb.net:27017,cluster0-shard-00-01-kvrlc.gcp.mongodb.net:27017,cluster0-shard-00-02-kvrlc.gcp.mongodb.net:27017/test?ssl=true&replicaSet=Cluster0-shard-0&authSource=admin&retryWrites=true&w=majority");
		MongoClient mongoClient = new MongoClient(uri);
		@SuppressWarnings("deprecation")
		DB database = mongoClient.getDB("environmentnow");
		DBCollection collection = database.getCollection("air");
		BasicDBObject query = new BasicDBObject("zipCode", address);
		query.toJson();
		DBCursor test = collection.find(query);
		int count=0;
		while(test.hasNext()) {
			count++;
			test.next();
		}
		return count;
    }
    
    
    //STILL WORKIN ON
    @GetMapping("api/updateAir")
    public String updateAirDB() {
		return "";
    }
    
  @GetMapping("/api/allergenData")
  public String getAllergenData(String address) throws IOException {
  	
  	// WORK ON THIS
  	String url = "https://www.weatherbug.com/life/pollen/76062";
  	Document doc = Jsoup.connect(url).get();
  	Elements div = doc.select("div[class=widget__body widget--index__body]");
  	return doc.toString();
  }
    
}   



// Storm, Earthquake, Wildfire, Flood, Drought, Extreme temperature, Landslide, Volcanic activity, Epidemic
