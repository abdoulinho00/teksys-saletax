package com.teksys.salestax;

import com.teksys.salestax.dto.Category;
import com.teksys.salestax.dto.Item;
import com.teksys.salestax.dto.SalesRequestDto;
import com.teksys.salestax.dto.SalesResponseDto;
import com.teksys.salestax.service.SalesTaxService;
import com.teksys.salestax.service.impl.SalesTaxServiceImpl;

import java.util.Arrays;

public class SalesTaxMain {

    public static void main(String[] args){
        SalesTaxService salesTaxService = new SalesTaxServiceImpl();

        SalesRequestDto input1 = new SalesRequestDto(Arrays.asList(
                new Item("book", 12.49,1 , Category.FOOD, false),
                new Item("music CD", 14.99,1 , null, false),
                new Item("chocolate bar", 0.85,1 , Category.FOOD, false)
        ));
        SalesRequestDto input2 = new SalesRequestDto(Arrays.asList(
                new Item("box of chocolate", 10.00,1 , Category.FOOD, true),
                new Item("bottle of perfume", 47.50,1 , null, true)
        ));

        SalesRequestDto input3 = new SalesRequestDto(Arrays.asList(
                new Item("bottle of perfume", 27.99,1 , null, true),
                new Item("bottle of perfume", 18.99,1 , null, false),
                new Item("packet of headache pills", 9.75,1 , Category.MEDICAL_SUPPLIES, false),
                new Item("box of chocolates", 11.25,1 , Category.FOOD, true)
        ));


        //print input1
        System.out.println("input1:");
        printRequest(input1);
        //print input2
        System.out.println("\ninput2:");
        printRequest(input2);
        //print input3
        System.out.println("\ninput3:");
        printRequest(input3);

        //print output1
        System.out.println("\nOutput1:");
        printResponse(salesTaxService.calculateTax(input1));
        //print output2
        System.out.println("\nOutput2:");
        printResponse(salesTaxService.calculateTax(input2));
        //print output3
        System.out.println("\nOutput3:");
        printResponse(salesTaxService.calculateTax(input3));

    }

    private static void printResponse(SalesResponseDto responseDto) {
        responseDto.getItems().forEach(
                item -> {
                    StringBuilder sb = new StringBuilder();
                    sb.append("- "+item.getQuantity());
                    if(item.isImported()){
                        sb.append(" imported");
                    }
                    sb.append(String.format(" %s : %.2f", item.getName(), item.getTotalPrice()));
                    System.out.println(sb.toString());
                }
        );
        System.out.println(String.format("Sales taxes : %.2f Total: %.2f", responseDto.getTotalTaxes(), responseDto.getTotalPrice()));
    }

    private static void printRequest(SalesRequestDto input1) {
        input1.getItems().forEach(
                item -> {
                    StringBuilder sb = new StringBuilder();
                    sb.append("- "+item.getQuantity());
                    if(item.isImported()){
                        sb.append(" imported");
                    }
                    sb.append(String.format(" %s at %.2f", item.getName(), item.getPrice()));
                    System.out.println(sb.toString());
                }
        );
    }
}
