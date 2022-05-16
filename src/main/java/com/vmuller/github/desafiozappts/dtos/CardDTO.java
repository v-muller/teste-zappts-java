package com.vmuller.github.desafiozappts.dtos;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

public class CardDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;
    @NotBlank
    private String name;
    @NotBlank
    private String edition;
    @NotBlank
    private String language;
    @NotBlank
    private Boolean foil;
    @NotBlank
    private Double price;
    @NotBlank
    private Integer quantity;

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
}
