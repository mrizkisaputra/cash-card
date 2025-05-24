package com.cashcard.repositories;

import com.cashcard.entities.CashCard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CashCardRepository extends JpaRepository<CashCard, String> {
    Page<CashCard> findAllByOwner(String owner, Pageable pageable);

    @Override
    default Page<CashCard> findAll(Pageable pageable) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String owner = auth.getName();

        return findAllByOwner(owner, pageable);
    }
}
