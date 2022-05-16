package com.vmuller.github.desafiozappts.repositories;

import com.vmuller.github.desafiozappts.entities.Deck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ListCardRepository extends JpaRepository<Deck, UUID> {
}
