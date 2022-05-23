package com.vmuller.github.desafiozappts.repositories;

import com.vmuller.github.desafiozappts.entities.Player;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.UUID;

@DataJpaTest
@DisplayName("Tests for PlayerRepository")
class PlayerRepositoryTest {
    @Autowired
    PlayerRepository playerRepository;

    @Test
    @DisplayName("Save persists player when successfully")
    void save_PersistPlayer_WhenSuccessfully() {
        Player playerToBeSaved = createPlayer();
        Player playerSaved = this.playerRepository.save(playerToBeSaved);

        Assertions.assertThat(playerSaved).isNotNull();
        Assertions.assertThat(playerSaved.getId()).isNotNull();
        Assertions.assertThat(playerSaved.getName()).isEqualTo(playerToBeSaved.getName());
    }

    @Test
    @DisplayName("Save updates Player when successfully")
    void save_UpdatePlayer_WhenSuccessfully() {
        Player playerToBeSaved = createPlayer();
        Player playerSaved = this.playerRepository.save(playerToBeSaved);

        playerToBeSaved.setName("Craudio");
        playerToBeSaved.setPassword("456");
        playerToBeSaved.setEmail("Daiana@gmail.com");

        Player playerUpdated = this.playerRepository.save(playerToBeSaved);


        Assertions.assertThat(playerUpdated.getName()).isEqualTo(playerToBeSaved.getName());
        Assertions.assertThat(playerUpdated.getPassword()).isEqualTo(playerToBeSaved.getPassword());
        Assertions.assertThat(playerUpdated.getEmail()).isEqualTo(playerToBeSaved.getEmail());
    }

    @Test
    @DisplayName("Delete remove Player when successfully")
    void delete_RemovesPlayer_WhenSuccessfully() {
        Player playerToBeSaved = createPlayer();
        Player playerSaved = this.playerRepository.save(playerToBeSaved);

        this.playerRepository.deleteById(playerSaved.getId());

        Optional<Player> playerOptional = this.playerRepository.findById(playerSaved.getId());
        Assertions.assertThat(playerOptional.isPresent()).isFalse();
    }

    @Test
    @DisplayName("Find by id returns the same Player id saved ")
    void findById_ReturnsTheSameObjectIdSaved() {
        Player playerToBeSaved = createPlayer();
        Player playerSaved = this.playerRepository.save(playerToBeSaved);

        Optional<Player> playerOptional = this.playerRepository.findById(playerSaved.getId());

        Assertions.assertThat(playerOptional.get()).isEqualTo(playerSaved);
    }

    @Test
    @DisplayName("Find by id returns empty because the id is wrong")
    void findById_ReturnsEmptyObject() {
        Player playerToBeSaved = createPlayer();
        Player playerSaved = this.playerRepository.save(playerToBeSaved);

        Optional<Player> playerOptional = this.playerRepository.findById(UUID.randomUUID());

        Assertions.assertThat(playerOptional.isPresent()).isFalse();
    }

    @Test
    @DisplayName("Find by email returns the same Player id saved")
    void findByEmail_ReturnsEmptyObject() {
        Player playerToBeSaved = createPlayer();
        Player playerSaved = this.playerRepository.save(playerToBeSaved);

        Player wantedPlayer = this.playerRepository.findByEmail(playerToBeSaved.getEmail());

        Assertions.assertThat(wantedPlayer).isEqualTo(playerSaved);
    }

    private Player createPlayer(){
        return new Player("Caroline Souza ", "carol@gmail.com", "12345");
    }
}