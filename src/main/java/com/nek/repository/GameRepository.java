package com.nek.repository;

import com.nek.model.Game;
import com.nek.model.GameStatus;
import com.nek.model.GameType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface GameRepository extends CrudRepository<Game, Long> {

    List<Game> findByGameTypeAndGameStatus(GameType GameType, GameStatus GameStatus);

    List<Game> findByGameStatus(GameStatus gameStatus);
}
