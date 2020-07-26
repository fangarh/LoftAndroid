package com.dds.loftmoney;

import java.util.UUID;

public class BudgetRow {
    private String name;
    private String price;
    private Integer color;
    private UUID id = UUID.randomUUID();

    public String getName() {
        return name;
    }

    public UUID getId() {
        return id;
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

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }

    public BudgetRow() {

    }

    public BudgetRow(String name, String price, Integer color) {
        this.name = name;
        this.price = price;
        this.color = color;
    }
}
