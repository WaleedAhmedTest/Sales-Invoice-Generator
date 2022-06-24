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

    // Function which loads the data
    public void loadFile(ActionEvent e){
        //TODO
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
}
