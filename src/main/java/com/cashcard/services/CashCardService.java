package com.cashcard.services;

import com.cashcard.dto.CashCardResponse;
import com.cashcard.dto.CreateCashCardDto;
import com.cashcard.entities.CashCard;
import com.cashcard.repositories.CashCardRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CashCardService {
    private final CashCardRepository cashCardRepository;

    public CashCardResponse getDetail(String id) {
        CashCard cashCard = this.cashCardRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "cashcard not found"));

        return CashCardResponse.builder()
                .id(cashCard.getId())
                .amount(cashCard.getAmount())
                .owner(cashCard.getOwner())
                .build();
    }

    public CashCard createCashCard(CreateCashCardDto newCashCard) {
        CashCard cashCardEntity = new CashCard();
        cashCardEntity.setAmount(newCashCard.getAmount());
        cashCardEntity.setOwner(newCashCard.getOwner());

        return this.cashCardRepository.save(cashCardEntity);
    }
}
