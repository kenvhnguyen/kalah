package com.nek.controller;

import com.nek.model.GameDto;
import com.nek.model.Game;
import com.nek.service.GameService;
import com.nek.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    GameService gameService;

    @Autowired
    PlayerService playerService;

    @Autowired
    HttpSession httpSession;

    Logger logger = LoggerFactory.getLogger(GameController.class);

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Game createNewGame(@RequestBody GameDto gameDto) {

        Game game = gameService.createNewGame(playerService.getLoggedUser(), gameDto);
        httpSession.setAttribute("gameId", game.getId());

        logger.info("new game id: " + httpSession.getAttribute("gameId") + " stored in session");

        return game;
    }

    @RequestMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Game> getGamesToJoin() {
        return gameService.getGamesToJoin(playerService.getLoggedUser());
    }

    @RequestMapping(value = "/join", method = RequestMethod.POST)
    public Game joinGame(@RequestBody GameDto gameDto) {
        Game game = gameService.joinGame(playerService.getLoggedUser(), gameDto);
        return game;
    }


    @RequestMapping(value = "/player/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Game> getPlayerGames() {
        return gameService.getPlayerGames(playerService.getLoggedUser());
    }

    @RequestMapping(value = "/{id}")
    public Game getGameProperties(@PathVariable Long id) {

        httpSession.setAttribute("gameId", id);

        return gameService.getGame(id);
    }

}
