package com.vmuller.github.desafiozappts.controllers;

import com.vmuller.github.desafiozappts.entities.Player;
import com.vmuller.github.desafiozappts.services.CardService;
import com.vmuller.github.desafiozappts.services.DeckService;
import com.vmuller.github.desafiozappts.services.PlayerService;
import com.vmuller.github.desafiozappts.util.DeckCreator;
import com.vmuller.github.desafiozappts.util.PlayerCreator;
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
class PlayerControllerTest {

    @InjectMocks
    PlayerController playerController;

    @Mock
    PlayerService playerServiceMock;

    @Mock
    DeckService deckServiceMock;

    @Mock
    CardService cardServiceMock;

    @Test
    @DisplayName("FindAll returns Page of Player when successfully")
    void findAll_ReturnsPageOfPlayer_WhenSuccessfully() {
        Page<Player> expectedPlayer = PlayerCreator.createPagePlayer();
        when(playerServiceMock.findAll(ArgumentMatchers.any()))
                .thenReturn(expectedPlayer);

        Page<Player> playerPage = this.playerController.findAll(null).getBody();

        Assertions.assertThat(playerPage).isNotNull();

        Assertions.assertThat(playerPage.toList()).isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(playerPage.toList().get(0)).isEqualTo(expectedPlayer.toList().get(0));
    }

    @Test
    @DisplayName("FindById returns Player when successfully")
    void findById_ReturnsPlayer_WhenSuccessfully() {
        Optional<Player> expectedPlayer = PlayerCreator.createOptionalPlayer();
        when(playerServiceMock.findById(ArgumentMatchers.any(UUID.class)))
                .thenReturn(expectedPlayer);

        ResponseEntity<Object> byId = this.playerController.findById(UUID.fromString("35c70ae2-3db6-41c3-afe7-43d2c4fbb488"));

        Assertions.assertThat(byId).isNotNull();

        Assertions.assertThat(byId.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("Save returns Player when successfully")
    void save_ReturnsPlayer_WhenSuccessfully() {
        when(playerServiceMock.save(ArgumentMatchers.any(Player.class)))
                .thenReturn(PlayerCreator.createPlayer());

        Player playerToBeSaved = PlayerCreator.createPlayer();
        ResponseEntity<Player> player = playerController.savePlayer(playerToBeSaved);

        Assertions.assertThat(player).isNotNull();
        Assertions.assertThat(player.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @DisplayName("Update returns Player when successfully")
    void update_ReturnsPlayer_WhenSuccessfully() {
        when(playerServiceMock.save(ArgumentMatchers.any(Player.class)))
                .thenReturn(PlayerCreator.createUpdatePlayer());

        when(playerServiceMock.findById(ArgumentMatchers.any(UUID.class)))
                .thenReturn(Optional.of(PlayerCreator.createPlayer()));

        Player playerToBeSaved = PlayerCreator.createPlayer();

        Assertions.assertThatCode(() -> playerController.updatePlayer(playerToBeSaved.getId(),
                PlayerCreator.createUpdatePlayer()).getBody()).doesNotThrowAnyException();

        ResponseEntity<Object> player = playerController.updatePlayer(playerToBeSaved.getId(),
                PlayerCreator.createUpdatePlayer());
        Assertions.assertThat(player).isNotNull();
        Assertions.assertThat(player.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("Delete removes Player when successfully")
    void delete_RemovesPlayer_WhenSuccessfully() {
        BDDMockito.doNothing().when(playerServiceMock).delete(ArgumentMatchers.any(UUID.class));

        when(playerServiceMock.findById(ArgumentMatchers.any(UUID.class)))
                .thenReturn(Optional.of(PlayerCreator.createPlayer()));

        Assertions.assertThatCode(() -> playerController.deletePlayer(DeckCreator.createDeck()
                .getId()).getBody()).doesNotThrowAnyException();

        ResponseEntity<Object> player = playerController.deletePlayer(PlayerCreator.createPlayer().getId());
        Assertions.assertThat(player).isNotNull();
        Assertions.assertThat(player.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}