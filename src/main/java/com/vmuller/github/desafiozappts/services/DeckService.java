package com.vmuller.github.desafiozappts.services;

import com.vmuller.github.desafiozappts.entities.Deck;
import com.vmuller.github.desafiozappts.repositories.CardRepository;
import com.vmuller.github.desafiozappts.repositories.ListCardRepository;
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
    private final ListCardRepository listCardRepository;
    private final CardRepository cardRepository;


    public DeckService(ListCardRepository listCardRepository, CardRepository cardRepository) {
        this.listCardRepository = listCardRepository;
        this.cardRepository = cardRepository;
    }

    public Page<Deck> findAll(Pageable pageable){
        return listCardRepository.findAll(pageable);
    }

    public Deck findById(UUID id){
        Optional<Deck> obj = listCardRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }

    @Transactional
    public Deck save(Deck deck){
        cardRepository.saveAll(deck.getCards());

        return listCardRepository.save(deck);
    }

    @Transactional
    public List<Deck> saveAll(List<Deck> cards){


        return listCardRepository.saveAll(cards);
    }

    @Transactional
    public void delete(UUID id){
        listCardRepository.deleteById(id);
    }

    public Deck update(Deck cards){
        Deck newObj = findById(cards.getId());

        return listCardRepository.save(newObj);
    }

}
