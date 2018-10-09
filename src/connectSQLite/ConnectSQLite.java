package connectSQLite;

import config.Config;

import java.sql.*;

public class ConnectSQLite {

    // create connect to database
     protected static Connection getConnection(){
        String url = Config.URL_SQLite;
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println("Get connection SQLite failed: " + Config.URL_SQLite);
        }
        return  connection;
    }

}
