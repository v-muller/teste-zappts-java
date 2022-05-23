package com.vmuller.github.desafiozappts.controllers;

import com.vmuller.github.desafiozappts.entities.Card;
import com.vmuller.github.desafiozappts.entities.Deck;
import com.vmuller.github.desafiozappts.services.CardService;
import com.vmuller.github.desafiozappts.services.DeckService;
import com.vmuller.github.desafiozappts.util.CardCreator;
import com.vmuller.github.desafiozappts.util.DeckCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class CardControllerTest {

    @InjectMocks
    CardController cardController;

    @Mock
    CardService cardServiceMock;

    @Mock
    DeckService deckServiceMock;


    @BeforeEach
    void setup(){
        PageImpl<Deck> deckPage = new PageImpl<>(List.of(DeckCreator.createDeck()));
        when(deckServiceMock.findAll(ArgumentMatchers.any()))
                .thenReturn(deckPage);
    }


    @Test
    @DisplayName("FindAll cards in alphabetic order")
    void findAll_AlphabeticOrder() {
        Page<Card> cardPageNameOrder = CardCreator.createPageCardsInAlphabeticOrder();
        when(cardServiceMock.findAllNameOrder(ArgumentMatchers.any()))
                .thenReturn(cardPageNameOrder);

        Page<Card> cardPage = this.cardController.getAllNameOrder(null).getBody();

        Assertions.assertThat(cardPage).isNotNull();
        Assertions.assertThat(cardPage.toList().get(0).getName()).isEqualTo("Ancestral Recall");
        Assertions.assertThat(cardPage.toList().get(1).getName()).isEqualTo("Black Lotus");
        Assertions.assertThat(cardPage.toList().get(2).getName()).isEqualTo("Time Walk");
        Assertions.assertThat(cardPage.toList().get(3).getName()).isEqualTo("Underground Sea");


    }

    @Test
    @DisplayName("FindAll cards in Quantity of the cards order")
    void findAll_QuantityOrder() {
        Page<Card> cardPageQuantityOrder = CardCreator.createPageCardsInQuantityOrder();
        when(cardServiceMock.findAllQuantityOrder(ArgumentMatchers.any()))
                .thenReturn(cardPageQuantityOrder);

        Page<Card> cardPage = this.cardController.getAllQuantityOrder(null).getBody();

        Assertions.assertThat(cardPage).isNotNull();
        Assertions.assertThat(cardPage.toList().get(0).getPrice()).isEqualTo(950005.0);
        Assertions.assertThat(cardPage.toList().get(1).getPrice()).isEqualTo(830005.0);
        Assertions.assertThat(cardPage.toList().get(2).getPrice()).isEqualTo(75000.9);
        Assertions.assertThat(cardPage.toList().get(3).getPrice()).isEqualTo(68000.0);
    }

    @Test
    @DisplayName("Save returns Card when successfully")
    void save_ReturnsCard_WhenSuccessfully() {
        when(cardServiceMock.save(ArgumentMatchers.any(Card.class)))
                .thenReturn(CardCreator.createCardImmutableId());

        Card cardToBeSaved = CardCreator.createCardImmutableId();
        ResponseEntity<Card> card = cardController.saveCard(cardToBeSaved);

        Assertions.assertThat(card).isNotNull();
        Assertions.assertThat(card.getBody().getId()).isEqualTo(cardToBeSaved.getId());

    }

    @Test
    @DisplayName("Update returns Card when successfully")
    void update_ReturnsCard_WhenSuccessfully() {
        when(cardServiceMock.save(ArgumentMatchers.any(Card.class)))
                .thenReturn(CardCreator.updateCardImmutableId());

        when(cardServiceMock.findById(ArgumentMatchers.any(UUID.class)))
                .thenReturn(Optional.of(CardCreator.updateCardImmutableId()));

        Card cardToBeSaved = CardCreator.updateCardImmutableId();

        Assertions.assertThatCode(() -> cardController.updateCard(cardToBeSaved.getId(),
                CardCreator.updateCardImmutableId()).getBody()).doesNotThrowAnyException();

        ResponseEntity<Object> card = cardController.updateCard(cardToBeSaved.getId(),
                CardCreator.updateCardImmutableId());
        Assertions.assertThat(card).isNotNull();
        Assertions.assertThat(card.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    @DisplayName("Delete removes Card when successfully")
    void delete_RemovesCard_WhenSuccessfully() {
        BDDMockito.doNothing().when(cardServiceMock).deleteByid(ArgumentMatchers.any(UUID.class));

        when(cardServiceMock.findById(ArgumentMatchers.any(UUID.class)))
                .thenReturn(Optional.of(CardCreator.createCardImmutableId()));

        Assertions.assertThatCode(() -> cardController.delete(CardCreator.createCardImmutableId()
                .getId()).getBody()).doesNotThrowAnyException();

        ResponseEntity<Object> card = cardController.delete(CardCreator.createCardImmutableId().getId());
        Assertions.assertThat(card).isNotNull();
        Assertions.assertThat(card.getStatusCode()).isEqualTo(HttpStatus.OK);

    }


}