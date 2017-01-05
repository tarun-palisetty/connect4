package com.moj.game.connect4.service;

import com.moj.game.connect4.common.DiscColor;
import com.moj.game.connect4.common.GameStatus;
import com.moj.game.connect4.exception.GameNotFoundException;
import com.moj.game.connect4.exception.InvalidGamePlayException;
import com.moj.game.connect4.exception.InvalidGameStatusException;
import com.moj.game.connect4.exception.NoPlayerSpaceException;
import com.moj.game.connect4.model.Game;
import com.moj.game.connect4.model.GameResponse;
import com.moj.game.connect4.model.Player;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by tarun on 05/01/2017.
 */

@Service
public class GameServiceImpl implements GameService {

    private Map<String, Game> gameMap = new ConcurrentHashMap<>();



    @Override
    public Game startNewGame(String userId, String color) {
        Player player = new Player();
        player.setUserId(userId);
        player.setDiscColor(DiscColor.valueOf(color));

        Game game = new Game();
        game.setId(UUID.randomUUID().toString());
        game.setPlayer1(player);
        //save
        gameMap.put(game.getId(), game);
        return game;
    }

    @Override
    public Game getGame(String gameId) {
        if (!gameMap.containsKey(gameId)){
           throw new GameNotFoundException("Game with Id: "+gameId+" not found.");
        }
        return gameMap.get(gameId);
    }

    @Override
    public Game joinGame(String gameId, String userId) {
        if(gameMap.containsKey(gameId)) {

            Game game = gameMap.get(gameId);

            if (game.getPlayer2() != null) {
                throw new NoPlayerSpaceException("Game does not have room for new player");
            }

            if (game.getPlayer1().getUserId().equals(userId)) {
                throw new NoPlayerSpaceException("Player with id: " + userId + " already exists.");
            }

            Player player = new Player();
            player.setUserId(userId);
            DiscColor player2DiscColor = game.getPlayer1().getDiscColor().equals(DiscColor.RED) ? DiscColor.YELLOW : DiscColor.RED;
            //player2DiscColor=DiscColor.YELLOW;
            player.setDiscColor(player2DiscColor);
            game.setPlayer2(player);
            game.setStatus(GameStatus.STARTED);

            return game;
        } else {
            throw new GameNotFoundException("Game with id:"+gameId+" is not found.");
        }

    }

    @Override
    public Game play(String gameId, String userId, int column) {
        if (!gameMap.containsKey(gameId)){
            throw  new GameNotFoundException("No game found with the gameId: "+gameId);
        }

        Game game = gameMap.get(gameId);

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
