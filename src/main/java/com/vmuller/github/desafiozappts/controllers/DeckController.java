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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/decks")
public class DeckController {

    private final DeckService deckService;
    private final CardService cardService;

    private final PlayerService playerService;

    public DeckController(DeckService deckService, CardService cardService, PlayerService playerService) {
        this.deckService = deckService;
        this.cardService = cardService;
        this.playerService = playerService;
    }

    @PostMapping
    public ResponseEntity<Deck> saveDeck(@RequestBody @Valid Deck deck) {

        Deck obj = new Deck();
        BeanUtils.copyProperties(deck, obj);
        return ResponseEntity.status(HttpStatus.CREATED).body(deckService.save(obj));
    }

    @GetMapping
    public ResponseEntity<Page<Deck>> findAll(@ParameterObject Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(deckService.findAll(pageable));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Object> findById(@PathVariable(value = "id") UUID id) {
        Optional<Deck> deck = deckService.findById(id);
        if (!deck.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Deck not Found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(deckService.findById(id));
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") UUID id) {
        Optional<Deck> deckOptional = deckService.findById(id);
        if (!deckOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Deck not found.");
        }
        if (!deckOptional.get().getCards().isEmpty()) {
            for (Card c : deckOptional.get().getCards()) {
                cardService.deleteByid(c.getId());
            }
        }
        deckService.delete(deckOptional.get().getId());
        return ResponseEntity.status(HttpStatus.OK).body("Deck deleted succefully.");
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<Object> updateDeck(@PathVariable UUID id,
                                             @RequestBody @Valid Deck deck) {
        Optional<Deck> deckOptional = deckService.findById(id);

        if (!deckOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Deck not found.");
        }

        var obj = new Deck();
        BeanUtils.copyProperties(deck, obj);
        obj.setId(deckOptional.get().getId());
        return ResponseEntity.status(HttpStatus.OK).body(deckService.save(deck));
    }
}
