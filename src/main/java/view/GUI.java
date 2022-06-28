package view;

import controller.Controller;
import model.InvoiceHeader;
import model.InvoiceLine;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import java.util.List;

public class GUI extends JFrame{

    // GUI attributes
    private final Controller controller;
    private JTable leftTable,rightTable;
    private JLabel invoiceNumLabel, invoiceTotalLabel;
    private JTextField dateTextField,customerTextField;

    // GUI constructor (This constructor initializes and sets-up the JFrame)
    public GUI(Controller controller) {

        // Initializing the controller
        this.controller = controller;

        // Creating the panels
        JPanel leftPanel = createLeftPanel();
        JPanel rightPanel = createRightPanel();

        // Creating the menu
        JMenuBar menuBar = new JMenuBar();
        JMenu fileBar = new JMenu("File");
        JMenuItem loadMenuItem = new JMenuItem("Load File");
        loadMenuItem.addActionListener(e -> controller.loadFile());
        JMenuItem saveMenuItem = new JMenuItem("Save File");
        saveMenuItem.addActionListener(e -> controller.saveFile());
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
        this.setLayout(new GridLayout(1,2)); // Setting the grid layout
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
        leftTable = new JTable(new DefaultTableModel(columns,0));
        leftTable.getTableHeader().setReorderingAllowed(false);
        leftTable.getTableHeader().setResizingAllowed(false);
        leftTable.setShowGrid(true);
        leftTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        leftTable.getSelectionModel().addListSelectionListener(event -> {
            if (event.getValueIsAdjusting())
                controller.showInvoice(leftTable.getValueAt(leftTable.getSelectedRow(), 0).toString());
        });
        leftTable.setShowGrid(true);
        leftTable.setDefaultEditor(Object.class, null);
        JScrollPane scrollPane = new JScrollPane(leftTable);
        scrollPane.setBounds(20,30,480,360);

        // Adding buttons
        JButton createNewInvoiceButton = new JButton();
        createNewInvoiceButton.setBounds(80,400,150,30);
        createNewInvoiceButton.setText("Create New Invoice");
        createNewInvoiceButton.setFocusable(false);
        createNewInvoiceButton.addActionListener(e-> controller.createNewInvoice());

        JButton deleteInvoiceButton = new JButton();
        deleteInvoiceButton.setBounds(250,400,150,30);
        deleteInvoiceButton.setText("Delete Invoice");
        deleteInvoiceButton.setFocusable(false);
        deleteInvoiceButton.addActionListener(e-> {
            if (!invoiceNumLabel.getText().equals(""))
                controller.deleteInvoice(invoiceNumLabel.getText());
        });

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

        invoiceNumLabel = new JLabel();
        invoiceNumLabel.setBounds(150,0,200,20);
        invoiceNumLabel.setText("");

        JLabel label1 = new JLabel();
        label1.setText("Invoice Date :");
        label1.setBounds(40,20,200,20);

        JLabel label2 = new JLabel();
        label2.setText("Customer Name : ");
        label2.setBounds(40,40,200,20);

        JLabel label3 = new JLabel();
        label3.setText("Invoice Total : ");
        label3.setBounds(40,60,200,20);

        invoiceTotalLabel = new JLabel();
        invoiceTotalLabel.setBounds(150,60,200,20);
        invoiceTotalLabel.setText("");

        JLabel label4 = new JLabel();
        label4.setText("Invoice Items");
        label4.setBounds(20,80,200,20);

        // Creating text fields
        dateTextField = new JTextField();
        dateTextField.setBounds(150,20,200,20);

        customerTextField = new JTextField();
        customerTextField.setBounds(150,40,200,20);

        // Creating Table
        String[] columns = {"No.","Item Name","Item Price","Count","Item Total"};
        rightTable = new JTable(new DefaultTableModel(columns,0)){
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0 || column == 1 || column == 2 || column == 3;
            }
        };
        rightTable.getSelectionModel().addListSelectionListener(event -> {
            if (event.getValueIsAdjusting())
                updateTotalCost();
        });
        rightTable.putClientProperty("terminateEditOnFocusLost", true);
        rightTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        rightTable.getTableHeader().setReorderingAllowed(false);
        rightTable.getTableHeader().setResizingAllowed(false);
        rightTable.setShowGrid(true);
        JScrollPane scrollPane = new JScrollPane(rightTable);
        scrollPane.setBounds(20,105,445,285);

        // Creating buttons
        JButton saveButton = new JButton();
        saveButton.setBounds(150,400,80,30);
        saveButton.setText("Save");
        saveButton.setFocusable(false);
        saveButton.addActionListener(e -> {
            if (!invoiceNumLabel.getText().equals(""))
              saveInvoice();
        });

        JButton cancelButton = new JButton();
        cancelButton.setBounds(250,400,80,30);
        cancelButton.setText("Cancel");
        cancelButton.setFocusable(false);
        cancelButton.addActionListener(e -> {
            if (!invoiceNumLabel.getText().equals("")) {
                try {
                    controller.cancelInstance(invoiceNumLabel.getText());
                }catch (Exception ee){
                    clearRightPanel();
                }
            }
        });
        // Setting right panel
        JPanel rightPanel = new JPanel();
        rightPanel.setBounds(500, 0, 500, 500);
        rightPanel.setLayout(null);
        rightPanel.add(label0);
        rightPanel.add(invoiceNumLabel);
        rightPanel.add(label1);
        rightPanel.add(dateTextField);
        rightPanel.add(label2);
        rightPanel.add(customerTextField);
        rightPanel.add(label3);
        rightPanel.add(invoiceTotalLabel);
        rightPanel.add(label4);
        rightPanel.add(saveButton);
        rightPanel.add(cancelButton);
        rightPanel.add(scrollPane);
        return rightPanel;
    }

    // This function initializes the Frame by showing the invoices in the invoices table and clearing the right panel data
    public void initializeFrame(Hashtable<Integer,InvoiceHeader> data){
        // Adjusting the left table
        DefaultTableModel leftModel = (DefaultTableModel) leftTable.getModel();
        leftModel.setRowCount(0);
        for (InvoiceHeader invoiceHeader : data.values()){
            leftModel.addRow(new String[]{invoiceHeader.getInvoiceNum(), invoiceHeader.getInvoiceDate()
            , invoiceHeader.getCustomerName(), ""+controller.calculateTotalCost(invoiceHeader)});
        }
        clearRightPanel();
    }

    // This function is used to update the right table with the given invoiceHeader
    public void updateRightTable(InvoiceHeader invoiceHeader){
        clearRightPanel();
        invoiceNumLabel.setText(invoiceHeader.getInvoiceNum());
        invoiceTotalLabel.setText(""+controller.calculateTotalCost(invoiceHeader));
        dateTextField.setText(invoiceHeader.getInvoiceDate());
        customerTextField.setText(invoiceHeader.getCustomerName());
        DefaultTableModel rightModel = (DefaultTableModel) rightTable.getModel();
        for (InvoiceLine invoiceLine : invoiceHeader.getInvoiceLines()){
            rightModel.addRow(new String[]{invoiceHeader.getInvoiceNum(), invoiceLine.getItemName()
                    , invoiceLine.getItemPrice(), invoiceLine.getCount(), "" +
                    (Double.parseDouble(invoiceLine.getCount()) * Double.parseDouble(invoiceLine.getItemPrice()))});
        }
    }

    // This function creates a new invoice with empty rows
    public void createNewInvoice(String invNum){
        clearRightPanel();
        invoiceNumLabel.setText(invNum);
        invoiceTotalLabel.setText("0.0");
        DefaultTableModel model = (DefaultTableModel) rightTable.getModel();
        for (int i = 0; i <= 20; i++)
            model.addRow(new Object[]{invNum,"","0","0","0.0"});
    }

    // This function clears the right panel data
    private void clearRightPanel(){
        DefaultTableModel rightModel = (DefaultTableModel) rightTable.getModel();
        rightModel.setRowCount(0);
        invoiceNumLabel.setText("");
        invoiceTotalLabel.setText("");
        dateTextField.setText("");
        customerTextField.setText("");
    }

    // This function is used to save invoice, it is called when Save button is pressed
    private void saveInvoice(){
        updateTotalCost();
        DefaultTableModel model = (DefaultTableModel) rightTable.getModel();
        InvoiceHeader invoiceHeader = new InvoiceHeader(invoiceNumLabel.getText(),
                dateTextField.getText(),customerTextField.getText());
        Vector<?> vector = model.getDataVector();
        ArrayList<InvoiceLine> invoiceLines = new ArrayList<>();
        for (Object obj : vector){
            List<?> row = (List<?>) obj;
            if (row.get(1).equals(""))
                continue;
            invoiceLines.add(new InvoiceLine((String) row.get(0),
                    (String) row.get(1),(String) row.get(2),(String) row.get(3)));
        }
        invoiceHeader.setInvoiceLines(invoiceLines);
        controller.saveInstance(invoiceHeader);
    }

    // This function updates the right panel total costs when values are changed
    private void updateTotalCost(){
        DefaultTableModel model = (DefaultTableModel) rightTable.getModel();
        Vector<?> vector = model.getDataVector();
        double sum = 0;
        try {
            for (int i = 0; i < vector.size(); i++) {
                List<?> row = (List<?>) vector.get(i);
                double updatedValue = Double.parseDouble(row.get(2) + "") * Double.parseDouble(row.get(3) + "");
                model.setValueAt("" + updatedValue, i, 4);
                sum += updatedValue;
            }
            invoiceTotalLabel.setText("" + sum);
        } catch (Exception e){
            System.err.println("[ERROR] Data entered are wrong. Count and price should be numbers");
        }
    }
}