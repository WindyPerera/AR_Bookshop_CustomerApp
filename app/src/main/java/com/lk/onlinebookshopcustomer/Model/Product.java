package com.lk.onlinebookshopcustomer.Model;

public class Product {

    String product_id;
    String product_name;
    String brand;
    String category;
    String pro_image;
    double price;


    public Product() {
    }

    public Product( String product_name, String brand, String type, double price,String imagePath) {

        this.product_name = product_name;
        this.brand = brand;
        this.category = type;
        this.price = price;
        this.pro_image =imagePath;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String type) {
        this.category = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

//    public int getPages() {
//        return pages;
//    }
//
//    public void setPages(int pages) {
//        this.pages = pages;
//    }

    public String getPro_image() {
        return pro_image;
    }

    public void setPro_image(String pro_image) {
        this.pro_image = pro_image;
    }
}
