package com.dds.objects;

import java.util.UUID;

public class Budget {
    //region ctor...

    public Budget() {

    }

    public Budget(String name, String price) {
        this.name = name;
        this.price = price;

    }

    //endregion

    //region private members declaration

    private String name;
    private String price;

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

    // endregion

}