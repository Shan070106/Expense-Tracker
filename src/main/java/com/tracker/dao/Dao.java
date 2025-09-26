package com.tracker.dao;

import com.tracker.util.DBConnection;
import com.tracker.model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Dao {

    private static final String C_SELECT_ALL = "SELECT * FROM category;";
    private static final String C_INSERT = "INSERT INTO category(cid,cname,count) VALUES(?,?,?);";    
    private static final String C_UPDATE = "UPDATE category SET cname = ?,count = ? WHERE cid = ?;";
    private static final String C_DELETE = "DELETE FROM category WHERE id = ?;";
    private static final String C_FILTER = "SELECT * FROM category WHERE cname = ?;";
    private static final String C_AMOUNT = "SELECT SUM(amount) FROM category WHERE cid = ?;";
    private static final String C_COUNT =  "SELECT COUNT(*) FROM category WHERE cid = ?;";    
    // private static final String = ";";

    private Category getCategory(ResultSet resultSet) throws SQLException {
        int cid = resultSet.getInt("cid");
        String cname = resultSet.getString("cname");
        int count = resultSet.getInt("count");
        return new Category(cid, cname);
    }
    public List<Category> getAllCategories() throws SQLException{
        List<Category> categories = new ArrayList<>();
        try (
                Connection connection = DBConnection.getDBConnection();
                PreparedStatement statement = connection.prepareStatement(C_SELECT_ALL);
            ) {
                ResultSet resultSet = statement.executeQuery();

                while(resultSet.next()){
                    categories.add(getCategory(resultSet));
                }
            } 
            return categories;
    }

    public int createCategory(Category category) throws SQLException{
        try (
            Connection connection = DBConnection.getDBConnection();
            PreparedStatement statment = connection.prepareStatement(C_INSERT);
        ) {
            statment.setString(1, category.getCname());
            statment.setInt(2, category.getCid());
            statment.setInt(3, 1);

            int rowsffected = statment.executeUpdate();

            if(rowsffected == 0)
                throw new SQLException("Creating a new category failed, no rows affected");
            
            try (ResultSet generatedKeys = statment.getGeneratedKeys()) {
                if(generatedKeys.next())
                    return generatedKeys.getInt(1);
                else    
                    throw new SQLException("Creating category failed, no ID failed obtained");
            } 
        } 
    }
    
    public int getCategoricalAmount(int cid) throws SQLException{
        int amount;
        try (
            Connection connection = DBConnection.getDBConnection();
            PreparedStatement statement = connection.prepareStatement(C_AMOUNT);
        ) {
            statement.setInt(1, cid);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                amount = resultSet.getInt(1);
            } else {
                amount = 0;
            }
        } 
        return amount;
    }

    public int getCategoricalCount(int cid) throws SQLException{
        int count;
        try (
            Connection connection = DBConnection.getDBConnection();
            PreparedStatement statement = connection.prepareStatement(C_COUNT);
        ) {
            statement.setInt(1, cid);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            } else {
                count = 0;
            }
        } 
        return count;
    }
}
