package com.example.phone_project.Model;

public class Phone {

    int id;
    String name;
    String description;
    int quantity;
    double price;
    String img;


    public static final String TABLE_NAME = "phones";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_IMAGE = "img";
    public static final String COL_QUANTITY = "quantity";
    public static final String COL_PRICE = "price";
    public static final String TABLE_CREATE = "create table "+TABLE_NAME+" ("+COL_ID+ " integer,"+COL_NAME+" text not null,"+COL_DESCRIPTION+" text not null,"+COL_QUANTITY+" integer,"+COL_PRICE+" double,"+COL_IMAGE+" text not null)";

    public Phone() {
    }


    public Phone(int id, String name, String description, int quantity, double price, String img) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.img = img;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
