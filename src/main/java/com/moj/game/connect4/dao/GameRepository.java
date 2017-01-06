package com.moj.game.connect4.dao;

import com.moj.game.connect4.model.Game;

import java.util.Map;

/**
 * Created by tarun on 06/01/2017.
 */
public interface GameRepository {

    Game createNewGame(String userId, String color);

    Game getGame(String gameId);
}
