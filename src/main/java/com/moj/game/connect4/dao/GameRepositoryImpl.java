package com.moj.game.connect4.dao;

import com.moj.game.connect4.common.DiscColor;
import com.moj.game.connect4.exception.GameNotFoundException;
import com.moj.game.connect4.model.Game;
import com.moj.game.connect4.model.Player;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by tarun on 06/01/2017.
 */

@Service
public class GameRepositoryImpl implements GameRepository {

    private Map<String, Game> gameMap = new ConcurrentHashMap<>();


    @Override
    public Map<String, Game> getMap() {
        return gameMap;
    }

    @Override
    public Game createNewGame(String userId, String color) {
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



}
