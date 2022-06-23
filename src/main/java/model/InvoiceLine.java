package model;

public class InvoiceLine {
    String invoiceNum;
    String itemName;
    String itemPrice;
    String count;

    public InvoiceLine(String invoiceNum, String itemName, String itemPrice, String count) {
        this.invoiceNum = invoiceNum;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.count = count;
    }


    @Override
    public String toString() {
        return "InvoiceLine{" +
                "invoiceNum='" + invoiceNum + '\'' +
                ", itemName='" + itemName + '\'' +
                ", itemPrice='" + itemPrice + '\'' +
                ", count='" + count + '\'' +
                '}';
    }
}
