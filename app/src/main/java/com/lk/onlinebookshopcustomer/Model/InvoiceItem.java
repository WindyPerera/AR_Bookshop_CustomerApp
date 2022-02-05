package com.lk.onlinebookshopcustomer.Model;

public class InvoiceItem {

    String invoiceId;

    String productId;
    int qty;
    double subAmount;

    public InvoiceItem() {
    }

    public InvoiceItem(String invoiceId, String productId, int qty, double subAmount) {
        this.invoiceId = invoiceId;
        this.productId = productId;
        this.qty = qty;
        this.subAmount = subAmount;
//        this.invoiceItemID = invoiceItemID;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getSubAmount() {
        return subAmount;
    }

    public void setSubAmount(double subAmount) {
        this.subAmount = subAmount;
    }


}
