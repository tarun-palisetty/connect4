package com.moj.game.connect4.exception;

/**
 * Created by tarun on 05/01/2017.
 */
public class GameException extends  RuntimeException{

    public GameException() {
        super();
    }

    public GameException(String message) {
        super(message);
    }

    public GameException(Throwable cause) {
        super(cause);
    }
}
