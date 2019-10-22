package no.kantega.springandreact;

import org.json.JSONArray;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
    
    @GetMapping("/api/ozoneLevel")
    public String getOzoneLevel(String url) throws IOException {
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

    @GetMapping("/api/waterData")
    public String getWaterData(){

		JSONObject responseZip = new JSONObject();
		JSONArray responseContaminants = new JSONArray();

		//Temporary zip code, should call Google api for location specifics
		String zipCode = "76103";
		int count = 0;

		final String url =
				"https://www.ewg.org/tapwater/search-results.php?zip5="+zipCode+"&searchtype=zip";

//		final String url =
//				"https://mytapwater.org/zip/"+zipCode+"/";

		try{
			final Document document = Jsoup.connect(url).get();

			Elements linkToData =  document.select(".primary-btn");
			String dataUrl = linkToData.attr("href");

			//testing to see if url is correct
			System.out.println(dataUrl);

			JSONObject tempContamObject = new JSONObject();

			final Document contaminantDoc = Jsoup.connect("https://www.ewg.org/tapwater/"+dataUrl).get();

			//for each grid item (contaminant)
			for(Element item: document.select("div.contaminants-grid")){
				System.out.println(item.toString());
			}




//			for(Element row: document.select(
//					"table.search-results-table tr"
//			)){
//				if(row.select("td:nth-of-type(1)").text().equals("")){
//					continue;
//				}else{
//					//Element cell = row.select("td:nth-of-type(1)").attr("href");
//					String waterProvider = row.select("td:nth-of-type(1)").text();
//
//					Elements link = row.select("td:nth-of-type(1) > a");
//
//					final String contaminantUrl = link.attr("href");
//
//					//only getting first 10;
//					count++;
//
//					try{
//						if(count < 10) {
//							JSONObject tempContamObject = new JSONObject();
//							//print out waterProvider
//							System.out.print(waterProvider + ": -->");
//							//print to look at contaminant URL
//							System.out.println(contaminantUrl);
//
//							final Document contaminantDoc = Jsoup.connect(contaminantUrl).get();
//
//							Element contaminantRow = contaminantDoc.select("tr.exceeded").first();
//							Element contaminant = contaminantDoc.select("div.contaminant").select("h4").first();
//
//							String year = contaminantRow.select("td:nth-of-type(1)").text();
//							String level = contaminantRow.select("td:nth-of-type(5)").text();
//
//							tempContamObject.put("contaminant", contaminant.id().toString());
//							tempContamObject.put("level", level);
//							tempContamObject.put( "year", year);
//
//							responseContaminants.put(tempContamObject);
//
//							System.out.println("Contaminant: " + contaminant.id().toString() + ", Year: " + year + ", Test Result: " + level);
//						}
//
//					}
//					catch (Exception e2){
//						System.out.println("Contaminant: N/A, Year: N/A, Test Result: N/A");
//					}
//
//				/*
//
//						{
//							"zipcode":76103,
//							"providers": [
//								{
//									"name" : "City of Fort Worth",
//									"contaminants": [
//										{ "name" : "chlorate",
//										  "level" : "<20"
//										 },
//										{ "name" : "strontium",
//										  "level" : "200"
//										 }
//										]
//								 },
//
//								 {
//									"name" : "City of Arlington",
//									"contaminants": [
//										{ "name" : "chlorate",
//										  "level" : "<20"
//										 },
//										{ "name" : "strontium",
//										  "level" : "200"
//										 }
//										]
//								 }
//							 ]
//						 }
//
//
//				 */
//
//				}
//			}
//
//			responseZip.put("zipcode", zipCode);
//			responseZip.put("contaminants", responseContaminants);
//
		}
		catch (Exception e){
			e.printStackTrace();
		}

		System.out.println(responseZip.toString());

		return responseZip.toString();

	}


}

