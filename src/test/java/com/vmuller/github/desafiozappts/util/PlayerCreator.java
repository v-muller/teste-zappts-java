package com.vmuller.github.desafiozappts.util;

import com.vmuller.github.desafiozappts.entities.Deck;
import com.vmuller.github.desafiozappts.entities.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PlayerCreator {

    public static Player createPlayer(){
        Player player = new Player("Joao miguel", "miguel@gmail.com", "1234");
        player.setId(UUID.fromString("35c70ae2-3db6-41c3-afe7-43d2c4fbb488"));
        return player;
    }

    public static Player createUpdatePlayer(){
        Player player = new Player("Craudio José", "craudio@gmail.com", "1234");
        player.setId(UUID.fromString("35c70ae2-3db6-41c3-afe7-43d2c4fbb488"));
        return player;
    }

    public static Optional<Player> createOptionalPlayer(){
        Player player = new Player("Joao miguel", "miguel@gmail.com", "1234");
        player.setId(UUID.fromString("35c70ae2-3db6-41c3-afe7-43d2c4fbb488"));
        return Optional.of(player);
    }

    public static Page<Player> createPagePlayer(){
        Player player = createPlayer();
        List<Player> players = Arrays.asList(player);
        Page<Player> playerPage = new PageImpl(players, Pageable.unpaged(), players.size());
        return playerPage;
    }
}
