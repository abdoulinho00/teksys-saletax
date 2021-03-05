package com.teksys.salestax.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesRequestDto {
    @NotNull(message = "List of items cannot be null")
    @Valid
    private List<Item> items;
}
