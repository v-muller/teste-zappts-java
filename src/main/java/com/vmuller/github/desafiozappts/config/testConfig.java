package com.vmuller.github.desafiozappts.config;

import com.vmuller.github.desafiozappts.entities.Card;
import com.vmuller.github.desafiozappts.entities.Deck;
import com.vmuller.github.desafiozappts.entities.Player;
import com.vmuller.github.desafiozappts.repositories.CardRepository;
import com.vmuller.github.desafiozappts.repositories.DeckRepository;
import com.vmuller.github.desafiozappts.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
@Profile("test")
public class testConfig implements CommandLineRunner  {
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    DeckRepository deckRepository;
    @Autowired
    CardRepository cardRepository;

    @Override
    public void run(String... args) throws Exception {
        Player p1 = new Player("Ana luiza", "ana@gmail.com", "12345");
        Player p2 = new Player("Craudio Roberto", "craudio@gmail.com", "101112");
        Player p3 = new Player("Brenda", "brenda@gmail.com", "202122");
        Player p4 = new Player("Bento", "bento@gmail.com", "202122");

        playerRepository.saveAll(Arrays.asList(p1, p2, p3, p4));

        Deck d1 = new Deck(p1);
        Deck d2 = new Deck(p2);
        Deck d3 = new Deck(p3);
        Deck d4 = new Deck(p4);

        p1.getDecks().add(d1);
        p2.getDecks().add(d2);
        p3.getDecks().add(d3);
        p4.getDecks().add(d4);


        Card c1 = new Card("Black Lotus", "1993", "portuguese", false, 830005.0, 1, d1);
        Card c2 = new Card("Time Walk", "1998", "Portugues", true, 68000.0, 2, d1);
        Card c3 = new Card("Ancestral Recall", "2001", "Portugues", true, 75000.9, 4, d2);
        Card c4 = new Card("Underground Sea", "1997", "Portugues", false, 95500.0, 5, d2);
        Card c5= new Card("Mox Jet", "1999", "Portugues", true, 69000.0, 2, d3);
        Card c6 = new Card("Timetwister", "2002", "Portugues", true, 68000.0, 8, d3);
        Card c7 = new Card("Mox Sapphire", "2000", "Portugues", true, 60000.0, 1, d4);
        Card c8 = new Card("Intuition", "2004", "Portugues", true, 59500.0, 9, d4);
        Card c9 = new Card("Mox Ruby", "2003", "Portugues", false, 57500.0, 4, d3);
        Card c10 = new Card("Mox Pearl", "2006", "Portugues", false, 55000.0, 6, d1);

        d1.getCards().addAll(Arrays.asList(c1, c2, c10));
        d2.getCards().addAll(Arrays.asList(c3, c4));
        d3.getCards().addAll(Arrays.asList(c5, c6, c9));
        d4.getCards().addAll(Arrays.asList(c7, c8));

        deckRepository.saveAll(Arrays.asList(d1, d2, d3, d4));
        cardRepository.saveAll(Arrays.asList(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10));

    }
}
