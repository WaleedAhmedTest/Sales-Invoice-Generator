package controller;

import model.FileOperations;
import model.InvoiceHeader;
import model.InvoiceLine;
import view.GUI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Scanner;

public class Controller {

    // Hashtable data which contains all the invoices' data (Hashtable is used to increase performance)
    private Hashtable<Integer,InvoiceHeader> data;
    // File operation and gui instances
    private final FileOperations fileOperations;
    private final GUI gui;

    // Initializing the controller which initialize also the GUI
    public Controller() {
        this.gui = new GUI(this);
        this.fileOperations = new FileOperations();
        loadFile();
    }

    // Function which loads the data from file and save it in memory
    public void loadFile() {
        data = fileOperations.readFile();
        gui.initializeFrame(data);
    }

    // Save function which is called when clicking the Save File button
    public void saveFile(){
        fileOperations.writeFile(data);
    }

    // Cancel function which is called when clicking the Cancel button
    public void cancelInstance(String invNum){
        gui.updateRightTable(data.get(Integer.parseInt(invNum)));
    }

    // Function which save the new instance (Called when save button is pressed)
    public void saveInstance(InvoiceHeader invoiceHeader){
        data.put(Integer.parseInt(invoiceHeader.getInvoiceNum()),invoiceHeader);
        gui.initializeFrame(data);
        showInvoice(invoiceHeader.getInvoiceNum());
    }

    // Function which creates new invoice
    public void createNewInvoice(){
        gui.createNewInvoice(getNewInvoiceNumber());
    }

    // Function deletes an invoice from the data
    public void deleteInvoice(String invNum){
        data.remove(Integer.parseInt(invNum));
        gui.initializeFrame(data);
    }

    // Function which shows a certain invoice
    public void showInvoice(String invNum){
        gui.updateRightTable(data.get(Integer.parseInt(invNum)));
    }

    // Function returns the total price of an invoice
    public double calculateTotalCost(InvoiceHeader invoiceHeader){
        double result = 0;
        for (InvoiceLine invoiceLine : invoiceHeader.getInvoiceLines()){
            result += Double.parseDouble(invoiceLine.getCount()) *
                    Double.parseDouble(invoiceLine.getItemPrice());
        }
        return result;
    }

    // Function which returns a new invoice number (ID)
    private String getNewInvoiceNumber(){
        String path = "src/main/java/controller/index";
        int invoiceNumber = -1;
        try {
            Scanner sc = new Scanner(new File(path));
            invoiceNumber = sc.nextInt();
            sc.close();
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(Integer.toString(invoiceNumber + 1));
            fileWriter.close();
        } catch(FileNotFoundException fileNotFoundException){
            System.err.println("[ERROR] " + path + " for index is not found...");
            System.exit(-1);
        } catch (IOException ioException) {
            System.err.println("[ERROR] Index file is corrupted...");
            System.exit(-1);
        }
        return ""+invoiceNumber;
    }
}
