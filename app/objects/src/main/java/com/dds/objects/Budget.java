package com.dds.objects;

import java.util.UUID;

public class Budget {
    //region ctor...

    public Budget() {

    }

    public Budget(String name, String price, Integer color) {
        this.name = name;
        this.price = price;
        this.color = color;
    }

    //endregion

    //region private members declaration

    private String name;
    private String price;
    private Integer color;
    private UUID id = UUID.randomUUID();

    //endregion

    //region getters/setters

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

    // endregion

}