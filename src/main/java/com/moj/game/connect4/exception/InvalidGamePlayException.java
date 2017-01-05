package com.moj.game.connect4.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by tarun on 05/01/2017.
 */

@ResponseStatus(value = HttpStatus.CONFLICT)
public class InvalidGamePlayException extends GameException {

    public InvalidGamePlayException(String message){
        super(message);
    }
}
