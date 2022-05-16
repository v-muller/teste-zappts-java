package com.vmuller.github.desafiozappts.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vmuller.github.desafiozappts.entities.Card;
import com.vmuller.github.desafiozappts.entities.Player;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serial;
import java.util.List;
import java.util.UUID;

public class DeckDTO {
    @NotBlank
    private List<Card> cards;
    @NotBlank
    private Player player;

    public DeckDTO(){}

    public DeckDTO(List<Card> cards, Player player) {
        this.cards = cards;
        this.player = player;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
