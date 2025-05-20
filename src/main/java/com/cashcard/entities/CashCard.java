package com.cashcard.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity @Table(name = "cashcards")
@Data @NoArgsConstructor
public class CashCard {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private BigDecimal amount;

    @Column(nullable = false, unique = true)
    private String owner;
}
