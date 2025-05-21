package com.cashcard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateCashCardDto {
    @NotNull @NotBlank
    private String owner;

    @NotNull
    private BigDecimal amount;
}
