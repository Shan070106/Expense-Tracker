package com.tracker;

import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.SwingUtilities;

import com.tracker.gui.MainFrame;
import com.tracker.util.DBConnection;

public class Main {

    public static void main(String[] args){
        // Database connection 
        try{
            Connection connection = DBConnection.getDBConnection();
            System.out.println("Database connected successfully");
        }
        catch(SQLException se){
            System.err.println("Database connection failed!");
        }
        
        SwingUtilities.invokeLater(
            () -> {
                try{
                    new MainFrame().setVisible(true);
                }
                catch(Exception e){
                    System.out.println("Error in starting application !");
                    System.out.println(e.getMessage());
                }
            }
        );
    }

    


}