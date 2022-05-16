package com.vmuller.github.desafiozappts.services;

import com.vmuller.github.desafiozappts.entities.Card;
import com.vmuller.github.desafiozappts.entities.Deck;
import com.vmuller.github.desafiozappts.repositories.CardRepository;
import com.vmuller.github.desafiozappts.repositories.DeckRepository;
import com.vmuller.github.desafiozappts.services.Exceptions.ObjectNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DeckService {
    private final DeckRepository deckRepository;
    private final CardRepository cardRepository;


    public DeckService(DeckRepository deckRepository, CardRepository cardRepository) {
        this.deckRepository = deckRepository;
        this.cardRepository = cardRepository;
    }

    public Page<Deck> findAll(Pageable pageable){
        return deckRepository.findAll(pageable);
    }

    public Optional<Deck> findById(UUID id){

        return deckRepository.findById(id);
    }

    @Transactional
    public Deck save(Deck deck){
        for(Card c : deck.getCards()){
            deck.setNumberOfCards(deck.getNumberOfCards() + c.getQuantity());
        }
        cardRepository.saveAll(deck.getCards());

        return deckRepository.save(deck);
    }

    @Transactional
    public List<Deck> saveAll(List<Deck> decks){
        addNumberOfCardsOnDeck(decks);

        return deckRepository.saveAll(decks);
    }

    private void addNumberOfCardsOnDeck(List<Deck> decks){
        for(Deck d : decks){
            for(Card c : d.getCards()){
                d.setNumberOfCards(d.getNumberOfCards() + c.getQuantity());
            }
        }
    }

    @Transactional
    public void delete(UUID id){
        deckRepository.deleteById(id);
    }

    public Deck update(Deck deck){

        return deckRepository.save(deck);
    }

}
