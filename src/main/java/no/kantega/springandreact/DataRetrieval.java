package no.kantega.springandreact;

import com.google.gson.Gson;
import com.mongodb.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class DataRetrieval {

    public static JSONObject getAQIData(Address address) throws IOException {
        String zipCode = address.zipCode;
        if(!zipCode.equals("Please narrow search")) {

            String document = SpringAndReactApplication.queryMongoDB("air", zipCode);
            if (document != null && !document.isEmpty()) {
                JSONObject convertedObject = new Gson().fromJson(document, JSONObject.class);
                return convertedObject;
            } else {
                // get JSON
                String url = "https://airnow.gov/index.cfm?action=airnow.local_city&zipcode=" + zipCode + "&submit=Go";
                Document doc = Jsoup.connect(url).get();
                Elements test = doc.select("table[class=TblInvisible]");
                Elements body = test.select("tbody");
                Elements td = body.select("td");
                JSONObject json = new JSONObject();
                //return td.get(2).text();
                Set<String> possibleValues = new HashSet<String>(Arrays.asList(new String[]{"Good", "Moderate", "Unhealthy for Sensitive Groups", "Unhealthy", "Very Unhealthy", "Hazardous"}));
                HashMap<String, String> toStore = new HashMap<>();
                for (int i = 0; i < td.size(); i++) {
                    String currentWord = td.get(i).text();
                    if (possibleValues.contains(currentWord) && i != td.size() - 1) {
                        String currentDetail = td.get(i + 1).text();
                        if (currentDetail.equals("Ozone")) {
                            json.put("Ozone", td.get(i + 2).text());
                            toStore.put("Ozone", td.get(i + 2).text());
                        } else if (currentDetail.equals("Particles (PM2.5)")) {
                            json.put("PM2.5", td.get(i + 2).text());
                            toStore.put("PM2.5", td.get(i + 2).text());
                        } else if (currentDetail.equals("Particles (PM10)")) {
                            json.put("PM10", td.get(i + 2).text());
                            toStore.put("PM10", td.get(i + 2).text());
                        }
                    }
                }
                SpringAndReactApplication.writeToMongoDB("air", zipCode, toStore);
                return json;
            }
        }else{
            return null;
        }
    }

    public static JSONObject getAllergenData(Address address) throws IOException {
        String zipCode = address.address;
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
        return json;
    }

    public static JSONObject getWaterData(Address address){

        // Get zipcode from address
        // address = "100 Orvieto Cove";

        String zipCode = address.zipCode;
        String checkDoc = SpringAndReactApplication.queryMongoDB("water", zipCode, true);

        if(!zipCode.equals("Please narrow search")) {

            if (checkDoc != null && !checkDoc.isEmpty()) {
                JSONObject convertedObject = new Gson().fromJson(checkDoc, JSONObject.class);
                return convertedObject;
            } else {
                JSONObject responseZip = new JSONObject();
                JSONArray responseContaminants = new JSONArray();
                org.bson.Document toPut = new org.bson.Document();
                toPut.put("zipcode", zipCode);
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
                        toPut.append("contaminant", contam);
                        toPut.append("level", level);
                        responseContaminants.put(tempContamObject);
                        // System.out.println(item.select("h3").text());
                        // System.out.println(item.select(".detect-times-greater-than").text());    //this number is the # of times over the EWG health guideline limit
                    }
                    responseZip.put("zipcode", zipCode);
                    responseZip.put("contaminants", responseContaminants);
                    System.out.println("trying to write to Database");
                    org.bson.Document test = org.bson.Document.parse(responseZip.toString());
                    SpringAndReactApplication.writeToMongoDB("water", test);
                    return responseZip;
                } catch (Exception e) {
                    e.printStackTrace();
                }


                //was commented out before
                SpringAndReactApplication.writeToMongoDB("water", toPut);
                return responseZip;
            }
        }else{
            return null;
        }

    }

    public static JSONObject getNaturalDisasterData(Address address) throws IOException{
        try {
            MongoClientURI uri = new MongoClientURI(
                    "mongodb://nikhilarora:soft461datatest@cluster0-shard-00-00-kvrlc.gcp.mongodb.net:27017,cluster0-shard-00-01-kvrlc.gcp.mongodb.net:27017,cluster0-shard-00-02-kvrlc.gcp.mongodb.net:27017/test?ssl=true&replicaSet=Cluster0-shard-0&authSource=admin&retryWrites=true&w=majority");
            MongoClient mongoClient = new MongoClient(uri);
            @SuppressWarnings("deprecation")
            DB database = mongoClient.getDB("environmentnow");
            DBCollection collection = database.getCollection("disasters");

            // address= "100 Orvieto Cove";

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
                return json;
            }else{
                return null;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
