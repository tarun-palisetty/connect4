package com.moj.game.connect4.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by tarun on 05/01/2017.
 */

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NoPlayerSpaceException extends GameException {


    public NoPlayerSpaceException(String message){
        super(message);
    }
}
