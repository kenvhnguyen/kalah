package com.nek.service;

import com.nek.model.GameDto;
import com.nek.model.Game;
import com.nek.model.Player;
import com.nek.model.GameStatus;
import com.nek.model.GameType;
import com.nek.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class GameService {

    private final GameRepository gameRepository;

    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Game createNewGame(Player player, GameDto gameDto) {
        Game game = new Game();
        game.setFirstPlayer(player);
        game.setGameType(gameDto.getGameType());
        game.setGameStatus(gameDto.getGameType() == GameType.COMPUTER ? GameStatus.IN_PROGRESS :
                GameStatus.WAIT_FOR_TURN);

        game.setCreated(new Date());
        gameRepository.save(game);

        return game;
    }

    public Game updateGameStatus(Game game, GameStatus gameStatus) {
        Game g = getGame(game.getId());
        g.setGameStatus(gameStatus);

        return g;
    }

    public List<Game> getGamesToJoin(Player player) {
        return gameRepository.findByGameTypeAndGameStatus(GameType.COMPETITION,
                GameStatus.WAIT_FOR_TURN).stream().filter(game -> game.getFirstPlayer() != player).collect(Collectors.toList());

    }

    public Game joinGame(Player player, GameDto gameDto) {
        Game game = getGame((long) gameDto.getId());
        game.setSecondPlayer(player);
        gameRepository.save(game);

        updateGameStatus(game, GameStatus.IN_PROGRESS);

        return game;

    }

    public List<Game> getPlayerGames(Player player) {
        return gameRepository.findByGameStatus(
                GameStatus.IN_PROGRESS).stream().filter(game -> game.getFirstPlayer() == player).collect(Collectors.toList());
    }


    public Game getGame(Long id) {
        return gameRepository.findOne(id);
    }
}
