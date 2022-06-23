package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame implements ActionListener {

    // GUI constructor
    public GUI() {

        // Creating the panels
        JPanel leftPanel = createLeftPanel();
        JPanel rightPanel = createRightPanel();

        //Creating the frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(1000, 500);
        this.setTitle("Sales Invoice Generator");
        ImageIcon image = new ImageIcon("src/main/java/frontend/logo.png");
        this.setIconImage(image.getImage());
        this.setResizable(false);
        this.setVisible(true);
        this.setLayout(null);
        this.add(leftPanel);
        this.add(rightPanel);
    }

    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(null);
        leftPanel.setBounds(0, 0, 500, 500);
        return leftPanel;
    }

    private JPanel createRightPanel() {

        // Creating labels
        JLabel label0 = new JLabel();
        label0.setBounds(20,20,200,20);
        label0.setText("Invoice Number : 23");

        JLabel label1 = new JLabel();
        label1.setText("Invoice Date ");
        label1.setBounds(20,40,200,20);

        JLabel label2 = new JLabel();
        label2.setText("Customer Name ");
        label2.setBounds(20,60,200,20);

        JLabel label3 = new JLabel();
        label3.setText("Invoice Total :     350.50");
        label3.setBounds(20,80,200,20);

        // Creating buttons
        JButton saveButton = new JButton();
        saveButton.setBounds(150,400,80,40);
        saveButton.setText("Save");
        saveButton.setFocusable(false);

        // Creating buttons
        JButton cancelButton = new JButton();
        cancelButton.setBounds(250,400,80,40);
        cancelButton.setText("Cancel");
        cancelButton.setFocusable(false);

        JPanel rightPanel = new JPanel();
        rightPanel.setBounds(500, 0, 500, 500);
        rightPanel.setLayout(null);
        rightPanel.add(label0);
        rightPanel.add(label1);
        rightPanel.add(label2);
        rightPanel.add(label3);
        rightPanel.add(saveButton);
        rightPanel.add(cancelButton);
        rightPanel.setBackground(Color.GREEN);
        return rightPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
