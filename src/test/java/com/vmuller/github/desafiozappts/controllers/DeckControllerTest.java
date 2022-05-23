package com.vmuller.github.desafiozappts.controllers;

import com.vmuller.github.desafiozappts.entities.Deck;
import com.vmuller.github.desafiozappts.services.CardService;
import com.vmuller.github.desafiozappts.services.DeckService;
import com.vmuller.github.desafiozappts.services.PlayerService;
import com.vmuller.github.desafiozappts.util.DeckCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class DeckControllerTest {

    @InjectMocks
    DeckController deckController;

    @Mock
    DeckService deckServiceMock;

    @Mock
    PlayerService playerServiceMock;

    @Mock
    CardService cardServiceMock;


    @Test
    @DisplayName("FindAll returns Page of Decks when successfully")
    void findAll_ReturnsPageOfDecks_WhenSuccessfully() {
        Page<Deck> expectedDeck = DeckCreator.createPageDeck();
        when(deckServiceMock.findAll(ArgumentMatchers.any()))
                .thenReturn(expectedDeck);

        Page<Deck> deckPage = this.deckController.findAll(null).getBody();

        Assertions.assertThat(deckPage).isNotNull();

        Assertions.assertThat(deckPage.toList()).isNotEmpty()
                        .hasSize(1);

        Assertions.assertThat(deckPage.toList().get(0)).isEqualTo(expectedDeck.toList().get(0));
    }

    @Test
    @DisplayName("FindById returns Page of Deck when successfully")
    void findById_ReturnsPageOfDecks_WhenSuccessfully() {
        Optional<Deck> expectedDeck = DeckCreator.createOptionalDeck();
        when(deckServiceMock.findById(ArgumentMatchers.any(UUID.class)))
                .thenReturn(expectedDeck);

        ResponseEntity<Object> byId = this.deckController.findById(UUID.fromString("35c70ae2-3db6-41c3-afe7-43d2c4fbb488"));

        Assertions.assertThat(byId).isNotNull();

        Assertions.assertThat(byId.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("Save returns Deck when successfully")
    void save_ReturnsDeck_WhenSuccessfully() {
        when(deckServiceMock.save(ArgumentMatchers.any(Deck.class)))
                .thenReturn(DeckCreator.createDeck());

        Deck deckToBeSaved = DeckCreator.createDeck();
        ResponseEntity<Deck> deck = deckController.saveDeck(deckToBeSaved);

        Assertions.assertThat(deck).isNotNull();
        Assertions.assertThat(deck.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(deck.getBody().getId()).isEqualTo(deckToBeSaved.getId());
    }

    @Test
    @DisplayName("Update returns Deck when successfully")
    void update_ReturnsDeck_WhenSuccessfully() {
        when(deckServiceMock.save(ArgumentMatchers.any(Deck.class)))
                .thenReturn(DeckCreator.createUpdateDeck());

        when(deckServiceMock.findById(ArgumentMatchers.any(UUID.class)))
                .thenReturn(Optional.of(DeckCreator.createDeck()));

        Deck deckToBeSaved = DeckCreator.createDeck();

        Assertions.assertThatCode(() -> deckController.updateDeck(deckToBeSaved.getId(),
                DeckCreator.createUpdateDeck()).getBody()).doesNotThrowAnyException();

        ResponseEntity<Object> deck = deckController.updateDeck(deckToBeSaved.getId(),
                DeckCreator.createUpdateDeck());
        Assertions.assertThat(deck).isNotNull();
        Assertions.assertThat(deck.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("Delete removes Deck when successfully")
    void delete_RemovesDeck_WhenSuccessfully() {
        BDDMockito.doNothing().when(deckServiceMock).delete(ArgumentMatchers.any(UUID.class));

        when(deckServiceMock.findById(ArgumentMatchers.any(UUID.class)))
                .thenReturn(Optional.of(DeckCreator.createDeck()));

        Assertions.assertThatCode(() -> deckController.delete(DeckCreator.createDeck()
                .getId()).getBody()).doesNotThrowAnyException();

        ResponseEntity<Object> deck = deckController.delete(DeckCreator.createDeck().getId());
        Assertions.assertThat(deck).isNotNull();
        Assertions.assertThat(deck.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}