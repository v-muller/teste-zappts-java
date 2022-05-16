package com.vmuller.github.desafiozappts.services;

import com.vmuller.github.desafiozappts.dtos.CardDTO;
import com.vmuller.github.desafiozappts.entities.Card;
import com.vmuller.github.desafiozappts.repositories.CardRepository;
import com.vmuller.github.desafiozappts.services.Exceptions.ObjectNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.Transient;
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

    public Page<Card> findAll(Pageable pageable){
        return cardRepository.findAll(pageable);
    }

    public Page<Card> findAllValueOrder(Pageable pageable){
        return cardRepository.findAll(pageable);
    }

    public Card findById(UUID id){
        Optional<Card> obj = cardRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }

    @Transactional
    @Transient
    public Card save(Card card){
        return cardRepository.save(card);
    }

    @Transactional
    @Transient
    public List<Card> saveAll(List<Card> cards){
        return cardRepository.saveAll(cards);
    }
    @Transactional
    public void delete(UUID id){
        cardRepository.deleteById(id);
    }

    public Card update(Card card){
        Card newObj = findById(card.getId());

        updateData(newObj, card);
        return cardRepository.save(newObj);
    }

    private void updateData(Card obj, Card newObj){
        newObj.setFoil(obj.getFoil());
        newObj.setEdition(obj.getEdition());
        newObj.setLanguage(obj.getLanguage());
        newObj.setName(obj.getName());
    }
}
