package com.theekshana.onlineshop.Model;

public class ModelOderedItem {

    private   String pid, name, price ,qty;

    public ModelOderedItem() {
    }

    public ModelOderedItem(String pid, String name, String price, String qty) {
        this.pid = pid;
        this.name = name;
        this.price = price;
        this.qty = qty;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}
