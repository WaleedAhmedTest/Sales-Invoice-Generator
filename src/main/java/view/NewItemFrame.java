package view;

import controller.Controller;
import model.InvoiceLine;

import javax.swing.*;

public class NewItemFrame extends JFrame {

    JFrame main; // Main GUI frame
    private final String invNum;
    private final Controller controller;
    private final JTextField itemNameTextField;
    private final JTextField itemPriceTextField;
    private final JTextField itemCountTextField;


    // NewInvoiceFrame constructor
    public NewItemFrame(String invNum, JFrame main, Controller controller){

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
        label1.setText("Item name : ");
        label1.setBounds(5,20,200,20);

        JLabel label2 = new JLabel();
        label2.setText("Item price : ");
        label2.setBounds(5,40,200,20);

        JLabel label3 = new JLabel();
        label3.setText("Item count : ");
        label3.setBounds(5,60,200,20);

        // Creating text fields
        itemNameTextField = new JTextField();
        itemNameTextField.setBounds(80,20,170,20);

        itemPriceTextField = new JTextField();
        itemPriceTextField.setBounds(80,40,170,20);

        itemCountTextField = new JTextField();
        itemCountTextField.setBounds(80,60,170,20);

        JButton saveButton = new JButton();
        saveButton.setBounds(50,100,80,30);
        saveButton.setText("Save");
        saveButton.setFocusable(false);
        saveButton.addActionListener(e -> saveItem());

        JButton cancelButton = new JButton();
        cancelButton.setBounds(160,100,80,30);
        cancelButton.setText("Cancel");
        cancelButton.setFocusable(false);
        cancelButton.addActionListener(e -> closeFrame());

        // Setting the frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(275, 180);
        this.setTitle("Create new invoice");
        ImageIcon image = new ImageIcon("src/main/java/view/logo.png");
        this.setIconImage(image.getImage());
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
        this.setLayout(null);
        this.add(label0);
        this.add(label1);
        this.add(itemNameTextField);
        this.add(label2);
        this.add(itemPriceTextField);
        this.add(label3);
        this.add(itemCountTextField);
        this.add(saveButton);
        this.add(cancelButton);
    }


    // This function is used to save invoice, it is called when Save button is pressed
    private void saveItem() {
        if (itemNameTextField.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Item must have a name !",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try{
            Double.parseDouble(itemPriceTextField.getText());
            Double.parseDouble(itemCountTextField.getText());
        }catch (Exception e){
            JOptionPane.showMessageDialog(this, "Item price and count must be numbers !",
                    "Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        InvoiceLine invoiceLine = new InvoiceLine(invNum,itemNameTextField.getText(),
                itemPriceTextField.getText(),itemCountTextField.getText());
        controller.saveItem(Integer.parseInt(invNum),invoiceLine);
        JOptionPane.showMessageDialog(this, "Item is added successfully.");
        closeFrame();
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