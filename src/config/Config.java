package config;

public class Config {
    //URL and number of row save to database

    public static final String DASHBOARD = "https://www.hackerrank.com/dashboard";
    public static final String EX_URL_Start = "https://www.hackerrank.com/rest/contests/master/tracks/";
    public static final String EX_URL_Mid1 = "/challenges?offset=";
    public static final String EX_URL_Mid2 = "&limit=";
    public static final String EX_URL_End = "&track_login=true";
    public static final String path = System.getProperty("user.dir");
    public static final String URL_SQLite = "jdbc:sqlite:" + path + "\\hcr_noData.db";
    public static final String LEADER_URL_Start = "https://www.hackerrank.com/rest/contests/master/challenges/";
    public static final String LEADER_URL_Mid1 = "/leaderboard?offset=";
    public static final String LEADER_URL_Mid2 = "&limit=";
    public static final String LEADER_URL_End = "&include_practice=true";
    public static final int NUM_LEADER_A_EX = 100;
    public static final int NUM_LEADER_SAVE_TMP = 50;
    public static final int NUM_EX_Detail_SAVE_TMP = 50;

}
