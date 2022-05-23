package com.vmuller.github.desafiozappts.util;

import com.vmuller.github.desafiozappts.entities.Deck;
import com.vmuller.github.desafiozappts.entities.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class DeckCreator {

    public static Deck createDeck() {
        Deck deck = new Deck(new Player("Joao miguel", "miguel@gmail.com", "1234"));
        deck.setId(UUID.fromString("35c70ae2-3db6-41c3-afe7-43d2c4fbb488"));
        return deck;
    }

    public static Deck createUpdateDeck() {
        Deck deck = new Deck(new Player("Carolina", "carol@gmail.com", "1234"));
        deck.setId(UUID.fromString("35c70ae2-3db6-41c3-afe7-43d2c4fbb488"));
        return deck;
    }

    public static Page<Deck> createPageDeck() {
        Deck deck = new Deck(new Player("Joao miguel", "miguel@gmail.com", "1234"));
        deck.setId(UUID.fromString("35c70ae2-3db6-41c3-afe7-43d2c4fbb488"));
        List<Deck> decks = Arrays.asList(deck);
        Page<Deck> deckPage = new PageImpl(decks, Pageable.unpaged(), decks.size());
        return deckPage;
    }

    public static Optional<Deck> createOptionalDeck() {
        Deck deck = new Deck(new Player("Joao miguel", "miguel@gmail.com", "1234"));
        deck.setId(UUID.fromString("35c70ae2-3db6-41c3-afe7-43d2c4fbb488"));

        return Optional.of(deck);
    }
}
