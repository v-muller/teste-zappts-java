package com.vmuller.github.desafiozappts.controllers;

import com.vmuller.github.desafiozappts.entities.Card;
import com.vmuller.github.desafiozappts.entities.Deck;
import com.vmuller.github.desafiozappts.services.CardService;
import com.vmuller.github.desafiozappts.services.DeckService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping(path = "/cards")
public class CardController {
    private final CardService cardService;

    private final DeckService deckService;

    public CardController(CardService cardService, DeckService deckService) {
        this.cardService = cardService;
        this.deckService = deckService;
    }

    @GetMapping("/names")
    @Operation(summary = "List all cards paginated", description = "The cards are in the alphabetic order.")
    public ResponseEntity<Page<Card>> getAllNameOrder(@ParameterObject @PageableDefault(page=0, size = 12, sort="name",
            direction = Sort.Direction.ASC ) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(cardService.findAllNameOrder(pageable));
    }

    @GetMapping("/quantities")
    @Operation(summary = "List all cards paginated", description = "The cards are in the price order.")
    public ResponseEntity<Page<Card>> getAllQuantityOrder(@ParameterObject @PageableDefault(page=0, size = 12, sort="quantity",
            direction = Sort.Direction.ASC ) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(cardService.findAllQuantityOrder(pageable));
    }

    @PostMapping
    public ResponseEntity<Card> saveCard(Card card){

        return ResponseEntity.status(HttpStatus.CREATED).body(cardService.save(card));
    }


    @DeleteMapping(path = "{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") UUID id){
        Optional<Card> cardOptional = cardService.findById(id);
        if(!cardOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Card not found");
        }

        cardService.deleteByid(id);
        return ResponseEntity.status(HttpStatus.OK).body("Card deleted successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCard(@PathVariable(value = "id") UUID id,
                                                    @RequestBody @Valid Card card){
        Optional<Card> cardOptional = cardService.findById(id);
        if (!cardOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Card not found.");
        }
        var obj = new Card();
        BeanUtils.copyProperties(card, obj);
        obj.setId(cardOptional.get().getId());
        return ResponseEntity.status(HttpStatus.OK).body(cardService.save(obj));
    }
}
