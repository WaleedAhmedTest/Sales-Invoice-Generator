package model;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

public class FileOperations {


    // Function which reads all invoices and returns the data
    public static Hashtable<Integer,InvoiceHeader> readFile(String invoiceLinePath,String invoiceHeaderPath, JFrame frame) {
        Hashtable<Integer,InvoiceHeader> data = new Hashtable<>();
        // Initializing scanners
        Scanner invoiceLineScanner;
        Scanner invoiceHeaderScanner;
        try{
            invoiceLineScanner = new Scanner(new File(invoiceLinePath));
            invoiceHeaderScanner = new Scanner(new File(invoiceHeaderPath));
        }catch (FileNotFoundException e){
            JOptionPane.showMessageDialog(frame, "Input files are missing...",
                    "Error",JOptionPane.ERROR_MESSAGE);
            return new Hashtable<>();
        }
        // Reading all invoice lines
        Hashtable<Integer,ArrayList<InvoiceLine>>hashtable = new Hashtable<>();
        invoiceLineScanner.nextLine();
        while (invoiceLineScanner.hasNextLine()){
            String[] line = invoiceLineScanner.nextLine().split(",");
            if(line.length!=4) {
                JOptionPane.showMessageDialog(frame, "Input data files are corrupted...",
                        "Error",JOptionPane.ERROR_MESSAGE);
                invoiceLineScanner.close();
                invoiceHeaderScanner.close();
                return new Hashtable<>();
            }
            InvoiceLine invoiceLine = new InvoiceLine(line[0],line[1],line[2],line[3]);
            if (hashtable.containsKey(Integer.parseInt(line[0])))
                hashtable.get(Integer.parseInt(line[0])).add(invoiceLine);
            else{
                ArrayList<InvoiceLine> newList = new ArrayList<>();
                newList.add(invoiceLine);
                hashtable.put(Integer.parseInt(line[0]),newList);
            }
        }
        invoiceLineScanner.close();

        // Reading all invoice headers
        invoiceHeaderScanner.nextLine();
        while (invoiceHeaderScanner.hasNextLine()) {
            String[] line = invoiceHeaderScanner.nextLine().split(",");
            if(line.length!=3){
                JOptionPane.showMessageDialog(frame, "Input data files are corrupted...",
                        "Error",JOptionPane.ERROR_MESSAGE);
                invoiceLineScanner.close();
                invoiceHeaderScanner.close();
                return new Hashtable<>();
            }
            InvoiceHeader invoiceHeader = new InvoiceHeader(line[0],line[1],line[2]);
            if (hashtable.containsKey(Integer.parseInt(line[0])))
                invoiceHeader.setInvoiceLines(hashtable.get(Integer.parseInt(line[0])));
            else
                invoiceHeader.setInvoiceLines(new ArrayList<>());
            data.put(Integer.parseInt(line[0]),invoiceHeader);
        }
        invoiceHeaderScanner.close();
        return data;
    }

    // Void which writes all invoices
    public static void writeFile(Hashtable<Integer,InvoiceHeader> data,String path,JFrame frame){
        FileWriter invoiceLineWriter;
        FileWriter invoiceHeaderWriter;
        try {
            // Initializing writers
            invoiceLineWriter = new FileWriter(path + "\\InvoiceLine.csv");
            invoiceHeaderWriter = new FileWriter(path + "\\InvoiceHeader.csv");
            invoiceLineWriter.write("invoiceNum,itemName,itemPrice,Count\n");
            invoiceHeaderWriter.write("invoiceNum,invoiceDate,CustomerName\n");

            for (InvoiceHeader invoiceHeader : data.values()){
                // Writing invoice headers
                invoiceHeaderWriter.write(invoiceHeader.getInvoiceNum() + "," + invoiceHeader.getInvoiceDate()
                + "," + invoiceHeader.getCustomerName() + "\n");

                // Writing invoice lines
                for (InvoiceLine invoiceLine : invoiceHeader.getInvoiceLines()){
                    invoiceLineWriter.write(
                            invoiceLine.getInvoiceNum() + "," + invoiceLine.getItemName() + ","
                                    + invoiceLine.getItemPrice() + "," + invoiceLine.getCount() + "\n"
                    );
                }
            }
            // Closing writers
            invoiceLineWriter.close();
            invoiceHeaderWriter.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Output files path is corrupted...",
                    "Error",JOptionPane.ERROR_MESSAGE);
        }
    }
}