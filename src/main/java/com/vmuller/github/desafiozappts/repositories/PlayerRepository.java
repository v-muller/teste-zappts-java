package com.vmuller.github.desafiozappts.repositories;

import com.vmuller.github.desafiozappts.entities.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PlayerRepository extends JpaRepository<Player, UUID> {

    Player findByEmail(String email);
}
