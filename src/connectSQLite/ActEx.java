package connectSQLite;

import model.Exercise;
import model.User_Ex;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ActEx {
    /* Note:
        Base exercise have fields: name, level, max_score, success_rate, link_hcr (link exercise in Hackerrank), slug, domain_name
        Detail exercise have fields: name, level, max_score, success_rate, link_hcr (link exercise in Hackerrank),
                                    slug, domain_name, problem, input format, output format, constraints
     */


    //insert list of base exercise into database
    public static boolean insertListEx(List<Exercise> exerciseList){
        String sql = "INSERT INTO Exercise(name, level, max_score, success_rate, link_hcr, slug, domain_name) VALUES(?,?,?,?,?,?,?)";
        try(Connection connection = ConnectSQLite.getConnection()
        ){
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < exerciseList.size(); i++) {
                preparedStatement.setString(1, exerciseList.get(i).getName());
                preparedStatement.setString(2, exerciseList.get(i).getLevel());
                preparedStatement.setDouble(3, exerciseList.get(i).getMax_score());
                preparedStatement.setDouble(4, exerciseList.get(i).getSuccess_rate());
                preparedStatement.setString(5, exerciseList.get(i).getLink_hcr());
                preparedStatement.setString(6, exerciseList.get(i).getSlug());;
                preparedStatement.setString(7, exerciseList.get(i).getDomain_name());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            connection.commit();
            System.out.println("Saving Exercise success!");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Saving Exercise faild!");
            return false;
        }
    }

    //select number of exercise with domain in database
    public static int getNumberExDBWithDomain(String domain){
        String sql = "SELECT COUNT(*) FROM Exercise WHERE domain_name = '" + domain + "'";
        try(Connection connection = ConnectSQLite.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql)){
            return  rs.getInt("COUNT(*)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // get max id of exercise in database
    public static int getMaxExID(){
        String sql = "SELECT id FROM Exercise ORDER BY id DESC LIMIT 1";
        try(Connection connection = ConnectSQLite.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql)){
            return  rs.getInt("id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    //get list of base exercise in database from start to end
    public static List<Exercise> getListExFromTo(int start, int end){
        String sql = "SELECT * FROM Exercise WHERE id>" +(start-1) + " AND id<" + (end+1) ;
        List<Exercise> exerciseList = new ArrayList<>();
        Exercise exercise;
        try(Connection connection = ConnectSQLite.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql)){
            while (rs.next()) {
                exercise = new Exercise(rs.getInt("id"), rs.getString("slug"), rs.getString("link_hcr"));
                exerciseList.add(exercise);
            }
            return exerciseList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //get all of base exercise in database
    public static List<Exercise> getListEx(){
        String sql = "SELECT * FROM Exercise";
        List<Exercise> exerciseList = new ArrayList<>();
        Exercise exercise;
        try(Connection connection = ConnectSQLite.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql)){
            while (rs.next()) {
                exercise = new Exercise(rs.getInt("id"), rs.getString("slug"), rs.getString("link_hcr"));
                exerciseList.add(exercise);
            }
            return exerciseList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //insert detail of detail exercise into database
    public static boolean insertExDetail(List<Exercise> exerciseList){
        String sql = "UPDATE Exercise SET problem=?,input_format=?,constraints=?,ouput_format=? WHERE id=?";
        try(Connection connection = ConnectSQLite.getConnection()){
            connection.setAutoCommit(false);
            Iterator<Exercise> exerciseIterator = exerciseList.iterator();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            while (exerciseIterator.hasNext()){
                Exercise exercise = exerciseIterator.next();
                preparedStatement.setString(1, exercise.getProblem());
                preparedStatement.setString(2, exercise.getInput_format());
                preparedStatement.setString(3, exercise.getConstraints());
                preparedStatement.setString(4, exercise.getOuput_format());
                preparedStatement.setInt(5, exercise.getId());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            connection.commit();
            System.out.println("Saving Ex_Detail Successed");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Saving Ex_Detail Failed");
            return false;
        }
    }

    // get detail exercise from 0 to number-1
    public static List<Exercise> getNumExListDetail(int num){
        String sql = "";
        if (num != 0) {
            sql = "SELECT * FROM Exercise LIMIT " + num;
        }else {
            sql = "SELECT * FROM Exercise";
        }
        List<Exercise> exerciseList = new ArrayList<>();
        Exercise exercise;
        try(Connection connection = ConnectSQLite.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql)){
            while (rs.next()) {
                exercise = new Exercise(rs.getInt("id"), rs.getString("name"), rs.getString("level")
                        , rs.getDouble("max_score"), rs.getDouble("success_rate"), rs.getString("problem"),
                        rs.getString("input_format"), rs.getString("constraints"), rs.getString("ouput_format"));
                exerciseList.add(exercise);
            }
            return exerciseList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //get detail exercise by domain
    public static List<Exercise> getListExByDomain(String domain_name){
        String sql = "SELECT * FROM Exercise WHERE domain_name = '" + domain_name + "'";
        List<Exercise> exerciseList = new ArrayList<>();
        Exercise exercise;
        try(Connection connection = ConnectSQLite.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql)){
            while (rs.next()) {
                exercise = new Exercise(rs.getInt("id"), rs.getString("name"), rs.getString("level")
                        , rs.getDouble("max_score"), rs.getDouble("success_rate"), rs.getString("problem"),
                        rs.getString("input_format"), rs.getString("constraints"), rs.getString("ouput_format"),
                        rs.getString("domain_name"));
                exerciseList.add(exercise);
            }
            return exerciseList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // get list of base exercise have not detail
    public static List<Exercise> getExNotDetail(){
        String sql = "SELECT * FROM Exercise WHERE problem = '' or problem is null ";
        List<Exercise> exerciseList = new ArrayList<>();
        Exercise exercise;
        try(Connection connection = ConnectSQLite.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql)){
            while (rs.next()) {
                exercise = new Exercise(rs.getInt("id"), rs.getString("slug"), rs.getString("link_hcr"));
                exerciseList.add(exercise);
            }
            return exerciseList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
