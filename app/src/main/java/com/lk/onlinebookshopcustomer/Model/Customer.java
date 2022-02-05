package com.lk.onlinebookshopcustomer.Model;

import java.util.Date;

public class Customer {

    String cus_name;
    String cus_address;
    String cus_email;
    String cus_mobileNo;

    String fcmID;
    String cus_picPath;
    String password;
    String customerId;

    public Customer() {
    }

    public Customer(String cus_name, String cus_address, String cus_email, String cus_mobileNo, String cus_picPath,String password) {
        this.cus_name = cus_name;
        this.cus_address = cus_address;
        this.cus_email = cus_email;
        this.cus_mobileNo = cus_mobileNo;
        this.cus_picPath = cus_picPath;
        this.password = password;

    }

    public Customer(String cus_name, String cus_address, String cus_email, String cus_mobileNo) {
        this.cus_name = cus_name;
        this.cus_address = cus_address;
        this.cus_email = cus_email;
        this.cus_mobileNo = cus_mobileNo;
    }

    public String getCus_name() {
        return cus_name;
    }

    public void setCus_name(String cus_name) {
        this.cus_name = cus_name;
    }

    public String getCus_address() {
        return cus_address;
    }

    public void setCus_address(String cus_address) {
        this.cus_address = cus_address;
    }

    public String getCus_email() {
        return cus_email;
    }

    public void setCus_email(String cus_email) {
        this.cus_email = cus_email;
    }

    public String getCus_mobileNo() {
        return cus_mobileNo;
    }

    public void setCus_mobileNo(String cus_mobileNo) {
        this.cus_mobileNo = cus_mobileNo;
    }


//    public String getCustomerId() {
//        return customerId;
//    }
//
//    public void setCustomerId(String customerId) {
//        this.customerId = customerId;
//    }

    public String getCus_picPath() {
        return cus_picPath;
    }

    public void setCus_picPath(String cus_picPath) {
        this.cus_picPath = cus_picPath;
    }

//    public String getFcmID() {
//        return fcmID;
//    }
//
//    public void setFcmID(String fcmID) {
//        this.fcmID = fcmID;
//    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

}
