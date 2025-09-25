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
        setEventListeners();
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
        categoryButton.addActionListener(e->callCategory());

        expenseButton.addActionListener(e->callExpense());
    }

    private void callCategory(){
        new CategoryFrame().setVisible(true);
    }

    private void callExpense(){
        new ExpenseFrame().setVisible(true);
    }
}

class CategoryFrame  extends JFrame{
    public CategoryFrame(){
        initializeComponents();
        setupLayout();
    }
    
    private void initializeComponents(){
        setTitle("Category Window");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800,600);
        setLocationRelativeTo(rootPane);
    }

    private void setupLayout(){

    }
}

class ExpenseFrame extends JFrame {
    public ExpenseFrame(){
        initializeComponents();
        setupLayout();
    }

    private void initializeComponents(){
        setTitle("Expense Window");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(rootPane);
    }

    private void setupLayout(){

    }
    
}