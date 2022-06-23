package view;

import controller.Controller;

import javax.swing.*;
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

        //Creating the frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(1000, 500);
        this.setTitle("Sales Invoice Generator");
        ImageIcon image = new ImageIcon("src/main/java/view/logo.png");
        this.setIconImage(image.getImage());
        this.setResizable(false);
        this.setVisible(true);
        this.setLayout(null);
        this.add(leftPanel);
        this.add(rightPanel);
    }

    // Function which creates and returns the left panel
    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(null);
        leftPanel.setBounds(0, 0, 500, 500);
        return leftPanel;
    }

    // Function which creates and returns the right panel
    private JPanel createRightPanel() throws IOException {

        // Creating labels
        JLabel label0 = new JLabel();
        label0.setBounds(20,20,200,20);
        label0.setText("Invoice Number : ");

        JLabel label1 = new JLabel();
        label1.setText("Invoice Date :");
        label1.setBounds(20,40,200,20);

        JLabel label2 = new JLabel();
        label2.setText("Customer Name : ");
        label2.setBounds(20,60,200,20);

        JLabel label3 = new JLabel();
        label3.setText("Invoice Total : ");
        label3.setBounds(20,80,200,20);

        // Creating buttons
        JButton saveButton = new JButton();
        saveButton.setBounds(150,400,80,40);
        saveButton.setText("Save");
        saveButton.setFocusable(false);
        saveButton.addActionListener(controller::save);

        // Creating buttons
        JButton cancelButton = new JButton();
        cancelButton.setBounds(250,400,80,40);
        cancelButton.setText("Cancel");
        cancelButton.setFocusable(false);
        cancelButton.addActionListener(controller::cancel);

        JPanel rightPanel = new JPanel();
        rightPanel.setBounds(500, 0, 500, 500);
        rightPanel.setLayout(null);
        rightPanel.add(label0);
        rightPanel.add(label1);
        rightPanel.add(label2);
        rightPanel.add(label3);
        rightPanel.add(saveButton);
        rightPanel.add(cancelButton);
        return rightPanel;
    }
}