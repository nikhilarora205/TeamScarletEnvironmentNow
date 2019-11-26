package no.kantega.springandreact;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Address {
    String address;
    String zipCode;
    String state;
    JSONObject geocoderResponse;


    public Address(String address ){
        this.address = address;
        initialize();
    }

    public void initialize() {
        try {
            // Use Google GeoCoder to get coordinates
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
            this.geocoderResponse = myResponse;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String isValid(){
                try {
                    if (geocoderResponse.getJSONArray("results").getJSONObject(0).get("partial_match").toString().equals("true")) {
                        //System.out.println("incorrect address");
                        return "incorrect";
                    }
                } catch (Exception e) {
                    //	System.out.println("address not incorrect");
                }

                try{

                    if(geocoderResponse.getJSONArray("results").getJSONObject(0).getJSONArray("address_components").toString().contains("[\"postal_code\"]")){
                        return "true";
                    }else{
                        return "narrow";
                    }


                }
                catch(Exception e){
                    System.out.println("here");
                    return "narrow";
                }

              //  return "true";
            }




    public void getAddressInformation(){
        findZip();
        findState();
    }
    public void findZip(){
        //iterates through address components array and finds the one with postal code
        JSONArray zipArray = geocoderResponse.getJSONArray("results").getJSONObject(0).getJSONArray("address_components");
        String zip = null;
        for (int i = 0; i < zipArray.length(); i++) {
            String type = zipArray.get(i).toString();
            System.out.println("type: " + type);
            if (type.contains("[\"postal_code\"]")) {
                zip = zipArray.getJSONObject(i).get("short_name").toString();
            }
        }
        this.zipCode = zip;
    }
    public void findState(){
        String state = null;
        JSONArray zipArray = geocoderResponse.getJSONArray("results").getJSONObject(0).getJSONArray("address_components");
        for(int i = 0; i < zipArray.length(); i++){
            String type = zipArray.get(i).toString();
            System.out.println("type: " + type);
            if(type.contains("administrative_area_level_1")){
                state = zipArray.getJSONObject(i).get("long_name").toString();
            }
        }
        this.state = state;
    }


}
