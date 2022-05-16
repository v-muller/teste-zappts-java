package com.vmuller.github.desafiozappts.dtos;

import com.vmuller.github.desafiozappts.entities.Deck;
import com.vmuller.github.desafiozappts.entities.Player;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class PlayerDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;
    @NotBlank
    private String name;
    @NotBlank
    private String email;

    @NotBlank
    private List<Deck> decks;

    public PlayerDTO(Player player){
        this.setId(player.getId());
        this.setName(player.getName());
        this.setEmail(player.getEmail());
        this.setDecks(player.getDecks());
    }

    public UUID getId() {
        return id;
    }



    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Deck> getDecks() {
        return decks;
    }

    public void setDecks(List<Deck> decks) {
        this.decks = decks;
    }
}
