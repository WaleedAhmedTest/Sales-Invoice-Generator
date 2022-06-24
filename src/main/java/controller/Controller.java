package controller;

import model.FileOperations;
import model.InvoiceHeader;
import view.GUI;

import javax.swing.event.ListSelectionEvent;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Controller {

    // Array data which contains all the invoices and details
    private ArrayList<InvoiceHeader> data;
    private FileOperations fileOperations;
    private GUI gui;

    // Initializing the controller
    public Controller() {
        this.gui = new GUI(this);
        fileOperations = new FileOperations();
        loadFile(null);
    }

    // Function which loads the data
    public void loadFile(ActionEvent e) {
        data = fileOperations.readFile();
        gui.updateTables(data);
        System.out.println("Load file");
    }

    // Save function which is called when clicking the Save button
    public void saveFile(ActionEvent e){
        //TODO
        System.out.println("Save file");
    }

    // Cancel function which is called when clicking the Cancel button
    public void cancelInstance(ActionEvent e){
        //TODO
        System.out.println("Cancel instance");
    }

    // Function which save the new instance (Called when save button is pressed)
    public void saveInstance(ActionEvent e){
        //TODO
        System.out.println("Save instance");
    }

    // Function which saves newInvoice
    public void saveNewInvoice(ActionEvent e){
        //TODO
        System.out.println("Save new invoice");
    }

    // Function which saves newInvoice
    public void deleteInvoice(ActionEvent e){
        //TODO
        System.out.println("Delete invoice");
    }

    // Function which shows a certain invoice
    public void showInvoice(ListSelectionEvent e){
        System.out.println(e.getFirstIndex());
    }
}
