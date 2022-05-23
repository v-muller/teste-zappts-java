package com.vmuller.github.desafiozappts.services;

import com.vmuller.github.desafiozappts.entities.Player;
import com.vmuller.github.desafiozappts.repositories.PlayerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class PlayerService implements UserDetailsService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Page<Player> findAll(Pageable pageable){
       return playerRepository.findAll(pageable);
    }

    public Optional<Player> findById(UUID id){
        return playerRepository.findById(id);
    }

    @Transactional
    public Player save(Player player){
        return playerRepository.save(player);
    }

    @Transactional
    public void delete(UUID id){
        playerRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return Optional.ofNullable(playerRepository.findByEmail(email))
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
    }
}
