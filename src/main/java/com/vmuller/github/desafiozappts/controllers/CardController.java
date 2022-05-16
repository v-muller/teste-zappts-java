package com.vmuller.github.desafiozappts.controllers;

import com.vmuller.github.desafiozappts.entities.Card;
import com.vmuller.github.desafiozappts.entities.Player;
import com.vmuller.github.desafiozappts.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/cards")
public class CardController {
    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/names")
    public ResponseEntity<Page<Card>> getAll(@PageableDefault(page=0, size = 12, sort="name",
            direction = Sort.Direction.ASC ) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(cardService.findAll(pageable));
    }
}
