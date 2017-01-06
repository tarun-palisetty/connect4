package com.moj.game.connect4.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by tarun on 05/01/2017.
 */

@ResponseStatus(HttpStatus.NOT_FOUND)
public class GameNotFoundException extends GameException {

    public GameNotFoundException(String message){
        super(message);
    }
}
