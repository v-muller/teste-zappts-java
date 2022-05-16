package com.vmuller.github.desafiozappts.controllers;

import com.vmuller.github.desafiozappts.dtos.DeckDTO;
import com.vmuller.github.desafiozappts.entities.Deck;
import com.vmuller.github.desafiozappts.services.DeckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/decks")
public class DeckController {

    private final DeckService deckService;

    public DeckController(DeckService deckService) {
        this.deckService = deckService;
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

}
