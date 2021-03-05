package com.teksys.salestax.service;

import com.teksys.salestax.dto.*;
import com.teksys.salestax.service.impl.SalesTaxServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SalesTaxServiceTest {

    SalesTaxServiceImpl salesTaxService = new SalesTaxServiceImpl();

    @Test
    public void calculateTax_whenEmptyList_ShouldReturnEmptyList(){
        SalesRequestDto salesRequestDto = new SalesRequestDto(
                Collections.emptyList()
        );

        SalesResponseDto response = salesTaxService.calculateTax(salesRequestDto);

        assertEquals(0,response.getItems().size());
        assertEquals(0.,response.getTotalPrice(), 0.001);
        assertEquals(0.,response.getTotalTaxes(), 0.001);
    }

    @Test
    public void calculateTax_whenNotImportedFood_shouldApplyAppropriateTax(){
        double price = 3.26;
        SalesRequestDto salesRequestDto = new SalesRequestDto(
                Collections.singletonList(new Item("item1" , price, 1, Category.FOOD, false))
        );

        SalesResponseDto response = salesTaxService.calculateTax(salesRequestDto);

        assertEquals(1,response.getItems().size());
        assertEquals(price,response.getTotalPrice(), 0.001);
        assertEquals(0.,response.getTotalTaxes(), 0.001);
    }

    @Test
    public void calculateTax_whenNotImportedBooks_shouldApplyAppropriateTax(){
        double price = 3.26;
        SalesRequestDto salesRequestDto = new SalesRequestDto(
                Collections.singletonList(new Item("item1" , price, 1, Category.BOOKS, false))
        );

        SalesResponseDto response = salesTaxService.calculateTax(salesRequestDto);

        assertEquals(1,response.getItems().size());
        assertEquals(price,response.getTotalPrice(), 0.001);
        assertEquals(0.,response.getTotalTaxes(), 0.001);
    }

    @Test
    public void calculateTax_whenNotImportedMedicalSupplies_shouldApplyAppropriateTax(){
        double price = 3.26;
        SalesRequestDto salesRequestDto = new SalesRequestDto(
                Collections.singletonList(new Item("item1" , price, 1, Category.MEDICAL_SUPPLIES, false))
        );

        SalesResponseDto response = salesTaxService.calculateTax(salesRequestDto);

        assertEquals(1,response.getItems().size());
        assertEquals(price,response.getTotalPrice(), 0.001);
        assertEquals(0.,response.getTotalTaxes(), 0.001);
    }

    @Test
    public void calculateTax_whenNoCategoryAndNotImported_shouldApplyDefaultTax(){
        double price = 100.;
        SalesRequestDto salesRequestDto = new SalesRequestDto(
                Collections.singletonList(new Item("item1" , price, 1, null, false))
        );

        SalesResponseDto response = salesTaxService.calculateTax(salesRequestDto);

        assertEquals(1,response.getItems().size());
        assertEquals(110.,response.getTotalPrice(), 0.001);
        assertEquals(10.,response.getTotalTaxes(), 0.001);
    }

    @Test
    public void calculateTax_whenImported_shouldAddImportTax(){
        double price = 100.;
        SalesRequestDto salesRequestDto = new SalesRequestDto(
                Collections.singletonList(new Item("item1" , price, 1, Category.FOOD, true))
        );

        SalesResponseDto response = salesTaxService.calculateTax(salesRequestDto);

        assertEquals(1,response.getItems().size());
        assertEquals(105.,response.getTotalPrice(), 0.001);
        assertEquals(5.,response.getTotalTaxes(), 0.001);

        SalesRequestDto salesRequestDto1 = new SalesRequestDto(
                Collections.singletonList(new Item("item1" , price, 1, null, true))
        );

        SalesResponseDto response1 = salesTaxService.calculateTax(salesRequestDto1);

        assertEquals(1,response1.getItems().size());
        assertEquals(115.,response1.getTotalPrice(), 0.001);
        assertEquals(15.,response1.getTotalTaxes(), 0.001);
    }

    @Test
    public void calculateTax_whenTaxed_shouldRoundUpCorrectly(){
        double price = 3.26;
        SalesRequestDto salesRequestDto = new SalesRequestDto(
                Collections.singletonList(new Item("item1" , price, 1, Category.FOOD, true))
        );

        SalesResponseDto response = salesTaxService.calculateTax(salesRequestDto);

        assertEquals(1,response.getItems().size());
        assertEquals(3.46,response.getTotalPrice(), 0.001);
        assertEquals(0.2,response.getTotalTaxes(), 0.001);

        double price1 = 3.;
        SalesRequestDto salesRequestDto1 = new SalesRequestDto(
                Collections.singletonList(new Item("item1" , price1, 1, Category.FOOD, true))
        );

        SalesResponseDto response1 = salesTaxService.calculateTax(salesRequestDto1);

        assertEquals(1,response1.getItems().size());
        assertEquals(3.15,response1.getTotalPrice(), 0.001);
        assertEquals(0.15,response1.getTotalTaxes(), 0.001);

        double price2 = 2.3;
        SalesRequestDto salesRequestDto2 = new SalesRequestDto(
                Collections.singletonList(new Item("item1" , price2, 1, Category.FOOD, true))
        );

        SalesResponseDto response2 = salesTaxService.calculateTax(salesRequestDto2);

        assertEquals(1,response2.getItems().size());
        assertEquals(2.45,response2.getTotalPrice(), 0.001);
        assertEquals(0.15,response2.getTotalTaxes(), 0.001);
    }

    @Test
    public void calculateTax_whenMultipleItems_shouldSumCorrectly(){
        SalesRequestDto salesRequestDto = new SalesRequestDto(
                Arrays.asList(
                        new Item("item1", 100, 1, Category.FOOD, true),
                        new Item("item2", 50, 3, Category.FOOD, true))

        );

        SalesResponseDto response = salesTaxService.calculateTax(salesRequestDto);

        assertEquals(2,response.getItems().size());
        assertEquals(262.5,response.getTotalPrice(), 0.001);
        assertEquals(12.5,response.getTotalTaxes(), 0.001);

        TaxedItem taxedItem1 = response.getItems().get(0);
        assertEquals("item1" , taxedItem1.getName());
        assertEquals(1 , taxedItem1.getQuantity());
        assertEquals(105. , taxedItem1.getTotalPrice(), 0.001);
        assertEquals(5. , taxedItem1.getTaxes(), 0.001);
        assertEquals(Category.FOOD , taxedItem1.getCategory());

        TaxedItem taxedItem2 = response.getItems().get(1);
        assertEquals("item2" , taxedItem2.getName());
        assertEquals(3 , taxedItem2.getQuantity());
        assertEquals(157.5 , taxedItem2.getTotalPrice(), 0.001);
        assertEquals(7.5 , taxedItem2.getTaxes(), 0.001);
        assertEquals(Category.FOOD , taxedItem2.getCategory());

    }

}
