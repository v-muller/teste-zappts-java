package com.vmuller.github.desafiozappts.repositories;

import com.vmuller.github.desafiozappts.entities.Card;
import com.vmuller.github.desafiozappts.util.CardCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.UUID;

@DataJpaTest
@DisplayName("Tests for cardRepository")
class CardRepositoryTest {
    @Autowired
    CardRepository cardRepository;

    @Test
    @DisplayName("Save persists card when successfully")
    void save_PersistCard_WhenSuccessfully() {
        Card cardToBeSaved = CardCreator.createCard();
        Card cardSaved = this.cardRepository.save(cardToBeSaved);

        Assertions.assertThat(cardSaved).isNotNull();
        Assertions.assertThat(cardSaved.getId()).isNotNull();
        Assertions.assertThat(cardSaved.getName()).isEqualTo(cardToBeSaved.getName());
    }

    @Test
    @DisplayName("Save updates Card when successfully")
    void save_UpdateCard_WhenSuccessfully() {
        Card cardToBeSaved = CardCreator.createCard();
        Card cardSaved = this.cardRepository.save(cardToBeSaved);

        cardToBeSaved.setName("Craudio");
        cardToBeSaved.setEdition("456");
        cardToBeSaved.setFoil(true);
        cardToBeSaved.setPrice(788.5);
        cardToBeSaved.setQuantity(10);

        Card cardUpdated = this.cardRepository.save(cardToBeSaved);


        Assertions.assertThat(cardUpdated.getName()).isEqualTo(cardToBeSaved.getName());
        Assertions.assertThat(cardUpdated.getEdition()).isEqualTo(cardToBeSaved.getEdition());
        Assertions.assertThat(cardUpdated.getFoil()).isEqualTo(cardToBeSaved.getFoil());
        Assertions.assertThat(cardUpdated.getPrice()).isEqualTo(cardToBeSaved.getPrice());
        Assertions.assertThat(cardUpdated.getQuantity()).isEqualTo(cardToBeSaved.getQuantity());
    }

    @Test
    @DisplayName("Delete remove Card when successfully")
    void delete_RemovesCard_WhenSuccessfully() {
        Card cardToBeSaved = CardCreator.createCard();
        Card cardSaved = this.cardRepository.save(cardToBeSaved);

        this.cardRepository.deleteById(cardSaved.getId());

        Optional<Card> cardOptional = this.cardRepository.findById(cardSaved.getId());
        Assertions.assertThat(cardOptional.isPresent()).isFalse();
    }

    @Test
    @DisplayName("Find by id returns the same Card saved id")
    void findById_ReturnsTheSameObjectIdSaved() {
        Card cardToBeSaved = CardCreator.createCard();
        Card cardSaved = this.cardRepository.save(cardToBeSaved);

        Optional<Card> cardOptional = this.cardRepository.findById(cardSaved.getId());

        Assertions.assertThat(cardOptional.get()).isEqualTo(cardSaved);
    }

    @Test
    @DisplayName("Find by id returns empty because the id is wrong")
    void findById_ReturnsEmptyObject() {
        Card cardToBeSaved = CardCreator.createCard();
        Card cardSaved = this.cardRepository.save(cardToBeSaved);

        Optional<Card> cardOptional = this.cardRepository.findById(UUID.randomUUID());

        Assertions.assertThat(cardOptional.isPresent()).isFalse();
    }
}