package no.kantega.springandreact;
import com.google.gson.Gson;
import com.mongodb.*;
import com.mongodb.util.JSON;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.swing.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class DataRetrieval {

    public static JSONObject getAQIData(Address address) throws IOException {
    	// Variables
    	String airString = new String();
		String zipCode = address.zipCode;
		String document = SpringAndReactApplication.queryMongoDB("air", zipCode, true);
		if(document != null && !document.isEmpty()){
			JSONObject convertedObject = new JSONObject(document);
			return convertedObject;
		}else{
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
			System.out.println("duplicate for air");
			JSONObject response = new JSONObject();
			response.put("zipcode", zipCode);
			response.put("airData", jsonArray);
			org.bson.Document toPut = org.bson.Document.parse(response.toString());
			SpringAndReactApplication.writeToMongoDB("air", toPut);
			return response;
		}

	}

    public static JSONObject getAllergenData(Address address) throws IOException {
		String zipCode = address.zipCode;
		String document = SpringAndReactApplication.queryMongoDB("allergen", zipCode, true);
		if (document != null && !document.isEmpty()) {
			JSONObject convertedObject = new JSONObject(document);
			return convertedObject;
		}else {
			String url = "https://weather.com/forecast/allergy/l/" + zipCode;
			Document doc = Jsoup.connect(url).get();
			Elements div = doc.select("section[class=styles__allergyOutlook__3e1L4]");
			Elements div2 = div.select("div[class=styles__allergyOutlookContentGraphMsgQual__25hN1]");
			JSONObject json = new JSONObject();
			HashMap<String, String> toStore = new HashMap<>();
			for(int i = 0; i < div2.size();i++ ) {
				String currentWord = div2.get(i).text();
				if( i == 0 ) {
					json.put("Tree Pollen", currentWord);
					toStore.put("Tree Pollen", currentWord);
				}else if(i == 1) {
					json.put("Grass Pollen", currentWord);
					toStore.put("Tree Pollen", currentWord);

				}else if(i == 2) {
					json.put("Ragweed Pollen", currentWord);
					toStore.put("Tree Pollen", currentWord);
				}
			}
			System.out.println("duplicate for air");
			SpringAndReactApplication.writeToMongoDB("allergen", zipCode, toStore);
			return json;
		}
    }

    public static JSONObject getWaterData(Address address){
		String zipCode = address.zipCode;
		String checkDoc = SpringAndReactApplication.queryMongoDB("water", zipCode, true);
		if (checkDoc != null && !checkDoc.isEmpty()) {
			JSONObject convertedObject = new JSONObject(checkDoc);
			return convertedObject;
		} else {
			JSONObject response = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			final String url =
					"https://www.ewg.org/tapwater/search-results.php?zip5=" + zipCode + "&searchtype=zip";
			try {
				final Document document = Jsoup.connect(url).get();
				Elements linkToData = document.select(".primary-btn");
				String dataUrl;

				if(!linkToData.hasAttr("href")){
					linkToData = document.select(".search-results-table");

					Element tableElement = linkToData.get(0);

					Elements childNode = tableElement.getElementsByAttribute("href");

					Element firstChild = childNode.get(0);

					System.out.println(linkToData.toString());

					dataUrl = firstChild.attr("href");
				}else{
					dataUrl = linkToData.attr("href");
				}

				final Document contaminantDoc = Jsoup.connect("https://www.ewg.org/tapwater/" + dataUrl).get();
				for (Element item : contaminantDoc.select(".contaminant-name")) {
					String contam = item.select("h3").text();
					String level = item.select(".detect-times-greater-than").text();
					if(!level.isEmpty()) {
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

				response.put("zipcode", zipCode);
				response.put("waterData", jsonArray);
				org.bson.Document test = org.bson.Document.parse(response.toString());
				SpringAndReactApplication.writeToMongoDB("water", test);
				return response;
			}
			catch (Exception e){
				e.printStackTrace();
			}
			// System.out.println("It is not getting bar2Data: " + response.toString());
			System.out.println("duplicate for air");

			return response;
		}
	}
    
    public static JSONObject getNaturalDisasterData(Address address) throws IOException {
		String checkDoc = SpringAndReactApplication.queryMongoDB("locations", address.state, true);
		if (checkDoc != null && !checkDoc.isEmpty()) {
			JSONObject convertedObject = new JSONObject(checkDoc);
			return convertedObject;
		} else {
			try {
				MongoClientURI uri = new MongoClientURI(
						"mongodb://nikhilarora:soft461datatest@cluster0-shard-00-00-kvrlc.gcp.mongodb.net:27017,cluster0-shard-00-01-kvrlc.gcp.mongodb.net:27017,cluster0-shard-00-02-kvrlc.gcp.mongodb.net:27017/test?ssl=true&replicaSet=Cluster0-shard-0&authSource=admin&retryWrites=true&w=majority");
				MongoClient mongoClient = new MongoClient(uri);
				@SuppressWarnings("deprecation")
				DB database = mongoClient.getDB("environmentnow");
				DBCollection collection = database.getCollection("disasters");
				String location = address.state;
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
					while (test.hasNext()) {
						String document = test.next().toString();
						if (document.contains("Storm")) {
							storm++;
						}
						if (document.contains("Earthquake")) {
							earthquake++;
						}
						if (document.contains("Wildfire")) {
							wildfire++;
						}
						if (document.contains("Flood")) {
							flood++;
						}
						if (document.contains("Drought")) {
							drought++;
						}
						if (document.contains("Extreme temperature")) {
							extremeTemp++;
						}
						if (document.contains("Landslide")) {
							landslide++;
						}
						if (document.contains("Volvanic activity")) {
							volcanicActivity++;
						}
						if (document.contains("Epidemic")) {
							epidemic++;
						}
					}

					JSONArray jsonArray = new JSONArray();
					JSONObject json = new JSONObject();

					if (storm != 0) {
						JSONObject tempson = new JSONObject();
						tempson.put("y", storm);
						tempson.put("x", "Storm");
						jsonArray.put(tempson);
					}

					if (earthquake != 0) {
						JSONObject tempson = new JSONObject();
						tempson.put("y", earthquake);
						tempson.put("x", "Earthquake");
						jsonArray.put(tempson);
					}

					if (wildfire != 0) {
						JSONObject tempson = new JSONObject();
						tempson.put("y", wildfire);
						tempson.put("x", "Wildfire");
						jsonArray.put(tempson);
					}

					if (flood != 0) {
						JSONObject tempson = new JSONObject();
						tempson.put("y", flood);
						tempson.put("x", "Flood");
						jsonArray.put(tempson);
					}

					if (drought != 0) {
						JSONObject tempson = new JSONObject();
						tempson.put("y", drought);
						tempson.put("x", "Drought");
						jsonArray.put(tempson);
					}

					if (extremeTemp != 0) {
						JSONObject tempson = new JSONObject();
						tempson.put("y", extremeTemp);
						tempson.put("x", "XTemp");
						jsonArray.put(tempson);
					}

					if (landslide != 0) {
						JSONObject tempson = new JSONObject();
						tempson.put("y", landslide);
						tempson.put("x", "Landslide");
						jsonArray.put(tempson);
					}

					if (volcanicActivity != 0) {
						JSONObject tempson = new JSONObject();
						tempson.put("y", volcanicActivity);
						tempson.put("x", "Volcano Act.");
						jsonArray.put(tempson);
					}

					if (epidemic != 0) {
						JSONObject tempson = new JSONObject();
						tempson.put("y", epidemic);
						tempson.put("x", "Epidemic");
						jsonArray.put(tempson);
					}
					JSONObject response = new JSONObject();
					response.put("state", address.state);
					response.put("natData", jsonArray);
					org.bson.Document toPut = org.bson.Document.parse(response.toString());
					System.out.println("duplicate for air");
					SpringAndReactApplication.writeToMongoDB("locations", toPut);
					return response;
				} else {
					JSONObject empty = new JSONObject();
					return empty;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			JSONObject empty = new JSONObject();
			return empty;
		}
	}

}
