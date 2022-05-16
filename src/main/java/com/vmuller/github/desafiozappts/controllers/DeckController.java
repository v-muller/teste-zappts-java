package com.vmuller.github.desafiozappts.controllers;

import com.vmuller.github.desafiozappts.dtos.DeckDTO;
import com.vmuller.github.desafiozappts.entities.Card;
import com.vmuller.github.desafiozappts.entities.Deck;
import com.vmuller.github.desafiozappts.services.CardService;
import com.vmuller.github.desafiozappts.services.DeckService;
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

    public DeckController(DeckService deckService, CardService cardService) {
        this.deckService = deckService;
        this.cardService = cardService;
    }

    @PostMapping
    public ResponseEntity<Object> saveDeck(Deck deck){
        return ResponseEntity.status(HttpStatus.CREATED).body(deckService.save(deck));
    }

    @GetMapping
    public ResponseEntity<Page<Deck>> findAll(@PageableDefault(sort = "cards", size = 12,
            direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(deckService.findAll(pageable));
    }


    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id")UUID id){
        Optional<Deck> deckOptional = deckService.findById(id);
        if(!deckOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Deck not found.");
        }
        for(Card c : deckOptional.get().getCards()){
            cardService.delete(c.getId());
        }
        deckService.delete(deckOptional.get().getId());
        return ResponseEntity.status(HttpStatus.OK).body("Player deleted succefully.");
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<Object> updateDeck(@PathVariable UUID id,
                                             @RequestBody @Valid DeckDTO deckDTO){
        Optional<Deck> deckOptional = deckService.findById(id);

        if(!deckOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Deck not found.");
        }

        var deck = new Deck();
        BeanUtils.copyProperties(deckDTO, deck);
        deck.setId(deckOptional.get().getId());
        return ResponseEntity.status(HttpStatus.OK).body(deckService.save(deck));
    }
}
