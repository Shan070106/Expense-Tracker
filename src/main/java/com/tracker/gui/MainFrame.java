package com.tracker.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

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

    private JTextField categoryField;
    private JTextField searchField;
    private JButton findButton;
    private JButton addButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JButton openButton; 
    private JButton refreshButton;
    private JPanel searchPanel;
    private JTable categoryTable;
    private DefaultTableModel tableModel;
    private final int BUTTON_HEIGHT = 20,BUTTON_WIDTH = 100;



    public CategoryFrame(){
        initializeComponents();
        setupLayout();
        setupEventListeners();
    }
    
    private void initializeComponents(){
        setTitle("Category Window");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800,600);
        setLocationRelativeTo(rootPane);
        
        String[] columNames = {"Category Name","Count","Amount"};
        tableModel = new DefaultTableModel(columNames,0){

            @Override
            public boolean isCellEditable(int row,int col){
                return false;
            }
        };

        categoryTable = new JTable(tableModel);
        categoryTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        categoryTable.getSelectionModel().addListSelectionListener(
            e->{
                if(!e.getValueIsAdjusting()){
                    // loadSelectedcategory();
                }
            }
        );
        
        categoryField = new JTextField(20);
        searchField = new JTextField(20);

        addButton = new JButton("Add category");
        deleteButton = new JButton("Delete category");
        updateButton = new JButton("Update category");
        findButton = new JButton("Find");
        openButton = new JButton("Open");
        refreshButton = new JButton("Refresh");

        addButton.setPreferredSize(new Dimension(BUTTON_WIDTH,BUTTON_HEIGHT));
        deleteButton.setPreferredSize(new Dimension(BUTTON_WIDTH,BUTTON_HEIGHT));            
        updateButton.setPreferredSize(new Dimension(BUTTON_WIDTH,BUTTON_HEIGHT));
        refreshButton.setPreferredSize(new Dimension(BUTTON_WIDTH,BUTTON_HEIGHT));
        openButton.setPreferredSize(new Dimension(BUTTON_WIDTH,BUTTON_HEIGHT));
        findButton.setPreferredSize(new Dimension(160,40));
        
        
    }

    private void setupLayout(){
        setLayout(new BorderLayout());

        JPanel eastPanel = new JPanel(new GridLayout(0,1,10,10));
        eastPanel.add(addButton);
        eastPanel.add(deleteButton);
        eastPanel.add(updateButton);
        eastPanel.add(refreshButton);

        JPanel northPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8,8,8,8);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        northPanel.add(new JLabel("Category"),gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        northPanel.add(categoryField,gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        northPanel.add(new JLabel("Search Bar"),gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        northPanel.add(findButton,gbc);

        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        southPanel.add(new JLabel("Total amount spent on all category(overall expense)"));
        // southPanel.add(getTotal(),new JPanel());

        add(northPanel,BorderLayout.NORTH);
        add(eastPanel,BorderLayout.EAST);
        add(new JScrollPane(categoryTable),BorderLayout.CENTER);
        add(southPanel,BorderLayout.SOUTH);
    }

    private void setupEventListeners(){
        addButton.addActionListener(e->{
            addCategory();
        });

        deleteButton.addActionListener(e->{
            deleteCategory();
        });
        
        updateButton.addActionListener(e->{
            updateCategory();
        });

        refreshButton.addActionListener(e->{
            refreshCategory();
        });

        findButton.addActionListener(e->{
            findCategory();
        });
    }

    private void findCategory() {
        // TODO: Implement find logic here
    }

    private void addCategory(){

    }

    private void deleteCategory(){
        
    }

    private void updateCategory(){
        // TODO: Implement update logic here
    }

    private void refreshCategory() {
        // TODO: Implement refresh logic here
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