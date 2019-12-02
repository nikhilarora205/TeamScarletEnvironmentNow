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

import org.bson.*;

import java.util.*;
import java.io.*;
import java.net.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.util.List;
import java.util.concurrent.TimeUnit;



@RestController
public class HelloController {

	@GetMapping("/api/AQIData/{address}")
    public String getAQIData(@PathVariable String address) throws IOException {
    	// Variables
    	String airString = new String();
		String zipCode = getLocation(address.replaceAll("%20", " "), 0);
		if(!zipCode.equals("Please narrow search")) {
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
						JSONObject airJSON = new JSONObject();
						airString = "{\"y\": " + Integer.parseInt(td.get(i+2).text()) + ", \"x\": \"Ozone\"}";
						airJSON.put("y",Integer.parseInt(td.get(i+2).text()));
						airJSON.put("x", "Ozone");
						jsonArray.put(airJSON);
					}else if(currentDetail.equals("Particles (PM2.5)")) {
						JSONObject airJSON = new JSONObject();
						airString = airString +  ",\n{\"y\": " + Integer.parseInt(td.get(i+2).text()) + ", \"x\": \"PM2.5\"}";
						airJSON.put("y",Integer.parseInt(td.get(i+2).text()));
						airJSON.put("x", "PM2.5");
						jsonArray.put(airJSON);
					}else if(currentDetail.equals("Particles (PM10)")) {
						JSONObject airJSON = new JSONObject();
						airJSON.put("y",Integer.parseInt(td.get(i+2).text()));
						airJSON.put("x", "PM10");
						jsonArray.put(airJSON);
					}
				}
			}
			JSONObject response = new JSONObject();
			response.put("airData", jsonArray);
			return response.toString();
		}
		else{
			return "{}";
		}
	}
	
	@GetMapping("/api/allergenData/{address}")
    public String getAllergenData(@PathVariable String address) throws IOException {
		String zipCode = getLocation(address.replaceAll("%20", " "), 0);
		if(!zipCode.equals("Please narrow search")) {
			String url = "https://weather.com/forecast/allergy/l/" + zipCode;
			Document doc = Jsoup.connect(url).get();
			Elements div = doc.select("section[class=styles__allergyOutlook__3e1L4]");
			Elements div2 = div.select("div[class=styles__allergyOutlookContentGraphMsgQual__25hN1]");
			JSONObject json = new JSONObject();
			for(int i = 0; i < div2.size();i++ ) {
				String currentWord = div2.get(i).text();
				if( i == 0 ) {
					json.put("Tree Pollen", currentWord);
				}else if(i == 1) {
					json.put("Grass Pollen", currentWord);
				}else if(i == 2) {
					json.put("Ragweed Pollen", currentWord);
				}
			}
			return json.toString();
		}else{
			return "{}";
		}
    }
	
	@GetMapping("/api/waterData/{address}")
    public String getWaterData(@PathVariable String address){
		String zipCode = getLocation(address.replaceAll("%20", " "), 0);
		if(!zipCode.equals("Please narrow search")) {
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
						// System.out.println(level);
						// System.out.println(level.replace("x", ""));
						// System.out.println(contam);
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

				response.put("waterData", jsonArray);
				// System.out.println(response.toString());
				return response.toString();
			}
			catch (Exception e){
				e.printStackTrace();
			}
			// System.out.println("It is not getting bar2Data: " + response.toString());
			return response.toString();
		}else{
			return "{}";
		}
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
			String location = reverseLocation(address);
			if (!location.equals("Please narrow search")) {
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
				response.put("natData", jsonArray);
				return response.toString();
			}else{
				return "Failed to query disaster data";
			}
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
		//	System.out.println(myResponse);
			try {
				if (myResponse.getJSONArray("results").getJSONObject(0).get("partial_match").toString().equals("true")) {
					//System.out.println("incorrect address");
					return "incorrect";
				}
			} catch (Exception e) {
			//	System.out.println("address not incorrect");
			}

			try{
		//		System.out.println("various types");
		//		System.out.println("-> " + myResponse.getJSONArray("results").getJSONObject(0).getJSONArray("address_components").getJSONObject(0).getJSONArray("types"));



				/*if(myResponse.getJSONArray("results").getJSONObject(0).getJSONArray("address_components").getJSONObject(0).get("types").toString().equals(response1)){
					return "true";
				}else{
					return "narrow";
				}*/

				if(myResponse.getJSONArray("results").getJSONObject(0).getJSONArray("address_components").toString().contains("[\"postal_code\"]")){
					return "true";
				}else{
					return "narrow";
				}


			}
			catch(Exception e){
				// System.out.println("here");
				return "narrow";
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
		return "true";

	}   
	
    public String getLocation(String address, Integer zeroForZip ) {
    	//this means frontend passed in a simple zipcode

			try {
				// Use Google GeoCoder to get coordinates
				// might have to revisit URLEncoder function later on
				// replace "Austin" with text box from front end

				// Get URL for API Request
				URL url = new URL(
						"https://maps.googleapis.com/maps/api/geocode/json?address="
								+ URLEncoder.encode(address, java.nio.charset.StandardCharsets.UTF_8.toString()) + "&sensor=true&key=AIzaSyARRJsBkisGqJ5_1Vo2QB_Pk2mIMYQVZlw");
				// System.out.println("URL: " + url.toString());

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

				//iterates through address components array and finds the one with postal code
				JSONArray zipArray = myResponse.getJSONArray("results").getJSONObject(0).getJSONArray("address_components");
				String zip = null;
				for (int i = 0; i < zipArray.length(); i++) {
					String type = zipArray.get(i).toString();
					// System.out.println("type: " + type);
					if (type.contains("[\"postal_code\"]")) {
						zip = zipArray.getJSONObject(i).get("short_name").toString();
					}
				}
				//will probably need to fix this later
				String latString = myResponse.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").get("lat").toString();
				String longString = myResponse.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").get("lng").toString();

				// System.out.println(zip);
				// System.out.println(latString);
				// System.out.println(longString);

				if (zeroForZip == 0) {
					return zip;
				} else {
					return latString + "," + longString;
				}
			} catch (Exception e) {
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
			//iterates through address components array and finds the one with postal code
			String state = null;
            JSONArray zipArray = myResponse.getJSONArray("results").getJSONObject(0).getJSONArray("address_components");
            for(int i = 0; i < zipArray.length(); i++){
            	String type = zipArray.get(i).toString();
            	// System.out.println("type: " + type);
				if(type.contains("administrative_area_level_1")){
					state = zipArray.getJSONObject(i).get("long_name").toString();
					// System.out.println(state);
				}
			}
            //String state = myResponse.getJSONArray("results").getJSONObject(0).getJSONArray("address_components").getJSONObject(4).get("long_name").toString();
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
	
	
	// @GetMapping("/api/AQIData/{address}")
    // public String getAQIData(@PathVariable String address) throws IOException {
	// 	/**
	// 	 * zipCode check is fine, only calls getLocation
	// 	 */
	// 	// Get zipcode from address
	// 	// address = "100 Orvieto Cove";
	// 	System.out.println(address);
	// 	String zipCode = getLocation(address.replaceAll("%20", " "), 0);


	// 	if(!zipCode.equals("Please narrow search")) {

	// 		String document = SpringAndReactApplication.queryMongoDB("air", zipCode);
	// 		if (document != null && !document.isEmpty()) {
	// 			return document;
	// 		} else {
	// 			// get JSON
	// 			String url = "https://airnow.gov/index.cfm?action=airnow.local_city&zipcode=" + zipCode + "&submit=Go";
	// 			Document doc = Jsoup.connect(url).get();
	// 			Elements test = doc.select("table[class=TblInvisible]");
	// 			Elements body = test.select("tbody");
	// 			Elements td = body.select("td");
	// 			JSONObject json = new JSONObject();
	// 			//return td.get(2).text();
	// 			Set<String> possibleValues = new HashSet<String>(Arrays.asList(new String[]{"Good", "Moderate", "Unhealthy for Sensitive Groups", "Unhealthy", "Very Unhealthy", "Hazardous"}));
	// 			HashMap<String, String> toStore = new HashMap<>();
	// 			for (int i = 0; i < td.size(); i++) {
	// 				String currentWord = td.get(i).text();
	// 				if (possibleValues.contains(currentWord) && i != td.size() - 1) {
	// 					String currentDetail = td.get(i + 1).text();
	// 					if (currentDetail.equals("Ozone")) {
	// 						json.put("Ozone", td.get(i + 2).text());
	// 						toStore.put("Ozone", td.get(i + 2).text());
	// 					} else if (currentDetail.equals("Particles (PM2.5)")) {
	// 						json.put("PM2.5", td.get(i + 2).text());
	// 						toStore.put("PM2.5", td.get(i + 2).text());
	// 					} else if (currentDetail.equals("Particles (PM10)")) {
	// 						json.put("PM10", td.get(i + 2).text());
	// 						toStore.put("PM10", td.get(i + 2).text());
	// 					}
	// 				}
	// 			}
	// 			SpringAndReactApplication.writeToMongoDB("air", zipCode, toStore);
	// 			return json.toString();
	// 		}
	// 	}else{
	// 		return "{}";
	// 	}
	// }

	// @GetMapping("/api/waterData/{address}")
    // public String getWaterData(@PathVariable String address){

    // 	// Get zipcode from address
    // 	// address = "100 Orvieto Cove";

	// 	String zipCode = getLocation(address.replaceAll("%20", " "), 0);
	// 	String checkDoc = SpringAndReactApplication.queryMongoDB("water", zipCode, true);

	// 	if(!zipCode.equals("Please narrow search")) {

	// 		if (checkDoc != null && !checkDoc.isEmpty()) {
	// 			return checkDoc;
	// 		} else {
	// 			JSONObject responseZip = new JSONObject();
	// 			JSONArray responseContaminants = new JSONArray();
	// 			org.bson.Document toPut = new org.bson.Document();
	// 			toPut.put("zipcode", zipCode);
	// 			int count = 0;
	// 			final String url =
	// 					"https://www.ewg.org/tapwater/search-results.php?zip5=" + zipCode + "&searchtype=zip";
	// 			try {


	// 				final Document document = Jsoup.connect(url).get();
	// 				Elements linkToData = document.select(".primary-btn");
	// 				String dataUrl = linkToData.attr("href");
	// 				//testing to see if url is correct
	// 				// System.out.println(dataUrl);
	// 				final Document contaminantDoc = Jsoup.connect("https://www.ewg.org/tapwater/" + dataUrl).get();
	// 				//for each grid item (contaminant)
	// 				for (Element item : contaminantDoc.select(".contaminant-name")) {
	// 					JSONObject tempContamObject = new JSONObject();
	// 					String contam = item.select("h3").text();
	// 					String level = item.select(".detect-times-greater-than").text();
	// 					tempContamObject.put("contaminant", contam);
	// 					tempContamObject.put("level", level);
	// 					toPut.append("contaminant", contam);
	// 					toPut.append("level", level);
	// 					responseContaminants.put(tempContamObject);
	// 					// System.out.println(item.select("h3").text());
	// 					// System.out.println(item.select(".detect-times-greater-than").text());    //this number is the # of times over the EWG health guideline limit
	// 				}
	// 				responseZip.put("zipcode", zipCode);
	// 				responseZip.put("contaminants", responseContaminants);
	// 				System.out.println("trying to write to Database");
	// 				org.bson.Document test = org.bson.Document.parse(responseZip.toString());
	// 				SpringAndReactApplication.writeToMongoDB("water", test);
	// 				return responseZip.toString();
	// 			} catch (Exception e) {
	// 				e.printStackTrace();
	// 			}


	// 			//was commented out before
	// 			SpringAndReactApplication.writeToMongoDB("water", toPut);
	// 			return responseZip.toString();
	// 		}
	// 	}else{
	// 		return "{}";
	// 	}

	// }

    // @GetMapping("/api/naturalDisasters/{address}")
    // public String getNaturalDisasterData(@PathVariable String address) throws IOException{
    // 	try {
	// 		MongoClientURI uri = new MongoClientURI(
	// 				"mongodb://nikhilarora:soft461datatest@cluster0-shard-00-00-kvrlc.gcp.mongodb.net:27017,cluster0-shard-00-01-kvrlc.gcp.mongodb.net:27017,cluster0-shard-00-02-kvrlc.gcp.mongodb.net:27017/test?ssl=true&replicaSet=Cluster0-shard-0&authSource=admin&retryWrites=true&w=majority");
	// 		MongoClient mongoClient = new MongoClient(uri);
	// 		@SuppressWarnings("deprecation")
	// 		DB database = mongoClient.getDB("environmentnow");
	// 		DBCollection collection = database.getCollection("disasters");

	// 		// address= "100 Orvieto Cove";

	// 		String location = reverseLocation(address);
	// 		if (!location.equals("Please narrow search")) {

	// 			BasicDBObject query = new BasicDBObject("Location", location);
	// 			query.toJson();
	// 			DBCursor test = collection.find(query);
	// 			int storm = 0;
	// 			int earthquake = 0;
	// 			int wildfire = 0;
	// 			int flood = 0;
	// 			int drought = 0;
	// 			int extremeTemp = 0;
	// 			int landslide = 0;
	// 			int volcanicActivity = 0;
	// 			int epidemic = 0;
	// 			while (test.hasNext()) {
	// 				String document = test.next().toString();
	// 				if (document.contains("Storm")) {
	// 					storm++;
	// 				}
	// 				if (document.contains("Earthquake")) {
	// 					earthquake++;
	// 				}
	// 				if (document.contains("Wildfire")) {
	// 					wildfire++;
	// 				}
	// 				if (document.contains("Flood")) {
	// 					flood++;
	// 				}
	// 				if (document.contains("Drought")) {
	// 					drought++;
	// 				}
	// 				if (document.contains("Extreme temperature")) {
	// 					extremeTemp++;
	// 				}
	// 				if (document.contains("Landslide")) {
	// 					landslide++;
	// 				}
	// 				if (document.contains("Volvanic activity")) {
	// 					volcanicActivity++;
	// 				}
	// 				if (document.contains("Epidemic")) {
	// 					epidemic++;
	// 				}
	// 			}
	// 			String returnString = "";
	// 			JSONObject json = new JSONObject();
	// 			// if (storm!=0) {
	// 			// 	returnString = returnString + "Storm: " + storm + "\n";
	// 			// 	json.put("Storm", storm);
	// 			// }
	// 			// if (earthquake!=0) {
	// 			// 	returnString = returnString + "Earthquake: " + earthquake + "\n";
	// 			// 	json.put("Earthquake", earthquake);
	// 			// }
	// 			// if (wildfire!=0) {
	// 			// 	returnString = returnString + "Wildfire: " + wildfire + "\n";
	// 			// 	json.put("Wildfire", wildfire);
	// 			// }
	// 			// if (flood!=0) {
	// 			// 	returnString = returnString + "Flood: " + flood + "\n";
	// 			// 	json.put("Flood", flood);
	// 			// }
	// 			// if (drought!=0) {
	// 			// 	returnString = returnString + "Drought: " + drought + "\n";
	// 			// 	json.put("Drought", drought);
	// 			// }
	// 			// if (extremeTemp!=0) {
	// 			// 	returnString = returnString + "Extreme Temperature: " + extremeTemp + "\n";
	// 			// 	json.put("Extreme Temperature", extremeTemp);
	// 			// }
	// 			// if (landslide!=0) {
	// 			// 	returnString = returnString + "Land Slide: " + landslide + "\n";
	// 			// 	json.put("Land Slide", landslide);
	// 			// }
	// 			// if (volcanicActivity!=0) {
	// 			// 	returnString = returnString + "Volcanic Activity: " + volcanicActivity + "\n";
	// 			// 	json.put("Volcanic Activity", volcanicActivity);
	// 			// }
	// 			// if (epidemic!=0) {
	// 			// 	returnString = returnString + "Epidemic: " + epidemic + "\n";
	// 			// 	json.put("Epidemic", epidemic);
	// 			// }
	// 			json.put("Storm", storm);
	// 			json.put("Earthquake", earthquake);
	// 			json.put("Wildfire", wildfire);
	// 			json.put("Flood", flood);
	// 			json.put("Drought", drought);
	// 			json.put("Extreme Temperature", extremeTemp);
	// 			json.put("Land Slide", landslide);
	// 			json.put("Volcanic Activity", volcanicActivity);
	// 			json.put("Epidemic", epidemic);

	// 			// return returnString;
	// 			return json.toString();
	// 		}else{
	// 			return "Failed to query disaster data";
	// 		}
	// 	}
    // 	catch (Exception e) {
    //         e.printStackTrace();
	// 	}
    // 	return "Failed to query disaster data";
	// }



	// template mapping
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
}   
