package connectSQLite;

import model.Exercise;
import model.Sample;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ActUser {

    // insert list of users with field: user_name, country
    public static List<User> insertListUser(List<User> userList){

        String sql = "INSERT INTO User(user_name, country) VALUES(?,?)";
        try(Connection connection = ConnectSQLite.getConnection()
        ){
            connection.setAutoCommit(false);
            Iterator<User> userIterator = userList.iterator();

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            while(userIterator.hasNext()){
                User user = userIterator.next();
                int idDB = checkUserInDB(user.getUser_name());
                if(idDB== -1) {
                    preparedStatement.setString(1, user.getUser_name());
                    preparedStatement.setString(2, user.getCountry());
                    preparedStatement.addBatch();
                }
            }
            preparedStatement.executeBatch();
            connection.commit();
            for (int i = 0; i < userList.size(); i++){
                userList.get(i).setId(checkUserInDB(userList.get(i).getUser_name()));
            }
            System.out.println("Saving User Successed");
            return  userList;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Saving User Failed");
        }
        return  null;
    }

    // check user have/have not in database by user_name
    public static int checkUserInDB(String username){
        String sql = "SELECT * FROM User WHERE user_name='" + username+"'";
        try(Connection connection = ConnectSQLite.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql)){
            if (!rs.next()){
                return -1;
            }
            return rs.getInt("user_id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // get user by user_id
    public static User getUserByUserID(int id){
        String sql = "SELECT * FROM User WHERE user_id =" + id;
        User user = null;
        try(Connection connection = ConnectSQLite.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql)){
            user = new User(rs.getInt("user_id"), rs.getString("user_name"),
                            rs.getString("country"));
            return  user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }





}
