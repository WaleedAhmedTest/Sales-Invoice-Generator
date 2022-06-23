package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

public class FileOperations {

    public ArrayList<InvoiceHeader> readFile() throws FileNotFoundException {
        ArrayList<InvoiceHeader> data = new ArrayList<>();

        // Reading all invoice lines
        Hashtable<Integer,ArrayList<InvoiceLine>>hashtable = new Hashtable<>();
        Scanner invoiceLineScanner = new Scanner(new File("src/main/resources/InvoiceLine.csv"));
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
        Scanner invoiceHeaderScanner = new Scanner(new File("src/main/resources/InvoiceHeader.csv"));
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

    public void writeFile(ArrayList<InvoiceHeader> data){
        //TODO
    }
}
