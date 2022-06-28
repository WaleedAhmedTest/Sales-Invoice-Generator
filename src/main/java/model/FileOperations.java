package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

public class FileOperations {

    private final String INVOICE_LINE_PATH;
    private final String INVOICE_HEADER_PATH;

    // Initializing the FileOperations class
    public FileOperations() {
        INVOICE_LINE_PATH = "src/main/resources/InvoiceLine.csv";
        INVOICE_HEADER_PATH = "src/main/resources/InvoiceHeader.csv";
    }

    // Function which reads all invoices and returns the data
    public Hashtable<Integer,InvoiceHeader> readFile() {
        Hashtable<Integer,InvoiceHeader> data = new Hashtable<>();
        // Initializing scanners
        Scanner invoiceLineScanner = null;
        Scanner invoiceHeaderScanner = null;
        try{
            invoiceLineScanner = new Scanner(new File(INVOICE_LINE_PATH));
            invoiceHeaderScanner = new Scanner(new File(INVOICE_HEADER_PATH));
        }catch (FileNotFoundException e){
            System.err.println("[ERROR] Input files are missing...");
            System.exit(-1);
        }
        // Reading all invoice lines
        Hashtable<Integer,ArrayList<InvoiceLine>>hashtable = new Hashtable<>();
        invoiceLineScanner.nextLine();
        while (invoiceLineScanner.hasNextLine()){
            String[] line = invoiceLineScanner.nextLine().split(",");
            if(line.length!=4) {
                System.err.println("[ERROR] Input data files are corrupted...");
                invoiceLineScanner.close();
                invoiceHeaderScanner.close();
                System.exit(-1);
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
                System.err.println("[ERROR] Input data files are corrupted...");
                invoiceLineScanner.close();
                invoiceHeaderScanner.close();
                System.exit(-1);
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
    public void writeFile(Hashtable<Integer,InvoiceHeader> data){
        FileWriter invoiceLineWriter;
        FileWriter invoiceHeaderWriter;
        try {
            // Initializing writers
            invoiceLineWriter = new FileWriter(INVOICE_LINE_PATH);
            invoiceHeaderWriter = new FileWriter(INVOICE_HEADER_PATH);
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
            System.err.println("[ERROR] Output files path is corrupted...");
            System.exit(-1);
        }
    }
}