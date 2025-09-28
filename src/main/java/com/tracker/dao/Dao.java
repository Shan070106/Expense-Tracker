package com.tracker.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.tracker.model.Category;
import com.tracker.model.Expense;
import com.tracker.util.DBConnection;

public class Dao {

    private static final String C_SELECT_ALL = "SELECT * FROM category;";
    private static final String C_INSERT = "INSERT INTO category(cname) VALUES(?);";    
    private static final String C_UPDATE = "UPDATE category SET cname = ? WHERE cid = ?;";
    private static final String C_DELETE = "DELETE FROM category WHERE cid = ?;";
    private static final String C_FILTER = "SELECT * FROM category WHERE cname = ?;";
    private static final String C_AMOUNT = "SELECT SUM(amount) FROM expense WHERE idc = ?;";
    private static final String C_COUNT =  "SELECT COUNT(*) FROM expense WHERE idc = ?;";    
    private static final String TOTAL_AMOUNT = "SELECT SUM(amount) FROM expense ;";
    private static final String C_FIND = "SELECT count FROM category WHERE cname = ?;";
    private static final String ALL_EXPENSES = "SELECT eid,description,expense.amount,date,cname FROM category RIGHT OUTER JOIN expense ON category.cid = expense.idc;";
    private static final String E_INSERT = "INSERT INTO expense(description,amount,idc) VALUES(?,?,?);";
    private static final String FIND_CID = "SELECT cid FROM category WHERE cname = ?;";

    private Category getCategory(ResultSet resultSet) throws SQLException {
        int cid = resultSet.getInt("cid");
        String cname = resultSet.getString("cname");
        // int count = resultSet.getInt("count");
        return new Category(cid, cname);
    }
     
    private Expense getExpense(ResultSet resultSet) throws SQLException{
        int eid = resultSet.getInt("eid");
        int amount = resultSet.getInt("amount");
        String cname = resultSet.getString("cname");
        String description = resultSet.getString("description");
        LocalDateTime date = resultSet.getTimestamp("date").toLocalDateTime();

        return new Expense(eid,description,amount,cname,date);
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

    public List<Expense> getAllExpenses() throws SQLException{
        List<Expense> expenses = new ArrayList<>();
        try(
            Connection connection = DBConnection.getDBConnection();
            PreparedStatement statment = connection.prepareStatement(ALL_EXPENSES);
        ){
            ResultSet resultSet = statment.executeQuery();

            while(resultSet.next()){
                expenses.add(getExpense(resultSet));
            }
        }
        return expenses;
    }

    public int createCategory(Category category) throws SQLException{
        try (
            Connection connection = DBConnection.getDBConnection();
            PreparedStatement statment = connection.prepareStatement(C_INSERT, Statement.RETURN_GENERATED_KEYS);
        ) {
            statment.setString(1, category.getCname());

            int rowsffected = statment.executeUpdate();

            if(rowsffected == 0)
                throw new SQLException("Creating a new category failed, no rows affected");
            
            try (ResultSet generatedKeys = statment.getGeneratedKeys();) {
                if(generatedKeys.next())
                    return generatedKeys.getInt(1);
                else    
                    throw new SQLException("Creating category failed, no ID obtained");
            } 
        } 
    }

    public int createExpense(Expense expense) throws SQLException{
        int categoryId;
        try(
            Connection connection = DBConnection.getDBConnection();
            PreparedStatement findCid = connection.prepareStatement(FIND_CID);
            PreparedStatement eInsert  = connection.prepareStatement(E_INSERT,Statement.RETURN_GENERATED_KEYS); 
            PreparedStatement cInsert = connection.prepareStatement(C_INSERT, Statement.RETURN_GENERATED_KEYS);
        ){
            findCid.setString(1,expense.getCname());
            ResultSet cidSet = findCid.executeQuery();

            if(!cidSet.next()){
                cInsert.setString(1,expense.getCname());
                int Affected = cInsert.executeUpdate();

                if(Affected == 0)
                    throw new SQLException("Creating a new category failed, no rows affected");
            
                try (ResultSet generatedKeys = cInsert.getGeneratedKeys();) {
                    if(generatedKeys.next())
                        categoryId = generatedKeys.getInt(1);
                    else    
                        throw new SQLException("Creating category failed, no ID obtained");
                } 
            }
            else {
                categoryId = cidSet.getInt("cid");
            }

            eInsert.setString(1,expense.getDescription());
            eInsert.setLong(2,expense.getAmount());
            eInsert.setInt(3,categoryId);

            int rowsAffected = eInsert.executeUpdate();
            if(rowsAffected == 0)
                throw new SQLException("Adding an expense failed! No rows affected");

            try( ResultSet generatedKeys = eInsert.getGeneratedKeys(); ){
                if(generatedKeys.next())
                    return generatedKeys.getInt(1);
                else
                    throw new SQLException("Adding new expense failed! No Id obtained");
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
    public boolean  getCategoricalCount(String cname) throws SQLException{
        int count;
        try (
            Connection connection = DBConnection.getDBConnection();
            PreparedStatement statement = connection.prepareStatement(C_COUNT);
        ) {
            statement.setString(1, cname);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            } else {
                count = 0;
            }
        } 
        return count > 0;
    }

    public boolean removeCategory( int categoryID) throws SQLException{
        try(
            Connection connection =  DBConnection.getDBConnection();
            PreparedStatement statement = connection.prepareStatement(C_DELETE);
        ){
            statement.setInt(1, categoryID);
            int rowsAffected = statement.executeUpdate();
            return  rowsAffected > 0;
        }
    }

    public boolean updateCategory(Category category) throws SQLException{
        try(
            Connection connection = DBConnection.getDBConnection();
            PreparedStatement statement = connection.prepareStatement(C_UPDATE);
        ){
            statement.setInt(2, category.getCid());
            statement.setString(1, category.getCname());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        }
    }
}
