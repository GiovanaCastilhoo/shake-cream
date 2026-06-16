package com.shakecream.app.models;

public class Additional {
    public int id;
    public String name;
    public double price;

    public Additional() {
    }

    public Additional(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() { 
        return name; 
    }

    public double getPrice() { 
        return price; 
    }
    

    public void setId(int id) {
        this.id = id;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
