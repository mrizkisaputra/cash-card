package com.cashcard.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data @Builder
public class CashCardResponse {
    private String id;
    private BigDecimal amount;
    private String owner;
}
