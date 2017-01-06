package com.moj.game.connect4.service;

import com.moj.game.connect4.common.DiscColor;
import com.moj.game.connect4.common.GameStatus;
import com.moj.game.connect4.dao.GameRepository;
import com.moj.game.connect4.exception.GameNotFoundException;
import com.moj.game.connect4.exception.InvalidGamePlayException;
import com.moj.game.connect4.exception.InvalidGameStatusException;
import com.moj.game.connect4.exception.NoPlayerSpaceException;
import com.moj.game.connect4.model.Game;
import com.moj.game.connect4.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

/**
 * Created by tarun on 05/01/2017.
 */

@Service
public class GameServiceImpl implements GameService {

    private GameRepository gameRepository;

    @Autowired
    public void setGameRepository(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public Game startNewGame(String userId, String color) {
        return gameRepository.createNewGame(userId, color);
    }

    @Override
    public Game getGame(String gameId) {
        return gameRepository.getGame(gameId);
    }

    @Override
    public Game joinGame(String gameId, String userId) {

        Game game = getGame(gameId);

        if (game.getPlayer2() != null) {
            throw new NoPlayerSpaceException("Game does not have room for new player");
        }

        if (game.getPlayer1().getUserId().equals(userId)) {
            throw new NoPlayerSpaceException("Player with id: " + userId + " already exists.");
        }

        Player player = new Player();
        player.setUserId(userId);
        DiscColor player2DiscColor = game.getPlayer1().getDiscColor().equals(DiscColor.RED) ? DiscColor.YELLOW : DiscColor.RED;
        player.setDiscColor(player2DiscColor);
        game.setPlayer2(player);
        game.setStatus(GameStatus.STARTED);

        return game;

    }

    @Override
    public Game play(String gameId, String userId, int column) {
        Game game = getGame(gameId);

        if (GameStatus.CREATED.equals(game.getStatus())){
            throw new InvalidGameStatusException("Game does not have enough players.");
        }

        if ( !(game.getPlayer1().getUserId().equals(userId) || game.getPlayer2().getUserId().equals(userId))){
            throw new GameNotFoundException("No game found with the userId: "+userId);
        }

        if (GameStatus.COMPLETED.equals(game.getStatus())){
            throw new InvalidGameStatusException("Game is already completed.");
        }

        Player player = game.getPlayer1().getUserId().equals(userId)?game.getPlayer1():game.getPlayer2();
        if (player.getDiscColor().equals(game.getLastPlayedDisc())){
            throw new InvalidGamePlayException("Now the drop disc turn is for you opponent Player");
        }

        game.dropDisc(player.getDiscColor(), column);
        if (game.calculateWinner()){
            game.setStatus(GameStatus.COMPLETED);
        }

        return game;
    }
}
