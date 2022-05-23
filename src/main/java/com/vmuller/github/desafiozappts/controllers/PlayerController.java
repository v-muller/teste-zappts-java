package com.vmuller.github.desafiozappts.controllers;

import com.vmuller.github.desafiozappts.entities.Card;
import com.vmuller.github.desafiozappts.entities.Deck;
import com.vmuller.github.desafiozappts.entities.Player;
import com.vmuller.github.desafiozappts.services.CardService;
import com.vmuller.github.desafiozappts.services.DeckService;
import com.vmuller.github.desafiozappts.services.PlayerService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
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
    private final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    public PlayerController(PlayerService playerService, DeckService deckService, CardService cardService) {
        this.playerService = playerService;
        this.deckService = deckService;
        this.cardService = cardService;
    }

    @PostMapping
    public ResponseEntity<Player> savePlayer(@RequestBody @Valid Player player){


        var obj = new Player();
        player.setId(obj.getId());
        BeanUtils.copyProperties(player, obj);
        obj.setPassword(passwordEncoder.encode(player.getPassword()));
        return ResponseEntity.status(HttpStatus.CREATED).body(playerService.save(obj));
    }

    @GetMapping("/names")
    public ResponseEntity<Page<Player>> findAll(@ParameterObject Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(playerService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable(value = "id") UUID id){
        Optional<Player> playerOptional = playerService.findById(id);
        if(!playerOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Player not found.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(playerService.findById(id));
    }

    @Transactional
    @DeleteMapping(path = ("/{id}"))
    public ResponseEntity<Object> deletePlayer(@PathVariable(value = "id") UUID id){
        Optional<Player> playerOptional = playerService.findById(id);
        if(!playerOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Player not found.");
        }

        if(!playerOptional.get().getDecks().isEmpty())
            for(Deck d : playerOptional.get().getDecks()){
                if(!d.getCards().isEmpty())
                    for(Card c : d.getCards()){
                        cardService.deleteByid(c.getId());
                    }
                deckService.delete(d.getId());
            }
        playerService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Player deleted successfully.");
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Object> updatePlayer(@PathVariable(value = "id") UUID id,
                                               @RequestBody @Valid Player player){
        Optional<Player> playerOptional = playerService.findById(id);

        if(!playerOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Player not found.");
        }

        var obj = new Player();
        BeanUtils.copyProperties(player, obj);
        obj.setId(playerOptional.get().getId());
        return ResponseEntity.status(HttpStatus.OK).body(playerService.save(obj));
    }
}
