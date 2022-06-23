package model;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FileOperationsTest {

    @org.junit.jupiter.api.Test
    void readFile() throws FileNotFoundException {
        FileOperations fileOperations = new FileOperations();
        ArrayList<InvoiceHeader> invoiceHeaders = fileOperations.readFile();
        System.out.println("BOOM");
    }

    @org.junit.jupiter.api.Test
    void writeFile() {
    }
}