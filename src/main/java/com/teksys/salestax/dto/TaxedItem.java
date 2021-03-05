package com.teksys.salestax.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaxedItem {

    private String name;
    private double totalPrice;
    private int quantity;
    private Category category;
    private double taxes;
    private boolean imported;
}
