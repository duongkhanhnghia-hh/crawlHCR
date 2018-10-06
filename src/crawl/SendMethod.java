package crawl;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SendMethod {

    // get response of url
    public static String getResponse(String url_st) {
        URL url = null;
        try {
            url = new URL(url_st);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            connection.setDoOutput(true);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String input;
            StringBuffer response = new StringBuffer();
            while ((input = bufferedReader.readLine()) != null) {
                response.append(input);
            }
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    // get response of url with crawl problem of exercise faild
    public static String getResponse2(String slug){
        String url = "https://www.hackerrank.com/rest/contests/master/challenges/" + slug + "?&_=1538734309432";
        String res = getResponse(url);
        try {
            JSONObject jsonObject = new JSONObject(res);
            return ((JSONObject)(jsonObject.get("model"))).get("body_html").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

}
