package com.lk.onlinebookshopcustomer.Model;

public class Order {
    String ordet_date;
    String ordet_time;
    String total_amount;
    String product_Id;
    String qty;
    String price;
    double currentRider_lat;
    double currentRider_lon;
    double currentCustomer_lat;

    double currentCustormer_lon;

    public Order() {
    }

    public Order(String ordet_date, String ordet_time, String total_amount) {
        this.ordet_date = ordet_date;
        this.ordet_time = ordet_time;
        this.total_amount = total_amount;
    }



    public String getOrdet_date() {
        return ordet_date;
    }

    public void setOrdet_date(String ordet_date) {
        this.ordet_date = ordet_date;
    }

    public String getOrdet_time() {
        return ordet_time;
    }

    public void setOrdet_time(String ordet_time) {
        this.ordet_time = ordet_time;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getProduct_Id() {
        return product_Id;
    }

    public void setProduct_Id(String product_Id) {
        this.product_Id = product_Id;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
