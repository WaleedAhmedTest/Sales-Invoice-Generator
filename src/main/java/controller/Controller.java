package controller;

import model.FileOperations;
import model.InvoiceHeader;
import model.InvoiceLine;
import view.GUI;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.Scanner;

public class Controller {

    // Hashtable data which contains all the invoices' data (Hashtable is used to increase performance)
    private Hashtable<Integer,InvoiceHeader> data;
    // GUI instance
    private final GUI gui;

    // Initializing the controller which initialize also the GUI
    public Controller() {
        this.gui = new GUI(this);
        loadFile("src/main/resources/InvoiceLine.csv","src/main/resources/InvoiceHeader.csv");
    }

    // Function which loads the data from file and save it in memory
    public int loadFile(String invoiceLinePath, String invoiceHeaderPath) {
        data = FileOperations.readFile(invoiceLinePath,invoiceHeaderPath,gui);
        gui.initializeFrame(data);
        if (data.isEmpty())
            return -1;
        else
            return 1;
    }

    // Save function which is called when clicking the Save File button
    public int saveFile(String path){return FileOperations.writeFile(data,path,gui); }


    // Function which save the new instance (Called when save button is pressed)
    public int saveInstance(InvoiceHeader invoiceHeader, JFrame frame){
        if (invoiceHeader.getCustomerName().equals("")){
            JOptionPane.showMessageDialog(frame, "Customer name is empty...",
                    "Error",JOptionPane.ERROR_MESSAGE);
            return -1;
        }
        if (!checkDateIsValid(invoiceHeader.getInvoiceDate(),frame))
            return -1;
        data.put(Integer.parseInt(invoiceHeader.getInvoiceNum()),invoiceHeader);
        gui.initializeFrame(data);
        showInvoice(invoiceHeader.getInvoiceNum());
        return 1;
    }

    // This function checks if the date format is correct
    private boolean checkDateIsValid(String date, JFrame frame){
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            simpleDateFormat.parse(date);
        }
        catch (ParseException e) {
            JOptionPane.showMessageDialog(frame, "Date format entered is wrong. It should be [dd/MM/yyyy]",
                    "Error",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    // This function adds a new item to a certain invoice =
    public void saveItem(Integer invNum,InvoiceLine invoiceLine){
        data.get(invNum).getInvoiceLines().add(invoiceLine);
        gui.initializeFrame(data);
        showInvoice("" + invNum);
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
    public String getNewInvoiceNumber(){
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
            JOptionPane.showMessageDialog(gui, "[ERROR] " + path + " for index is not found...",
                    "Error",JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        } catch (IOException ioException) {
            JOptionPane.showMessageDialog(gui, "[ERROR] Index file is corrupted...",
                    "Error",JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        }
        return ""+invoiceNumber;
    }
}