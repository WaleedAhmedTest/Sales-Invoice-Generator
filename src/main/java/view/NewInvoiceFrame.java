package view;

import controller.Controller;
import model.InvoiceHeader;

import javax.swing.*;
import java.util.ArrayList;

public class NewInvoiceFrame extends JFrame {

    JFrame main; // Main GUI frame
    private final String invNum;
    private final Controller controller;
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

        // Creating text fields
        dateTextField = new JTextField();
        dateTextField.setBounds(150,20,200,20);

        customerTextField = new JTextField();
        customerTextField.setBounds(150,40,200,20);

        JButton saveButton = new JButton();
        saveButton.setBounds(130,70,80,30);
        saveButton.setText("Save");
        saveButton.setFocusable(false);
        saveButton.addActionListener(e -> saveInvoice());

        JButton cancelButton = new JButton();
        cancelButton.setBounds(240,70,80,30);
        cancelButton.setText("Cancel");
        cancelButton.setFocusable(false);
        cancelButton.addActionListener(e -> closeFrame());

        // Setting the frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(500, 150);
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
        this.add(saveButton);
        this.add(cancelButton);
    }


    // This function is used to save invoice, it is called when Save button is pressed
    private void saveInvoice(){
        InvoiceHeader invoiceHeader = new InvoiceHeader(invNum,
                dateTextField.getText(),customerTextField.getText());
        invoiceHeader.setInvoiceLines(new ArrayList<>());
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