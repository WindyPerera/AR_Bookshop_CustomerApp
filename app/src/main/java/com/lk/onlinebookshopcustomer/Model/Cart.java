package com.lk.onlinebookshopcustomer.Model;

import java.util.Objects;

public class Cart {


//    @Override
//    public String toString() {
//        return "Cart{" +
//                "product=" + product +
//                ", qty=" + qty +
//                '}';
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Cart cart = (Cart) o;
//        return getQty() == cart.getQty() &&
//                getProduct().equals(cart.getProduct());
//    }


    String productID;
    String customerId;
    String productName;
    String currentDate;
    String currentTime;
    int totalQty;
    double totalPrice;
    double productPrice;

    public Cart() {
    }

    public Cart(String productID, String productName, String currentDate, String currentTime, int totalQty, double totalPrice, double productPrice,String customerId) {
        this.productID = productID;
        this.productName = productName;
        this.currentDate = currentDate;
        this.currentTime = currentTime;
        this.totalQty = totalQty;
        this.totalPrice = totalPrice;
        this.productPrice = productPrice;
        this.customerId = customerId;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public int getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(int totalQty) {
        this.totalQty = totalQty;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
