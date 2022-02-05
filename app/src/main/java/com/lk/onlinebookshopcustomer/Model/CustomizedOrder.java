package com.lk.onlinebookshopcustomer.Model;

public class CustomizedOrder {
    String customizedOrder;
    String invoiceId;
    String orderImagePath;
    String orderDocPath;
    String description;

    public CustomizedOrder() {
    }

    public CustomizedOrder(String customizedOrder, String invoiceId, String orderImagePath, String orderDocPath, String description) {
        this.customizedOrder = customizedOrder;
        this.invoiceId = invoiceId;
        this.orderImagePath = orderImagePath;
        this.orderDocPath = orderDocPath;
        this.description = description;
    }

    public String getCustomizedOrder() {
        return customizedOrder;
    }

    public void setCustomizedOrder(String customizedOrder) {
        this.customizedOrder = customizedOrder;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getOrderImagePath() {
        return orderImagePath;
    }

    public void setOrderImagePath(String orderImagePath) {
        this.orderImagePath = orderImagePath;
    }

    public String getOrderDocPath() {
        return orderDocPath;
    }

    public void setOrderDocPath(String orderDocPath) {
        this.orderDocPath = orderDocPath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
