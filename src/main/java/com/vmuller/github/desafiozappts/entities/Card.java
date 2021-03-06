package com.vmuller.github.desafiozappts.entities;


import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "TB_CARDS")
public class Card implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false, length = 30)
    private String name;
    @Column(nullable = false, length = 5)
    private String edition;
    @Column(nullable = false, length = 15)
    private String language;
    @Column(nullable = false, length = 5)
    private Boolean foil;
    @Column(nullable = false, length = 7)
    private Double price;
    @Column(nullable = false, length = 5)
    private Integer quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "deck_id")
    private Deck deck;

    public Card() {
    }

    public Card(String name, String edition, String language, Boolean foil, Double price, Integer quantity, Deck deck) {
        this.name = name;
        this.edition = edition;
        this.language = language;
        this.foil = foil;
        this.price = price;
        this.quantity = quantity;
        this.deck = deck;
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

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Boolean getFoil() {
        return foil;
    }

    public void setFoil(Boolean foil) {
        this.foil = foil;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }
}
