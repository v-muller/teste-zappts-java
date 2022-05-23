package com.vmuller.github.desafiozappts.services;

import com.vmuller.github.desafiozappts.entities.Card;
import com.vmuller.github.desafiozappts.entities.Deck;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlayerMethods {

    public Optional<Card> InsertCardOnDeck(Card card, UUID deckId);

    public Optional<Card> InsertDeck(Deck deck);

    public List<Optional<Card>> findAllNameOrder();

    public List<Optional<Card>> findAllValueOrder();

    public void deleteByid(UUID id, int qtd);

    public void updateValues(int qtd);

}
