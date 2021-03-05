package com.teksys.salestax.service.impl;

import com.teksys.salestax.dto.*;
import com.teksys.salestax.service.SalesTaxService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SalesTaxServiceImpl implements SalesTaxService {

    private Map<Category, Double> taxMap;
    private Double defaultTax;
    private Double importTax;

    public SalesTaxServiceImpl() {
        // this could be part of a config
        taxMap = new HashMap<>();
        taxMap.put(Category.BOOKS, 0.);
        taxMap.put(Category.FOOD, 0.);
        taxMap.put(Category.MEDICAL_SUPPLIES, 0.);

        defaultTax = 10.;
        importTax = 5.;
    }

    @Override
    public SalesResponseDto calculateTax(SalesRequestDto salesRequestDto) {
        double totalPrice = 0., totalTaxes = 0.;

        List<TaxedItem> items = new ArrayList<>();
        for (Item item : salesRequestDto.getItems()) {
            TaxedItem taxedItem = calculateItemTax(item);
            totalPrice += taxedItem.getTotalPrice();
            totalTaxes += taxedItem.getTaxes();
            items.add(taxedItem);
        }

        SalesResponseDto salesResponseDto = new SalesResponseDto();
        salesResponseDto.setItems(items);
        salesResponseDto.setTotalPrice(totalPrice);
        salesResponseDto.setTotalTaxes(totalTaxes);
        return salesResponseDto;
    }

    private TaxedItem calculateItemTax(Item item) {

        double taxToApply = getTaxToApply(item);

        double taxesPaid = item.getPrice() * item.getQuantity() * (taxToApply / 100);
        taxesPaid = round(taxesPaid);

        double totalPrice = item.getPrice() * item.getQuantity() + taxesPaid;

        return TaxedItem.builder()
                .category(item.getCategory())
                .name(item.getName())
                .totalPrice(totalPrice)
                .taxes(taxesPaid)
                .imported(item.isImported())
                .quantity(item.getQuantity())
                .build();
    }

    private double getTaxToApply(Item item) {
        double taxToApply = taxMap.getOrDefault(item.getCategory(), defaultTax);
        if (item.isImported()) {
            taxToApply += importTax;
        }
        return taxToApply;
    }

    private double round(final double price) {
        double decimal  = (price - (int) price);
        decimal = (int)(decimal * 1000) /1000.;
        decimal = Math.ceil(decimal * 20); // 100/5
        double roundedDecimal = decimal /20;
        return (int) price + roundedDecimal;
    }
}
