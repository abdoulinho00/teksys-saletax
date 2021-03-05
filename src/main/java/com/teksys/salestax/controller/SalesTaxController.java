package com.teksys.salestax.controller;

import com.teksys.salestax.dto.SalesRequestDto;
import com.teksys.salestax.dto.SalesResponseDto;
import com.teksys.salestax.service.SalesTaxService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class SalesTaxController {

    private final SalesTaxService salesTaxService;
    @PostMapping
    public SalesResponseDto calculateTax(@RequestBody @Valid SalesRequestDto request){
        return salesTaxService.calculateTax(request);
    }
}
