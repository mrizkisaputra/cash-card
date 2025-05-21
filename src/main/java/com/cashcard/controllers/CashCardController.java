package com.cashcard.controllers;

import com.cashcard.dto.ApiResponse;
import com.cashcard.dto.CashCardResponse;
import com.cashcard.dto.CreateCashCardDto;
import com.cashcard.dto.Pagination;
import com.cashcard.entities.CashCard;
import com.cashcard.repositories.CashCardRepository;
import com.cashcard.services.CashCardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/cashcards")
@RequiredArgsConstructor
public class CashCardController {
    private final CashCardService cashCardService;
    private final CashCardRepository cashCardRepository;

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<Object> handleGetDetailCashCard(@PathVariable String id) {
        CashCardResponse cashCardResponse = this.cashCardService.getDetail(id);
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("success")
                .data(cashCardResponse)
                .build();
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<Void> handleCreateCashCard(
            @Valid @RequestBody CreateCashCardDto newCashCard,
            UriComponentsBuilder ucb
    ) {
        CashCard cashCard = this.cashCardService.createCashCard(newCashCard);
        URI location = ucb.path("/api/cashcards/{id}")
                .buildAndExpand(cashCard.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<Object> handleGetAll(Pageable pageable) {
        Page<CashCard> cashCards = this.cashCardRepository.findAll(pageable);
        Pagination pagination = Pagination.builder()
                .totalElement(cashCards.getTotalElements())
                .totalPage(cashCards.getTotalPages())
                .size(cashCards.getSize())
                .build();
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("success")
                .data(cashCards.getContent())
                .pagination(pagination)
                .build();
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }
}
