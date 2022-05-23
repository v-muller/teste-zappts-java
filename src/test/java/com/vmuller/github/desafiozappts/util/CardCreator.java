package com.vmuller.github.desafiozappts.util;

import com.vmuller.github.desafiozappts.entities.Card;
import com.vmuller.github.desafiozappts.entities.Deck;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;

import java.util.*;

public class CardCreator {
    public static List<Card> createCards() {
        List<Card> cards = new ArrayList<>();
        Card c1 = new Card("Black Lotus", "1993", "portuguese", false, 830005.0, 1, new Deck());
        Card c2 = new Card("Time Walk", "1998", "Portugues", true, 68000.0, 2, new Deck());
        Card c3 = new Card("Ancestral Recall", "2001", "Portugues", true, 75000.9, 4, new Deck());
        Card c4 = new Card("Underground Sea", "1997", "Portugues", false, 950005.0, 5, new Deck());
        cards.addAll(Arrays.asList(c1, c2, c3, c4));
        return cards;
    }

    public static Page<Card> createPageCardsInAlphabeticOrder() {
        List<Card> cards = new ArrayList<>();
        Card c1 = new Card("Black Lotus", "1993", "portuguese", false, 830005.0, 1, new Deck());
        Card c2 = new Card("Time Walk", "1998", "Portugues", true, 68000.0, 2, new Deck());
        Card c3 = new Card("Ancestral Recall", "2001", "Portugues", true, 75000.9, 4, new Deck());
        Card c4 = new Card("Underground Sea", "1997", "Portugues", false, 950005.0, 5, new Deck());
        cards.addAll(Arrays.asList(c1, c2, c3, c4));
        Collections.sort(cards, (o1, o2) -> o1.getName().compareTo(o2.getName()));

        Page<Card> cardPage = new PageImpl(cards, Pageable.unpaged(), cards.size());
        return cardPage;
    }

    public static Page<Card> createPageCardsInQuantityOrder() {
        List<Card> cards = new ArrayList<>();
        Card c1 = new Card("Black Lotus", "1993", "portuguese", false, 830005.0, 1, new Deck());
        Card c2 = new Card("Time Walk", "1998", "Portugues", true, 68000.0, 2, new Deck());
        Card c3 = new Card("Ancestral Recall", "2001", "Portugues", true, 75000.9, 4, new Deck());
        Card c4 = new Card("Underground Sea", "1997", "Portugues", false, 950005.0, 5, new Deck());
        cards.addAll(Arrays.asList(c1, c2, c3, c4));
        Collections.sort(cards, (o1, o2) -> o1.getPrice() > o2.getPrice() ? -1 : 1);

        Page<Card> cardPage = new PageImpl(cards, Pageable.unpaged(), cards.size());
        return cardPage;
    }

    public static Card createCard() {
        return new Card("Black Lotus", "1993", "portuguese", false, 830005.0, 1, new Deck());
    }

    public static Card createCardImmutableId() {
        Card card = new Card("Black Lotus", "1993", "portuguese", false, 830005.0, 1, new Deck());
        card.setId(UUID.fromString("35c70ae2-3db6-41c3-afe7-43d2c4fbb488"));
        return card;
    }

    public static Object createObjImmutableId() {
        Card card = new Card("Black Lotus", "1993", "portuguese", false, 830005.0, 1, new Deck());
        card.setId(UUID.fromString("35c70ae2-3db6-41c3-afe7-43d2c4fbb488"));
        return card;
    }

    public static Card updateCardImmutableId() {
        Card card = new Card("Time Walk", "1998", "Portugues", true, 68000.0, 2, new Deck());
        card.setId(UUID.fromString("35c70ae2-3db6-41c3-afe7-43d2c4fbb488"));
        return card;
    }
}
