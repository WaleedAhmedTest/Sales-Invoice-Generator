package model;

import java.util.ArrayList;

public class InvoiceHeader {
    int invoiceNum;
    String invoiceDate;
    String customerName;
    ArrayList<InvoiceLine> invoiceLines;
}
