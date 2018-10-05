package connectSQLite;

import model.Sample;
import model.User;
import model.User_Ex;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ActUserEx {

    // insert list of leaderboard to user_ex: once insert a list
    public static boolean insert_user_ex(List<User_Ex> user_exList){
        String sql = "INSERT INTO USER_EX(user_id,ex_id,score,rank) VALUES(?,?,?,?)";
        try(Connection connection = ConnectSQLite.getConnection()){
            connection.setAutoCommit(false);
            Iterator<User_Ex> user_exIterator = user_exList.iterator();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            while (user_exIterator.hasNext()){
                User_Ex user_ex = user_exIterator.next();
                preparedStatement.setInt(1, user_ex.getUser_id());
                preparedStatement.setInt(2, user_ex.getEx_id());
                preparedStatement.setDouble(3, user_ex.getScore());
                preparedStatement.setInt(4, user_ex.getRank());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            connection.commit();
            System.out.println("Saving User_EX Successed");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Saving User_EX Failed");
            return false;
        }
    }

    // insert list of leaderboard to user_ex one by one
    public static boolean insert_user_ex_one(List<User_Ex> user_exList){
        String sql = "INSERT INTO USER_EX(user_id,ex_id,score,rank) VALUES(?,?,?,?)";
        try(Connection connection = ConnectSQLite.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < user_exList.size(); i++){
                User_Ex user_ex = user_exList.get(i);
                preparedStatement.setInt(1, user_ex.getUser_id());
                preparedStatement.setInt(2, user_ex.getEx_id());
                preparedStatement.setDouble(3, user_ex.getScore());
                preparedStatement.setInt(4, user_ex.getRank());
                preparedStatement.executeUpdate();
            }
            System.out.println("Saving User_EX Successed");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Saving User_EX Failed");
            return false;
        }
    }

    //get list of leaderboard by exercise id
    public static List<User_Ex> getUserExListByExID(int ex_id){
        String sql = "SELECT * FROM User_EX WHERE ex_id =" + ex_id;
        List<User_Ex> userExList = new ArrayList<>();
        User_Ex user_ex = null;
        try(Connection connection = ConnectSQLite.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql)){
            while (rs.next()){
                user_ex = new User_Ex(rs.getInt("user_id"), rs.getInt("ex_id"),
                        rs.getDouble("score"), rs.getInt("rank"));
                userExList.add(user_ex);
            }
            return  userExList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // get max ex_id in user_ex table
    public static int getMaxExIDOfUserEx(){
        String sql = "SELECT ex_id FROM User_Ex ORDER BY ex_id DESC LIMIT 1";
        try(Connection connection = ConnectSQLite.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql)){
            if (rs.next()) {
                return  rs.getInt("ex_id");
            }else {
                return -1;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }


}
