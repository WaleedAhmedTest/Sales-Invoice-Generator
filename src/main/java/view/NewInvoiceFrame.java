package view;

import controller.Controller;
import model.InvoiceHeader;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class NewInvoiceFrame extends JFrame {

    JFrame main; // Main GUI frame
    private final String invNum;
    private final Controller controller;
    private final JTable table;
    private final JLabel invoiceTotalLabel;
    private final JTextField dateTextField;
    private final JTextField customerTextField;

    // NewInvoiceFrame constructor
    public NewInvoiceFrame(String invNum, JFrame main, Controller controller){

        // Initializing variables and disabling main frame
        this.invNum = invNum;
        this.controller = controller;
        this.main = main;
        main.setEnabled(false);
        main.setVisible(false);

        // Creating labels
        JLabel label0 = new JLabel();
        label0.setBounds(40,0,200,20);
        label0.setText("Invoice Number : " + invNum);

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
        table = new JTable(new DefaultTableModel(columns,0)){
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0 || column == 1 || column == 2 || column == 3;
            }
        };
        table.getSelectionModel().addListSelectionListener(event -> {
            if (event.getValueIsAdjusting())
                GUI.updateTotalCost(table,invoiceTotalLabel,this);
        });
        table.putClientProperty("terminateEditOnFocusLost", true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.setShowGrid(true);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20,105,445,285);

        // Creating buttons
        JButton createNewInvoiceLineButton = new JButton();
        createNewInvoiceLineButton.setBounds(50,400,160,30);
        createNewInvoiceLineButton.setText("New Invoice Line");
        createNewInvoiceLineButton.setFocusable(false);
        createNewInvoiceLineButton.addActionListener(e -> createNewInvoiceLine());


        JButton saveButton = new JButton();
        saveButton.setBounds(230,400,80,30);
        saveButton.setText("Save");
        saveButton.setFocusable(false);
        saveButton.addActionListener(e -> saveInvoice());

        JButton cancelButton = new JButton();
        cancelButton.setBounds(330,400,80,30);
        cancelButton.setText("Cancel");
        cancelButton.setFocusable(false);
        cancelButton.addActionListener(e -> closeFrame());

        // Setting the frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(500, 500);
        this.setTitle("Create new invoice");
        ImageIcon image = new ImageIcon("src/main/java/view/logo.png");
        this.setIconImage(image.getImage());
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
        this.setLayout(null);
        this.add(label0);
        this.add(label1);
        this.add(dateTextField);
        this.add(label2);
        this.add(customerTextField);
        this.add(label3);
        this.add(invoiceTotalLabel);
        this.add(label4);
        this.add(createNewInvoiceLineButton);
        this.add(saveButton);
        this.add(cancelButton);
        this.add(scrollPane);
    }

    // Function which create new empty invoice line
    private void createNewInvoiceLine(){
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(new Object[]{invNum,"","0","0","0.0"});
    }

    // This function is used to save invoice, it is called when Save button is pressed
    private void saveInvoice(){
        GUI.updateTotalCost(table,invoiceTotalLabel,this);
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        InvoiceHeader invoiceHeader = new InvoiceHeader(invNum,
                dateTextField.getText(),customerTextField.getText());
        GUI.getTableData(model, invoiceHeader);
        int status = controller.saveInstance(invoiceHeader,this);
        if (status == 1) {
            JOptionPane.showMessageDialog(this, "Invoice saved");
            closeFrame();
        }
    }

    // This function is used to close the frame
    private void closeFrame(){
        main.setVisible(true);
        main.setEnabled(true);
        this.setVisible(false);
        this.setEnabled(false);
        dispose();
    }
}