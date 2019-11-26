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
import com.google.gson.*;
import org.bson.*;

import java.util.*;
import java.io.*;
import java.net.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import javax.xml.crypto.Data;
import java.util.List;
import java.util.concurrent.TimeUnit;



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

	@GetMapping("/api/getValid/{address}")
	public String getValid(@PathVariable String address){
    	return(new Address((address)).isValid());
	}

	@GetMapping("/api/getData/{address}")
	public String getData(@PathVariable String address){
    	Address addressObj = new Address(address);
    	addressObj.getAddressInformation();
    	JSONObject aqiData, waterData, environmentalData, allergenData;
    	try {
			 aqiData = DataRetrieval.getAQIData(addressObj);
			 System.out.println("getting data: " + aqiData.toString());
			 waterData = DataRetrieval.getWaterData(addressObj);
			 environmentalData = DataRetrieval.getNaturalDisasterData(addressObj);
			 allergenData = DataRetrieval.getAllergenData(addressObj);
			JSONArray jsonArray = new JSONArray();
			jsonArray.put(0, aqiData);
			jsonArray.put(1, waterData);
			jsonArray.put(2, environmentalData);
			jsonArray.put(3, allergenData);
			return jsonArray.toString();

		}
    	catch(Exception e){
    		e.printStackTrace();
		}



    	return null;
	}

    /*
    Idk what this code below does
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
