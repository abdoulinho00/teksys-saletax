package com.teksys.salestax.dto;

import lombok.Data;

import java.util.List;

@Data
public class SalesResponseDto {
    private List<TaxedItem> items;
    private Double totalTaxes;
    private Double totalPrice;
}
