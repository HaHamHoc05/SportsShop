package com.example.giaybongda.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CartItem implements Serializable {
    private String key;
    private Integer productId;
    private String name;
    private String image;
    private Integer sizeId;
    private String sizeLabel;
    private Integer colorId;
    private String colorLabel;
    private Long unitPrice;
    private int quantity;

    public long getSubtotal() {
        return unitPrice * (long) quantity;
    }

}
