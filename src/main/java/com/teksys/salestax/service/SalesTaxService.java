package com.teksys.salestax.service;

import com.teksys.salestax.dto.SalesRequestDto;
import com.teksys.salestax.dto.SalesResponseDto;

public interface SalesTaxService {
    SalesResponseDto calculateTax(SalesRequestDto salesRequestDto);
}
