package crawl;

import config.Config;
import connectSQLite.ActEx;
import connectSQLite.ActUser;
import connectSQLite.ActUserEx;
import connectSQLite.ConnectSQLite;
import model.Exercise;
import model.User;
import model.User_Ex;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CrawlLeaderboard {

    // get total leader with a exercise
    public static long getTotalLeader(String ex_name){
        try {
            // crawl leaderboard of exercise by JSON url
            String res = SendMethod.getResponse(Config.LEADER_URL_Start + ex_name + Config.LEADER_URL_Mid1
                    + "0" + Config.LEADER_URL_Mid2 + "10" + Config.LEADER_URL_End);
            // parse response to JSON
            JSONObject jsonObject = new JSONObject(res);
            // get total leader
            int total = Integer.parseInt(jsonObject.get("total").toString());
            return total;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // get all leader board
    // slug and id of exercise
    public static String getAllLeader(String slug, int id){
        System.out.println("Crawling Leaderboard...");
        try {
            System.out.println("get Leaderboard for " + slug);
            // get total leader of this exercise
            long total_leader = getTotalLeader(slug);
            // count variable
            long total_tmp = 0;
            System.out.println("Total Leader: " + total_leader);
            System.out.println("Need Leader: " +  Config.NUM_LEADER_A_EX);
            // create new list to save once
            List<User> userList = new ArrayList<>();
            List<User_Ex> user_exList = new ArrayList<>();

            //if total_tmp < total of leder and total_tmp < number of leader want to save
            while (total_tmp < total_leader && total_tmp < Config.NUM_LEADER_A_EX) {
                // get 100 leader once crawl by JSON url (100 is max)
                String res = SendMethod.getResponse(Config.LEADER_URL_Start + slug + Config.LEADER_URL_Mid1
                        + total_tmp + Config.LEADER_URL_Mid2 + 100 + Config.LEADER_URL_End);
                // parse response to JSON
                JSONObject jsonObject = new JSONObject(res);
                // get array leader
                JSONArray jsonArray = new JSONArray(jsonObject.get("models").toString());
                //with a leader if not deleted then create user (not yet), create user_ex and save it to database
                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject json_tmp = (JSONObject) jsonArray.get(i);
                    if (!json_tmp.getString("hacker").equals("[deleted]")){
                        User user = new User(json_tmp.getString("hacker"), json_tmp.getString("country"));
                        User_Ex user_ex = new User_Ex(-1, id, json_tmp.getDouble("score"), json_tmp.getInt("rank"));
                        userList.add(user);
                        user_exList.add(user_ex);
                    }
                }
                // increase count variable
                total_tmp += 100;
                // if count variable = number want to save one then save to database
                if (total_tmp % Config.NUM_LEADER_SAVE_TMP == 0){
                    System.out.println("Saving Leaderboard " + (total_tmp));
                    // insert user list
                    List<User> userListIS = ActUser.insertListUser(userList);
                    for (int i = 0; i < userListIS.size(); i++){
                        user_exList.get(i).setUser_id(userListIS.get(i).getId());
                    }
                    // insert user_ex list
                    Boolean saveUserEx = ActUserEx.insert_user_ex(user_exList);
                    System.out.println("Save " + (total_tmp) + " User_EX: " + saveUserEx);
                    // if insert user_ex list once failed then insert one by one
                    if (!saveUserEx){
                        Boolean saveOneEx = ActUserEx.insert_user_ex_one(user_exList);
                        System.out.println("Save " + (user_exList.size()) + " User_EX: " + saveUserEx);
                    }
                    userList = new ArrayList<>();
                    user_exList = new ArrayList<>();
                }
            }
            return "";
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }


    // save all leaderboard with all exercise
    public static boolean saveAllLeader(){
        // get number ex have leader in database
        int number = ActUserEx.getMaxExIDOfUserEx()+1;
        //get total exercise
        int end = ActEx.getMaxExID();
        // get list not have leader
        List<Exercise> exerciseList = ActEx.getListExFromTo(number, end);

        System.out.println(exerciseList.size());
        //crawl leader for exercise have not
        for (int i = 0; i < exerciseList.size(); i++){
            getAllLeader(exerciseList.get(i).getSlug(), exerciseList.get(i).getId());
        }
        return true;
    }



}
