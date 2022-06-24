package view;

import controller.Controller;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.IOException;

public class GUI extends JFrame {

    // Controller reference
    Controller controller;

    // GUI constructor
    public GUI() throws IOException {

        // Initializing the controller
        controller = new Controller();

        // Creating the panels
        JPanel leftPanel = createLeftPanel();
        JPanel rightPanel = createRightPanel();

        // Creating the menu
        JMenuBar menuBar = new JMenuBar();
        JMenu fileBar = new JMenu("File");
        JMenuItem loadMenuItem = new JMenuItem("Load File");
        loadMenuItem.addActionListener(controller::loadFile);
        JMenuItem saveMenuItem = new JMenuItem("Save File");
        saveMenuItem.addActionListener(controller::saveFile);
        fileBar.add(loadMenuItem);
        fileBar.add(saveMenuItem);
        menuBar.add(fileBar);

        // Setting the frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(1000, 500);
        this.setTitle("Sales Invoice Generator");
        ImageIcon image = new ImageIcon("src/main/java/view/logo.png");
        this.setIconImage(image.getImage());
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
        this.setLayout(null);
        this.setJMenuBar(menuBar);
        this.add(leftPanel);
        this.add(rightPanel);
    }

    // Function which creates and returns the left panel
    private JPanel createLeftPanel() {

        // Creating labels
        JLabel label0 = new JLabel();
        label0.setBounds(10,5,200,20);
        label0.setText("Invoices Table ");

        // Adding table
        String[] columns = {"No.","Date","Customer","Total"};
        String[][] data = {
                {"1","PS","100","15"},
                {"2","PPS","102","12"},
                {"3","P_PPS","103","17"}
        };
        JTable table = new JTable(data,columns);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.setShowGrid(true);
        table.setDefaultEditor(Object.class, null);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20,30,480,360);


        // Adding buttons
        JButton createNewInvoiceButton = new JButton();
        createNewInvoiceButton.setBounds(80,400,150,30);
        createNewInvoiceButton.setText("Create New Invoice");
        createNewInvoiceButton.setFocusable(false);
        createNewInvoiceButton.addActionListener(controller::saveNewInvoice);

        JButton deleteInvoiceButton = new JButton();
        deleteInvoiceButton.setBounds(250,400,150,30);
        deleteInvoiceButton.setText("Delete Invoice");
        deleteInvoiceButton.setFocusable(false);
        deleteInvoiceButton.addActionListener(controller::deleteInvoice);

        // Setting left panel
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(null);
        leftPanel.setBounds(0, 0, 500, 500);
        leftPanel.add(label0);
        leftPanel.add(scrollPane);
        leftPanel.add(createNewInvoiceButton);
        leftPanel.add(deleteInvoiceButton);
        return leftPanel;
    }

    // Function which creates and returns the right panel
    private JPanel createRightPanel() {

        // Creating labels
        JLabel label0 = new JLabel();
        label0.setBounds(40,0,200,20);
        label0.setText("Invoice Number : ");

        JLabel label1 = new JLabel();
        label1.setText("Invoice Date :");
        label1.setBounds(40,20,200,20);

        JLabel label2 = new JLabel();
        label2.setText("Customer Name : ");
        label2.setBounds(40,40,200,20);

        JLabel label3 = new JLabel();
        label3.setText("Invoice Total : ");
        label3.setBounds(40,60,200,20);

        JLabel label4 = new JLabel();
        label4.setText("Invoice Items");
        label4.setBounds(20,80,200,20);

        // Adding Table
        String[] columns = {"No.","Item Name","Item Price","Count","Item Total"};
        String[][] data = {
                {"1","PS","100","15","45"},
                {"2","PPS","102","12","345"},
                {"3","P_PPS","103","17","145"}
        };
        JTable table = new JTable(data,columns);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.setShowGrid(true);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20,105,450,285);

        // Creating buttons
        JButton saveButton = new JButton();
        saveButton.setBounds(150,400,80,30);
        saveButton.setText("Save");
        saveButton.setFocusable(false);
        saveButton.addActionListener(controller::saveInstance);

        JButton cancelButton = new JButton();
        cancelButton.setBounds(250,400,80,30);
        cancelButton.setText("Cancel");
        cancelButton.setFocusable(false);
        cancelButton.addActionListener(controller::cancelInstance);

        // Setting right panel
        JPanel rightPanel = new JPanel();
        rightPanel.setBounds(500, 0, 500, 500);
        rightPanel.setLayout(null);
        rightPanel.add(label0);
        rightPanel.add(label1);
        rightPanel.add(label2);
        rightPanel.add(label3);
        rightPanel.add(label4);
        rightPanel.add(saveButton);
        rightPanel.add(cancelButton);
        rightPanel.add(scrollPane);
        return rightPanel;
    }
}