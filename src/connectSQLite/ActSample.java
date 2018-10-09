package connectSQLite;

import model.Sample;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ActSample {

    //insert list of sample with field: ex_id, sample_input, sample_output, explanation
    public static boolean insertListSample(List<Sample> sampleList) {
        String sql = "INSERT INTO Sample(ex_id,sample_input,sample_output,explanation) VALUES(?,?,?,?)";
        try (Connection connection = ConnectSQLite.getConnection()) {
            connection.setAutoCommit(false);
            Iterator<Sample> sampleIterator = sampleList.iterator();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            while (sampleIterator.hasNext()) {
                Sample sample = sampleIterator.next();
                preparedStatement.setInt(1, sample.getEx_id());
                preparedStatement.setString(2, sample.getSample_input());
                preparedStatement.setString(3, sample.getSample_output());
                preparedStatement.setString(4, sample.getExplanation());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            connection.commit();
            System.out.println("Saving sample Successed");
            return true;
        } catch (SQLException e) {
            System.out.println("Saving sample Failed");
            return false;
        }
    }

    // get max exercise id in sample table
    public static int getMaxExID(){
        String sql = "SELECT ex_id FROM Sample ORDER BY ex_id DESC LIMIT 1";
        try(Connection connection = ConnectSQLite.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql)){
            if (rs.next()) {
                return rs.getInt("ex_id");
            }else {
                return -1;
            }
        } catch (SQLException e) {
            System.out.println("Get Max Ex_Id in Sample failed");
        }
        return -1;
    }

    // get list of sample bay exercise id
    public static List<Sample> getSampleListByExID(int ex_id){
        String sql = "SELECT * FROM Sample WHERE ex_id =" + ex_id;
        List<Sample> sampleList = new ArrayList<>();
        Sample sample = null;
        try(Connection connection = ConnectSQLite.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql)){
            while (rs.next()){
                sample = new Sample(rs.getInt("ex_id"), rs.getString("sample_input"),
                                    rs.getString("sample_output"), rs.getString("explanation"));
                sampleList.add(sample);
            }
            return  sampleList;
        } catch (SQLException e) {
            System.out.println("Get Sample List failed: " + ex_id);
        }
        return null;
    }

}
