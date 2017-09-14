package com.nek.controller;

import com.nek.model.PlayerDto;
import com.nek.model.Player;
import com.nek.service.PlayerService;
import com.nek.service.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/player")
public class PlayerController {

    @Autowired
    PlayerService playerService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Player createAccount(@RequestBody PlayerDto newPlayerDto) {
        Player newPlayer = playerService.createNewPlayer(newPlayerDto);
        return newPlayer;
    }

    @RequestMapping(value = "/players", method = RequestMethod.GET)
    public void getPlayers() {
        playerService.listPlayers();
    }

    @RequestMapping(value = "/logged", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Player> getLoggedPlayer() {
        return new Response<>(playerService.getLoggedUser(), Response.Status.CREATED);
    }


}


