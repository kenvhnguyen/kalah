package com.nek.service;

import com.nek.model.PlayerDto;
import com.nek.model.Player;
import com.nek.repository.PlayerRepository;
import com.nek.security.ContextUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Player createNewPlayer(PlayerDto playerDto) {
        Player newPlayer = new Player();
        newPlayer.setUserName(playerDto.getUserName());
        newPlayer.setPassword(passwordEncoder.encode(playerDto.getPassword()));
        newPlayer.setEmail(playerDto.getEmail());
        playerRepository.save(newPlayer);
        return newPlayer;
    }

    public Player getLoggedUser() {
        ContextUser principal = (ContextUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return playerRepository.findOneByUserName(principal.getPlayer().getUserName());
    }

    public List<Player> listPlayers() {
        List<Player> players = (List<Player>) playerRepository.findAll();
        return players;
    }

}

