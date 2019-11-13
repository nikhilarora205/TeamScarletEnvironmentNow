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

	@GetMapping("/api/AQIData/{address}")
    public String getAQIData(@PathVariable String address) throws IOException {

		System.out.println(address);
    	String zipCode = getLocation(address.replaceAll("%20", " "), 0);
    	
    	//check if zip exists in DB
//    	if(countAirDB(zipCode) == 0) {
//    		//Put in DB if not
//    		
//    	}
    	// Check if search was specific enough
//    	if (zipCode == "Please narrow search") {
//    		return "Please narrow search";
//    	}
    	
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


    @GetMapping("/api/waterData/{address}")
    public String getWaterData(@PathVariable String address){

    	// Get zipcode from address
    	// address = "100 Orvieto Cove";
    	String zipCode = getLocation(address.replaceAll("%20", " "), 0);
    	
    	// Check if search was specific enough
//    	if (zipCode == "Please narrow search") {
//    		return "Please narrow search";
//    	}
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
			// System.out.println(dataUrl);
			final Document contaminantDoc = Jsoup.connect("https://www.ewg.org/tapwater/" + dataUrl).get();
			//for each grid item (contaminant)
			for (Element item : contaminantDoc.select(".contaminant-name")) {
				JSONObject tempContamObject = new JSONObject();
				String contam = item.select("h3").text();
				String level = item.select(".detect-times-greater-than").text();
				tempContamObject.put("contaminant", contam);
				tempContamObject.put("level", level);
				responseContaminants.put(tempContamObject);
				// System.out.println(item.select("h3").text());
				// System.out.println(item.select(".detect-times-greater-than").text());    //this number is the # of times over the EWG health guideline limit
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


    @GetMapping("/api/naturalDisasters/{address}")
    public String getNaturalDisasterData(@PathVariable String address) throws IOException{
		//TODO:
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
    		// if (storm!=0) {
			// 	returnString = returnString + "Storm: " + storm + "\n";
			// 	json.put("Storm", storm);
    		// }
    		// if (earthquake!=0) {
			// 	returnString = returnString + "Earthquake: " + earthquake + "\n";
			// 	json.put("Earthquake", earthquake);
    		// }
    		// if (wildfire!=0) {
			// 	returnString = returnString + "Wildfire: " + wildfire + "\n";
			// 	json.put("Wildfire", wildfire);
    		// }
    		// if (flood!=0) {
			// 	returnString = returnString + "Flood: " + flood + "\n";
			// 	json.put("Flood", flood);
    		// }
    		// if (drought!=0) {
			// 	returnString = returnString + "Drought: " + drought + "\n";
			// 	json.put("Drought", drought);
    		// }
    		// if (extremeTemp!=0) {
			// 	returnString = returnString + "Extreme Temperature: " + extremeTemp + "\n";
			// 	json.put("Extreme Temperature", extremeTemp);
    		// }
    		// if (landslide!=0) {
			// 	returnString = returnString + "Land Slide: " + landslide + "\n";
			// 	json.put("Land Slide", landslide);
    		// }
    		// if (volcanicActivity!=0) {
			// 	returnString = returnString + "Volcanic Activity: " + volcanicActivity + "\n";
			// 	json.put("Volcanic Activity", volcanicActivity);
    		// }
    		// if (epidemic!=0) {
			// 	returnString = returnString + "Epidemic: " + epidemic + "\n";
			// 	json.put("Epidemic", epidemic);
			// }
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
            /*
            //zeroForZip = 1;
			String zip = myResponse.get("status").toString();
			System.out.println(zip);
            String latString = myResponse.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").get("lat").toString();
            String longString = myResponse.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").get("lng").toString();
            
			if(zip.equals("ZERO_RESULTS")) return "False";
			else return "True";
		}*/
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
 		//	System.out.println(myResponse.toString());
            //zeroForZip = 1;
            String zip = myResponse.getJSONArray("results").getJSONObject(0).getJSONArray("address_components").getJSONObject(6).get("short_name").toString();
            String latString = myResponse.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").get("lat").toString();
            String longString = myResponse.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").get("lng").toString();
            
            System.out.println(zip);
            System.out.println(latString);
            System.out.println(longString);
            
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
 			//System.out.println(myResponse.toString());
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
    
    
    @GetMapping("/api/checkAirExist")
    public Integer countAirDB(String zipCode) {
		MongoClientURI uri = new MongoClientURI(
				"mongodb://nikhilarora:soft461datatest@cluster0-shard-00-00-kvrlc.gcp.mongodb.net:27017,cluster0-shard-00-01-kvrlc.gcp.mongodb.net:27017,cluster0-shard-00-02-kvrlc.gcp.mongodb.net:27017/test?ssl=true&replicaSet=Cluster0-shard-0&authSource=admin&retryWrites=true&w=majority");
		MongoClient mongoClient = new MongoClient(uri);
		@SuppressWarnings("deprecation")
		DB database = mongoClient.getDB("environmentnow");
		DBCollection collection = database.getCollection("air");
		BasicDBObject query = new BasicDBObject("zipCode", zipCode);
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
}   
// Storm, Earthquake, Wildfire, Flood, Drought, Extreme temperature, Landslide, Volcanic activity, Epidemic
