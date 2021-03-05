package com.teksys.salestax.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    private String name;
    @DecimalMin(value = "0.", message = "Price should have a positive value")
    private double price;
    @Min(value = 0, message = "Quantity should have a positive value")
    private int quantity;
    private Category category;
    private boolean imported;
}
