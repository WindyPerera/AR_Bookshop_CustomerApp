package com.lk.onlinebookshopcustomer.Model;

public class Invoice {


    double totalAmount;
    String customerId;
    String paymentType;
    String recipientName;
    String address;
    int postCode;

    String currentDate;
    String currentTime;
    String orderedStatus;

    public Invoice() {

    }

    public Invoice( double totalAmount, String customerId, String recipientName, String address, String currentDate, String currentTime, String orderedStatus, String paymentType, int postCode) {

        this.totalAmount = totalAmount;
        this.customerId = customerId;
        this.recipientName = recipientName;
        this.address = address;
        this.currentDate = currentDate;
        this.currentTime = currentTime;
        this.orderedStatus = orderedStatus;
        this.paymentType = paymentType;
        this.postCode = postCode;
    }



    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }

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

    public String getOrderedStatus() {
        return orderedStatus;
    }

    public void setOrderedStatus(String orderedStatus) {
        this.orderedStatus = orderedStatus;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public int getPostCode() {
        return postCode;
    }

    public void setPostCode(int postCode) {
        this.postCode = postCode;
    }
}
