package com.shakecream.app.models;

public class Additional {
    public int id;
    public String name;
    public double price;
    private boolean selected;

    public Additional() {
    }

    public Additional(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.selected = false;
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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
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
