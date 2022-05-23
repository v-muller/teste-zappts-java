package com.vmuller.github.desafiozappts.services;

import com.vmuller.github.desafiozappts.entities.Card;
import com.vmuller.github.desafiozappts.repositories.CardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CardService {

    private final CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public Page<Card> findAllQuantityOrder(Pageable pageable){

        return cardRepository.findAll(pageable);
    }

    public Page<Card> findAllNameOrder(@PageableDefault(page=0, size = 12, sort="name",
            direction = Sort.Direction.ASC )Pageable pageable){

        return cardRepository.findAll(pageable);
    }

    public Optional<Card> findById(UUID id){

        return cardRepository.findById(id);
    }

    @Transactional
    public Card save(Card card){
        return cardRepository.save(card);
    }

    @Transactional
    public List<Card> saveAll(List<Card> cards){
        return cardRepository.saveAll(cards);
    }
    @Transactional
    public void deleteByid(UUID id){
        cardRepository.deleteById(id);
    }

}
