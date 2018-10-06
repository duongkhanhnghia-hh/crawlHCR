package crawl;

import config.Config;
import connectSQLite.ActEx;
import connectSQLite.ConnectSQLite;
import model.Domain;
import model.Exercise;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CrawlExList {
    static ConnectSQLite connection = new ConnectSQLite();

    // get total of Ex by crawl json and get total
    public static int getTotalEx(String domain) {
        try {
            // get respone in json link
            String total_st = SendMethod.getResponse(Config.EX_URL_Start + domain + Config.EX_URL_Mid1  + "0" + Config.EX_URL_Mid2 + "10" + Config.EX_URL_End);
            // parse to json
            JSONObject jsonObject = new JSONObject(total_st);
            // get total
            int total = Integer.parseInt(jsonObject.get("total").toString());
            return total;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Crawl list exercise by domain
    public static void getExercise(Domain domain) {
        try {
            System.out.println("Crawling Ex From Domain: " + domain.getName());
            // get number exercise crawled with this domain
            int numberInDB = ActEx.getNumberExDBWithDomain(domain.getName());
            int total_tmp = numberInDB;
            // get total number of domain in Hackerrank
            int total = getTotalEx(domain.getUrl());
            // create new list to save once
            List<Exercise> exerciseList = new ArrayList<>();
            Exercise exercise;

            // if number ex of domain < total ex of domain then crawl
            while (total_tmp < total) {
                // get response with 50 exercise by JSON url (max is 50)
                String response = SendMethod.getResponse(Config.EX_URL_Start + domain.getUrl() + Config.EX_URL_Mid1 +
                                                    total_tmp + Config.EX_URL_Mid2 + 50 + Config.EX_URL_End);
                // increase total_tmp
                total_tmp += 50;
                // parse response to JSON
                JSONObject jsonObject =  new JSONObject(response);
                // get array exercise
                JSONArray jsonArray = new JSONArray(jsonObject.get("models").toString());
                // with a exercise create exercise object and add it to list
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject_tmp = (JSONObject) jsonArray.get(i);
                    String link_hcr = "https://www.hackerrank.com/challenges/" + jsonObject_tmp.get("slug") + "/problem";
                    exercise = new Exercise(jsonObject_tmp.get("name").toString(), jsonObject_tmp.get("difficulty_name").toString(),
                            Double.parseDouble(jsonObject_tmp.get("max_score").toString()),
                            Double.parseDouble(jsonObject_tmp.get("success_ratio").toString()) , link_hcr, jsonObject_tmp.get("slug").toString(), domain.getName());
                    exerciseList.add(exercise);
                }
                // once, save 200 exercise to database
                if(total_tmp % 200 == 0){
                    System.out.println("Saving Ex " + total_tmp);
                    boolean result = ActEx.insertListEx(exerciseList);
                    System.out.println("Save Ex " + result);
                    exerciseList = new ArrayList<>();
                }
            }
            // save extant exercise and print notification
            if(total_tmp % 200 != 0){
                System.out.println("Saving Ex " + exerciseList.size());
                boolean result = ActEx.insertListEx(exerciseList);
                System.out.println("Save Ex " + result);
            }
            System.out.println("Crawl Complete");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
