package controller;

import model.FileOperations;
import model.InvoiceHeader;
import model.InvoiceLine;
import view.GUI;

import java.awt.event.ActionEvent;
import java.util.Hashtable;

public class Controller {

    // Array data which contains all the invoices and details
    private Hashtable<Integer,InvoiceHeader> data;
    private final FileOperations fileOperations;
    private final GUI gui;

    // Initializing the controller
    public Controller() {
        this.gui = new GUI(this);
        this.fileOperations = new FileOperations();
        loadFile();
    }

    // Function which loads the data
    public void loadFile() {
        data = fileOperations.readFile();
        gui.initializeFrame(data);
    }

    // Save function which is called when clicking the Save button
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
    }

    // Function which saves newInvoice
    public void saveNewInvoice(ActionEvent e){
        //TODO
    }

    // Function which saves newInvoice
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
}
