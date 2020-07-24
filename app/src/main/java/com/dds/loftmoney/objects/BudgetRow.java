package com.dds.loftmoney.objects;

import java.util.UUID;

public class BudgetRow {
    private String _name;
    private String _price;
    private Integer _color;
    private UUID _id = UUID.randomUUID();

    public String getName() {
        return _name;
    }

    public UUID getId() {
        return _id;
    }

    public void setName(String name) {
        _name = name;
    }

    public String getPrice() {
        return _price;
    }

    public void setPrice(String price) {
        _price = price;
    }

    public Integer getColor() {
        return _color;
    }

    public void setColor(Integer color) {
        _color = color;
    }

    public BudgetRow() {

    }

    public BudgetRow(String name, String price, Integer color) {
        _name = name;
        _price = price;
        _color = color;
    }
}
