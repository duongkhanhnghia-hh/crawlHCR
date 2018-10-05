package connectSQLite;

import model.Domain;
import model.Sample;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ActDomain {

    //insert list of domain to database
    public static boolean insertDomainList(List<Domain> domainList) {
        String sql = "INSERT INTO Domain(domain_name,domain_url) VALUES(?,?)";
        try (Connection connection = ConnectSQLite.getConnection()) {
            connection.setAutoCommit(false);
            Iterator<Domain> domainIterator = domainList.iterator();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            while (domainIterator.hasNext()) {
                Domain domain = domainIterator.next();
                preparedStatement.setString(1, domain.getName());
                preparedStatement.setString(2, domain.getUrl());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            connection.commit();
            System.out.println("Saving domain Successed");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Saving domain Failed");
            return false;
        }
    }

    // get all of domain in database
    public static List<Domain> getAllDomain(){
        String sql = "SELECT * FROM Domain ";
        List<Domain> domainList = new ArrayList<>();
        Domain domain = null;
        try(Connection connection = ConnectSQLite.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql)){
            while (rs.next()){
                domain = new Domain( rs.getString("domain_name"), rs.getString("domain_url"));
                domainList.add(domain);
            }
            return  domainList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // get all of domain and all of exercise in database
    public static List<Domain> getAllDomainAndEx(){
        String sql = "SELECT * FROM Domain";
        List<Domain> domainList = new ArrayList<>();
        Domain domain = null;
        try(Connection connection = ConnectSQLite.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql)){
            while (rs.next()){
                domain = new Domain( rs.getString("domain_name"), rs.getString("domain_url"),ActEx.getListExByDomain(rs.getString("domain_name")));
                domainList.add(domain);
            }
            return  domainList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
