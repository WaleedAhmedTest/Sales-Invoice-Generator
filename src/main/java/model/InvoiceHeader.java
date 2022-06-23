package model;

import java.util.ArrayList;

public class InvoiceHeader {
    String invoiceNum;
    String invoiceDate;
    String customerName;
    ArrayList<InvoiceLine> invoiceLines;

    public InvoiceHeader(String invoiceNum, String invoiceDate, String customerName) {
        this.invoiceNum = invoiceNum;
        this.invoiceDate = invoiceDate;
        this.customerName = customerName;
    }

    public void setInvoiceLines(ArrayList<InvoiceLine> invoiceLines) {
        this.invoiceLines = invoiceLines;
    }

    @Override
    public String toString() {
        return "InvoiceHeader{" +
                "invoiceNum='" + invoiceNum + '\'' +
                ", invoiceDate='" + invoiceDate + '\'' +
                ", customerName='" + customerName + '\'' +
                ", invoiceLines=" + invoiceLines +
                '}';
    }
}
