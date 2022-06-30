package view;

import controller.Controller;
import model.InvoiceHeader;
import model.InvoiceLine;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
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
        loadMenuItem.addActionListener(e -> saveAndLoadFiles("load"));
        JMenuItem saveMenuItem = new JMenuItem("Save File");
        saveMenuItem.addActionListener(e -> saveAndLoadFiles("save"));
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
        createNewInvoiceButton.addActionListener(e-> createNewInvoice());

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
                GUI.updateTotalCost(rightTable,invoiceTotalLabel,this);
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
    public void createNewInvoice(){
        new NewInvoiceFrame(controller.getNewInvoiceNumber(),this, controller);
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
        updateTotalCost(rightTable,invoiceTotalLabel,this);
        DefaultTableModel model = (DefaultTableModel) rightTable.getModel();
        InvoiceHeader invoiceHeader = new InvoiceHeader(invoiceNumLabel.getText(),
                dateTextField.getText(),customerTextField.getText());
        getTableData(model, invoiceHeader);
        controller.saveInstance(invoiceHeader,this);
        JOptionPane.showMessageDialog(this, "Invoice saved");
    }

    // This function gets the table data
    public static void getTableData(DefaultTableModel model, InvoiceHeader invoiceHeader) {
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
    }

    // This function updates the total coses when values are changed
    public static void updateTotalCost(JTable table,JLabel invoiceTotalLabel, JFrame frame){
        DefaultTableModel model = (DefaultTableModel) table.getModel();
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
            JOptionPane.showMessageDialog(frame, "Data entered is wrong. Count and price should be numbers",
                    "Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    // This function is called when save file or load file are pressed
    private void saveAndLoadFiles(String mode){
        JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        if (mode.equals("save")) {
            j.setDialogTitle("Select directory to save InvoiceHeader.csv and InvoiceLine.csv");
            j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int r = j.showSaveDialog(null);
            if (r == JFileChooser.APPROVE_OPTION) {
                int status = controller.saveFile(j.getSelectedFile().getAbsolutePath());
                if (status == 1)
                    JOptionPane.showMessageDialog(this, "Save complete");
            }
            else
                JOptionPane.showMessageDialog(this, "The user cancelled the operation");
        }
        else {
            String invoiceHeaderPath,invoiceLinePath;
            j.setDialogTitle("Select InvoiceHeader.csv file");
            j.setMultiSelectionEnabled(true);
            int r = j.showOpenDialog(null);
            if (r == JFileChooser.APPROVE_OPTION)
                invoiceHeaderPath = j.getSelectedFile().getAbsolutePath();
            else {
                JOptionPane.showMessageDialog(this, "The user cancelled the operation");
                return;
            }
            j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            j.setDialogTitle("Select InvoiceLine.csv file");
            j.setMultiSelectionEnabled(true);
            r = j.showOpenDialog(null);
            if (r == JFileChooser.APPROVE_OPTION)
                invoiceLinePath = j.getSelectedFile().getAbsolutePath();
            else {
                JOptionPane.showMessageDialog(this, "The user cancelled the operation");
                return;
            }
            int status = controller.loadFile(invoiceLinePath,invoiceHeaderPath);
            if (status==1)
                JOptionPane.showMessageDialog(this, "Data loaded successfully");
        }
    }
}