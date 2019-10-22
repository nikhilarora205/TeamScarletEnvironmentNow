package textInputSubmitJava;

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

public class Main {

	public static void main(String[] args) {
		
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
            
//            JSONObject myResponse = new JSONObject(content.toString());
            
//            System.out.println(myResponse);
            String result = content.toString();
//            System.out.println(result);

            JSONObject myResponse = new JSONObject(result.toString());

            
            String testing = myResponse.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").toString();
            String latString = myResponse.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").get("lat").toString();
            String longString = myResponse.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").get("lng").toString();
//            System.out.println(testing);
//            System.out.println(latString+","+longString);
            
            // String humid = myResponse.getJSONObject("currently").getString("humidity");
            // String wind = myResponse.getJSONObject("currently").getString("windSpeed");
            // String precip = myResponse.getJSONObject("currently").getString("precipProbability");

            // Plug in coordinates to get request
            double latitude = Double.parseDouble(latString);
            double longitude = Double.parseDouble(longString);

            // https://www.adt.com/natural-disasters?30.307983936955342,-97.75340139999997,9 --> Austin

            Document doc = Jsoup.connect("https://www.adt.com/natural-disasters?" +latitude+","+longitude+",9").get();
            System.out.println(doc);

            // parse data after getting table populated

        } 
        catch (Exception e) {
            e.printStackTrace();
		}
		
	}
	
	public void submittingForm() throws Exception {
	    try (final WebClient webClient = new WebClient()) {

	        // Get the first page
	        final HtmlPage page1 = webClient.getPage("https://www.adt.com/natural-disasters");
	        //wait some time???
	        //??????

	        // Get the form that we are dealing with
	        // The form on the website does not have a button
	        //2 options: 
	        //1. simulate "enter" keypress within text input box
	        //2. create button and append it to the form
	        DomElement e = page1.getElementById("pac-input");							//2 separate trials
	        DomElement eHtml = page1.getHtmlElementById("pac-input");					//(both should work)
	        final HtmlForm form1 = (HtmlForm) e;
	        final HtmlForm form2 = (HtmlForm) eHtml;
	       
	        HtmlElement button = (HtmlElement) page1.createElement("button");			//create button to submit
	        button.setAttribute("type", "submit");
	        
	        form1.appendChild(button);

	        final HtmlSubmitInput button1 = (HtmlSubmitInput) button;
	        //final HtmlTextInput textField = form.getInputByName("userid");

	        // Change the value of the text field
	        //textField.type("houston Texas");

	        // Now submit the form by clicking the button and get back the second page.
	        //final HtmlPage page2 = button.click();
	    }
	}
}
