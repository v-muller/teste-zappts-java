package com.vmuller.github.desafiozappts.repositories;

import com.vmuller.github.desafiozappts.entities.Deck;
import com.vmuller.github.desafiozappts.entities.Player;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.UUID;

@DataJpaTest
@DisplayName("Tests for DeckRepository")
class DeckRepositoryTest {
    @Autowired
    DeckRepository deckRepository;

    @Test
    @DisplayName("Save persists Deck when successfully")
    void save_PersistDeck_WhenSuccessfully(){
        Deck deckToBeSaved = createDeck();
        Deck deckSaved = deckRepository.save(deckToBeSaved);

        Assertions.assertThat(deckSaved).isNotNull();
        Assertions.assertThat(deckSaved.getId()).isNotNull();
    }

    @Test
    @DisplayName("Save updates Deck when successfully")
    void save_UpdateDeck_WhenSuccessfully(){
        Deck deckToBeSaved = createDeck();
        Deck deckSaved = deckRepository.save(deckToBeSaved);

        deckToBeSaved.setPlayer(new Player("Daniela", "dani@gmail.com", "5678"));
        Deck deckUpdated = this.deckRepository.save(deckToBeSaved);

        Assertions.assertThat(deckUpdated).isNotNull();
        Assertions.assertThat(deckUpdated.getId()).isNotNull();
        Assertions.assertThat(deckUpdated.getPlayer()).isNotNull();
        Assertions.assertThat(deckUpdated.getPlayer().getName()).isEqualTo("Daniela");
        Assertions.assertThat(deckUpdated.getPlayer().getEmail()).isEqualTo("dani@gmail.com");
        Assertions.assertThat(deckUpdated.getPlayer().getPassword()).isEqualTo("5678");
    }

    @Test
    @DisplayName("Delete remove Deck when successfully")
    void delete_RemovesDeck_WhenSuccessfully(){
        Deck deckToBeSaved = createDeck();
        Deck deckSaved = deckRepository.save(deckToBeSaved);

        this.deckRepository.deleteById(deckSaved.getId());

        Optional<Deck> deckOptional = this.deckRepository.findById(deckSaved.getId());

        Assertions.assertThat(deckOptional.isPresent()).isFalse();
    }

    @Test
    @DisplayName("Find by id returns the same deck saved id")
    void findById_ReturnsTheSameObjectIdSaved(){
        Deck deckToBeSaved = createDeck();
        Deck deckSaved = deckRepository.save(deckToBeSaved);

        Optional<Deck> wantedDeck = this.deckRepository.findById(deckSaved.getId());

        Assertions.assertThat(wantedDeck.get()).isEqualTo(deckSaved);
    }

    @Test
    @DisplayName("Find by id returns empty because the id is wrong")
    void findById_ReturnsEmptyObject(){
        Deck deckToBeSaved = createDeck();
        Deck deckSaved = deckRepository.save(deckToBeSaved);

        Deck newDeckSaved = createDeck();
        this.deckRepository.save(newDeckSaved);

        Optional<Deck> wantedDeck = this.deckRepository.findById(UUID.randomUUID());

        Assertions.assertThat(wantedDeck.isPresent()).isFalse();
    }

    private Deck createDeck(){
        return new Deck(new Player("Joao miguel", "miguel@gmail.com", "1234"));
    }
}