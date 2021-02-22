package com.example.demo.dto;

import javax.validation.constraints.NotNull;

public class AddPositionToBucketDTO {

    @NotNull
    private int quantity;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
