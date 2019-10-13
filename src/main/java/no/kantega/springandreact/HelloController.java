package no.kantega.springandreact;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;

@RestController
public class HelloController {
    @GetMapping("/api/hello")
    public String hello() {
        //return "Hello, the time at the server is now " + new Date() + "\n";
        String myUrl = "https://api.github.com/repos/nikhilarora205/TeamScarletEnvironmentNow/stats/commit_activity";
        //String to place our result in
        String result;
        HttpGetRequest getRequest = new HttpGetRequest();
        result = getRequest.execute(myUrl).get();
        JSONObject myResponse = new JSONObject(result.toString());
        String total = myResponse.getJSONObject("total").toString();
        return total;
    }
}
