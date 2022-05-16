package com.vmuller.github.desafiozappts.services;

import com.vmuller.github.desafiozappts.data.PlayerDetail;
import com.vmuller.github.desafiozappts.entities.Player;
import com.vmuller.github.desafiozappts.repositories.PlayerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PlayerDetailService implements UserDetailsService {
    private final PlayerRepository playerRepository;

    public PlayerDetailService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Player> playerOptional = playerRepository.findByEmail(email);

        if(playerOptional.isEmpty()){
            throw new UsernameNotFoundException("User [" + email + "] not found.");
        }

        return new PlayerDetail(playerOptional);
    }
}
