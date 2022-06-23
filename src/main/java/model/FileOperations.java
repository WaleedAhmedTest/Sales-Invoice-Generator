package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

public class FileOperations {

    private final String INVOICE_LINE_PATH;
    private final String INVOICE_HEADER_PATH;

    // Initializing the FileOperations
    public FileOperations() {
        INVOICE_LINE_PATH = "src/main/resources/InvoiceLine.csv";
        INVOICE_HEADER_PATH = "src/main/resources/InvoiceHeader.csv";

    }

    // Function which reads all invoices
    public ArrayList<InvoiceHeader> readFile() throws FileNotFoundException {
        ArrayList<InvoiceHeader> data = new ArrayList<>();

        // Reading all invoice lines
        Hashtable<Integer,ArrayList<InvoiceLine>>hashtable = new Hashtable<>();
        Scanner invoiceLineScanner = new Scanner(new File(INVOICE_LINE_PATH));
        invoiceLineScanner.next("invoiceNum,itemName,itemPrice,Count");
        invoiceLineScanner.useDelimiter("[,\n]");
        while (invoiceLineScanner.hasNext()){
            String invoiceNum = invoiceLineScanner.next().replace("\r", "");
            while (invoiceNum.equals(""))
                invoiceNum = invoiceLineScanner.next().replace("\r", "");
            String itemName = invoiceLineScanner.next().replace("\r", "");
            String itemPrice = invoiceLineScanner.next().replace("\r", "");
            String count = invoiceLineScanner.next().replace("\r", "");
            InvoiceLine invoiceLine = new InvoiceLine(invoiceNum,itemName,itemPrice,count);
            if (hashtable.containsKey(Integer.parseInt(invoiceNum)))
                hashtable.get(Integer.parseInt(invoiceNum)).add(invoiceLine);
            else{
                ArrayList<InvoiceLine> newList = new ArrayList<>();
                newList.add(invoiceLine);
                hashtable.put(Integer.parseInt(invoiceNum),newList);
            }
        }
        invoiceLineScanner.close();

        // Reading all invoice headers
        Scanner invoiceHeaderScanner = new Scanner(new File(INVOICE_HEADER_PATH));
        invoiceHeaderScanner.next("invoiceNum,invoiceDate,CustomerName");
        invoiceHeaderScanner.useDelimiter("[,\n]");
        while (invoiceHeaderScanner.hasNext()) {
            String invoiceNum = invoiceHeaderScanner.next().replace("\r", "");
            while (invoiceNum.equals(""))
                invoiceNum = invoiceHeaderScanner.next().replace("\r", "");
            String invoiceDate = invoiceHeaderScanner.next().replace("\r", "");
            String customerName = invoiceHeaderScanner.next().replace("\r", "");
            InvoiceHeader invoiceHeader = new InvoiceHeader(invoiceNum,invoiceDate,customerName);
            if (hashtable.containsKey(Integer.parseInt(invoiceNum)))
                invoiceHeader.setInvoiceLines(hashtable.get(Integer.parseInt(invoiceNum)));
            else
                invoiceHeader.setInvoiceLines(new ArrayList<>());
            data.add(invoiceHeader);
        }
        invoiceHeaderScanner.close();
        return data;
    }

    // Function which writes all invoices
    public void writeFile(ArrayList<InvoiceHeader> data){


        FileWriter invoiceLineWriter = null;
        FileWriter invoiceHeaderWriter = null;
        try {
            // Initializing writers
            invoiceLineWriter = new FileWriter(INVOICE_LINE_PATH);
            invoiceHeaderWriter = new FileWriter(INVOICE_HEADER_PATH);
            invoiceLineWriter.write("invoiceNum,itemName,itemPrice,Count\n");
            invoiceHeaderWriter.write("invoiceNum,invoiceDate,CustomerName\n");

            for (InvoiceHeader invoiceHeader : data){
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
            System.err.println(e);
            System.exit(-1);
        }
    }
}
