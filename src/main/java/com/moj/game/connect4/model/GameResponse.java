package com.moj.game.connect4.model;

/**
 * Created by tarun on 05/01/2017.
 */
public class GameResponse {

    private Game game;
    private String message;

    public GameResponse(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
