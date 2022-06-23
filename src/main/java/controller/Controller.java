package controller;

import model.FileOperations;
import model.InvoiceHeader;

import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Controller {

    // Array data which contains all the invoices and details
    ArrayList<InvoiceHeader> data;
    FileOperations fileOperations;

    // Initializing the controller
    public Controller() throws FileNotFoundException {
        fileOperations = new FileOperations();
        data = fileOperations.readFile();
    }

    // Save function which is called when clicking the Save button
    public void save(ActionEvent e){
        fileOperations.writeFile(data);
    }

    // Cancel function which is called when clicking the Cancel button
    public void cancel(ActionEvent e){
        //TODO
    }
}
