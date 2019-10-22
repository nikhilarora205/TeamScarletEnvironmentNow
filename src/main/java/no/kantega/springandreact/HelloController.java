package no.kantega.springandreact;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
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
    public ArrayList<String> getOzoneLevel(String url) throws IOException {
    	url = "https://airnow.gov/index.cfm?action=airnow.local_city&mapcenter=0&cityid=242";
    	Document doc = Jsoup.connect(url).get();
    	Elements rating = doc.select("table.AQData");
    	ArrayList<String> toReturn = new ArrayList<>(); 
    	Elements body = rating.select("tbody");
    	Elements rows = body.select("tr");
    	for(Element row : rows) {
    		toReturn.add(row.select("th").text());
    		toReturn.add(row.select("td").text());
    	}
    	//System.out.println(rating.toString());
    	return toReturn;
    }
}

