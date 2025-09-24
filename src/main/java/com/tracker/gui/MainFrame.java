package com.tracker.gui;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import com.tracker.util.*;
import com.tracker.model.*;


public class MainFrame extends JFrame{
    private JButton expenseButton;
    private JButton categoryButton;
    private static final int HEIGHT = 100;
    private static final int  WIDTH = 100; 
    
    public MainFrame(){
        initalizeComponents();
    }

    private void initalizeComponents(){
        setTitle("Expense Tracker App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,600);
        setLocationRelativeTo(null);

        expenseButton = new JButton("Expense");
        expenseButton.setPreferredSize(new Dimension(HEIGHT,WIDTH));;
        
        categoryButton = new JButton("Category");
        categoryButton.setPreferredSize(new Dimension(HEIGHT, WIDTH));

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(expenseButton);
        buttonPanel.add(categoryButton);
        
        setLayout(new BorderLayout());
        add(buttonPanel,BorderLayout.CENTER);
    }

    private void setEventListeners(){
        categoryButton.addActionListener(
            (e)->{
                try {
                    new CategoryFrame();
                } catch (Exception e) {
                    // TODO: handle exception
                    JOptionPane.showMessageDialog(rootPane, "Failed to get Category window","Failed !", JOptionPane.ERROR_MESSAGE);
                }
            }
        );
    }
}

class CategoryFrame  {

    
}
