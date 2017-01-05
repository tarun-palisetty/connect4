package com.moj.game.connect4.service;

import com.moj.game.connect4.model.Game;

/**
 * Created by tarun on 05/01/2017.
 */
public interface GameService {

    Game startNewGame(String userId, String color);

    Game getGame(String gameId);

    Game joinGame(String gameId, String userId);

    Game play(String gameId, String userId, int column);

}
