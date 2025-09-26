package com.tracker.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.tracker.dao.Dao;
import com.tracker.model.Category;


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

    private Dao dao;
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
        this.dao = new Dao();
        initializeComponents();
        setupLayout();
        setupEventListeners();
        loadCategoryTable();
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

        addButton = new JButton("Add");
        deleteButton = new JButton("Delete");
        updateButton = new JButton("Update");
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
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        northPanel.add(new JLabel("Category"),gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        northPanel.add(categoryField,gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        northPanel.add(new JLabel("Search Bar"),gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        northPanel.add(searchField,gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
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
        String categoryName = categoryField.getText().trim();
        
        if(categoryName.isEmpty()){
            JOptionPane.showMessageDialog(this,"Give new category name","Empty name", JOptionPane.WARNING_MESSAGE);
            return ;
        }

        try{
            Category category = new Category(categoryName);
            dao.createCategory(category);

            JOptionPane.showMessageDialog(this, "New Category created", "Creation successful!", JOptionPane.INFORMATION_MESSAGE);
            
            loadCategoryTable();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this,e.getMessage(),"Creation Failed!",JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadCategoryTable() {
        categoryField.setText("");
        searchField.setText("");

        try {
          List<Category> categories = dao.getAllCategories();
            updateCategoryTable(categories);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,e.getMessage(),"Loading failed",JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteCategory(){
        int row = categoryTable.getSelectedRow();

        if(row == -1){
            JOptionPane.showMessageDialog(this,"Please select a row!","Invalid row",JOptionPane.WARNING_MESSAGE);
            return ;
        }

        String category = (String)categoryTable.getValueAt(row, 0);
        categoryField.setText(category);
        
        try {
            if(dao.removeCategory(category)){
                JOptionPane.showMessageDialog(this, category + "category deleted successfully","Deletion successful",JOptionPane.INFORMATION_MESSAGE);
                loadCategoryTable();
            }
            else{
                JOptionPane.showMessageDialog(this, "Failed to delete category" + category,"Deletion failed",JOptionPane.ERROR_MESSAGE);
            }
        } 
        catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getStackTrace(),"Deletion failed!",JOptionPane.ERROR_MESSAGE);
        }


        
    }

    private void updateCategoryTable(List<Category> categories) {
        tableModel.setRowCount(0);
        try {
            for (Category c : categories) {
                tableModel.addRow(new Object[]{c.getCname(), dao.getCategoricalCount(c.getCid()), dao.getCategoricalAmount(c.getCid())});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,"Error occurred in updation!","Updation failed",JOptionPane.ERROR_MESSAGE);
        }
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