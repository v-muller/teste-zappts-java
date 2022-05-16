package com.vmuller.github.desafiozappts.controllers;

import com.vmuller.github.desafiozappts.entities.Card;
import com.vmuller.github.desafiozappts.entities.Deck;
import com.vmuller.github.desafiozappts.entities.Player;
import com.vmuller.github.desafiozappts.services.CardService;
import com.vmuller.github.desafiozappts.services.DeckService;
import com.vmuller.github.desafiozappts.services.PlayerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/players")
public class PlayerController {


    private final PlayerService playerService;
    private final DeckService deckService;
    private final CardService cardService;
    //private final PasswordEncoder encoder;

    public PlayerController(PlayerService playerService, DeckService deckService, CardService cardService) {
        this.playerService = playerService;
        this.deckService = deckService;
        this.cardService = cardService;
        //this.encoder = encoder;
        //, PasswordEncoder encoder - colocar no parametro
    }


    @Transactional
    @PostMapping
    public ResponseEntity<Object> savePlayer(@RequestBody @Valid Player player){
        //player.setPassword(encoder.encode(player.getPassword()));

        for(Deck c : player.getDecks()){
            cardService.saveAll(c.getCards());
        }
        deckService.saveAll(player.getDecks());
        return ResponseEntity.status(HttpStatus.CREATED).body(playerService.save(player));
    }

    @PostMapping(path = "/save")
    public ResponseEntity<Object> savePlayerOnly(@RequestBody  Player player){
        //player.setPassword(encoder.encode(player.getPassword()));

        return ResponseEntity.status(HttpStatus.CREATED).body(playerService.save(player));
    }

    @GetMapping("/names")
    public ResponseEntity<Page<Player>> getAll(@PageableDefault(page=0, size = 12, sort="name",
            direction = Sort.Direction.ASC ) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(playerService.findAll(pageable));
    }

    @DeleteMapping(path = ("{id}"))
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> deletePlayer(@PathVariable(value = "id") UUID id){
        Optional<Player> playerOptional = playerService.findById(id);
        if(!playerOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Player not found.");
        }
        for(Deck d : playerOptional.get().getDecks()){
            for(Card c : d.getCards()){
                cardService.delete(c.getId());
            }
            deckService.delete(d.getId());
        }
        playerService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Player deleted successfully.");
    }


}
