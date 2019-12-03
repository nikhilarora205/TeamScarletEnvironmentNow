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
}   
