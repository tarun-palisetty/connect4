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
    public Game createGame(String userId, String color) {
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
        return gameMap.get(gameId);
    }

    @Override
    public void updateGame(Game updatedGame) {
        gameMap.replace(updatedGame.getId(), updatedGame);
    }


}
