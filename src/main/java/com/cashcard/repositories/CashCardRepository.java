package com.cashcard.repositories;

import com.cashcard.entities.CashCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CashCardRepository extends JpaRepository<CashCard, String> {
}
