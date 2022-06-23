package model;

import java.io.FileNotFoundException;
import java.util.ArrayList;

class FileOperationsTest {

    @org.junit.jupiter.api.Test
    void readFile() throws FileNotFoundException {
        FileOperations fileOperations = new FileOperations();
        ArrayList<InvoiceHeader> invoiceHeaders = fileOperations.readFile();
        for (InvoiceHeader invoiceHeader : invoiceHeaders)
            System.out.println(invoiceHeader);
    }

    @org.junit.jupiter.api.Test
    void writeFile() {

    }
}