package controller;

import model.FileOperations;
import model.InvoiceHeader;
import model.InvoiceLine;
import view.GUI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

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
        gui.initializeFrame(data);
        showInvoice(invoiceHeader.getInvoiceNum());
    }

    // Function which saves newInvoice
    public void saveNewInvoice(){
        gui.createNewInvoice(getNewInvoiceNumber());
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

    // This function calculates the total cost and returns a list with new values
    public ArrayList<Double> updateTotalCost(String invNum){
        ArrayList<Double> updatedValues = new ArrayList<>();
        double sum = 0;
        InvoiceHeader invoiceHeader = data.get(Integer.parseInt(invNum));
        for (InvoiceLine invoiceLine : invoiceHeader.getInvoiceLines()){
            double temp = Double.parseDouble(invoiceLine.getCount()) *
                    Double.parseDouble(invoiceLine.getItemPrice());
            updatedValues.add(temp);
            sum += temp;
        }
        updatedValues.add(sum);
        return updatedValues;
    }

    // Function which returns a new invoice number
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
