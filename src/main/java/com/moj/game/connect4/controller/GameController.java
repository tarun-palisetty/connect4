package com.moj.game.connect4.controller;

import com.moj.game.connect4.common.DiscColor;
import com.moj.game.connect4.common.GameStatus;
import com.moj.game.connect4.exception.GameNotFoundException;
import com.moj.game.connect4.exception.InvalidGamePlayException;
import com.moj.game.connect4.exception.InvalidGameStatusException;
import com.moj.game.connect4.exception.NoPlayerSpaceException;
import com.moj.game.connect4.model.Game;
import com.moj.game.connect4.model.GameResponse;
import com.moj.game.connect4.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * Created by tarun on 05/01/2017.
 */

@RestController
@RequestMapping(path="/connect4")
public class GameController {

    private GameService gameService;

    @Autowired
    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }

    @RequestMapping(value = "/games", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public GameResponse startNewGame(@RequestBody @Validated GamePlayDto dto){
        validateColor(dto.getColor());
        Game game = gameService.startNewGame(dto.getUserId(), dto.getColor());
        GameResponse gameResponse = new GameResponse(game);
        gameResponse.setMessage("Game is created successfully");
        return gameResponse;
    }

    @RequestMapping(value = "/games/{gameId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public GameResponse getGame(@PathVariable @NotNull String gameId) throws GameNotFoundException{
        Game game = gameService.getGame(gameId);

        GameResponse gameResponse = new GameResponse(game);
        gameResponse.setMessage("Game is retrieved");
        return gameResponse;
    }

    @RequestMapping(value = "/games/{gameId}", method = RequestMethod.PUT)
    //@ResponseStatus(HttpStatus.OK)
    public GameResponse joinGame(@PathVariable @NotNull String gameId, @RequestBody @Validated GamePlayDto dto) throws  GameNotFoundException, NoPlayerSpaceException {
        Game game = gameService.joinGame(gameId, dto.getUserId());
        GameResponse gameResponse = new GameResponse(game);
        gameResponse.setMessage("User: "+ dto.getUserId()+" joined the game: "+gameId+" with disc color: "+game.getPlayer2().getDiscColor());
        return gameResponse;
    }


    @RequestMapping(value = "/games/{gameId}/discs", method = RequestMethod.PUT)
    //@ResponseStatus(HttpStatus.OK)
    public GameResponse playGame(@PathVariable @NotNull String gameId, @RequestBody @Validated GamePlayDto dto) throws GameNotFoundException, InvalidGameStatusException, InvalidGamePlayException {
        if (dto.getColumn()<0 || dto.getColumn()>6){
            throw new IllegalArgumentException("Column value should be between 0 and 6");
        }

        Game game = gameService.play(gameId, dto.getUserId(), dto.getColumn());
        GameResponse gameResponse = new GameResponse(game);
        gameResponse.setMessage("User: "+ dto.getUserId()+" dropped the disc on column: "+ dto.getColumn()+" for the game: "+gameId);

        if (GameStatus.COMPLETED.equals(game.getStatus())){
            gameResponse.setMessage(gameResponse.getMessage()+" User: "+ dto.getUserId()+" won the game.");
        }

        return gameResponse;
    }

    @RequestMapping(value = "/games/{gameId}/outcome", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public GameResponse gameOutCome(@PathVariable @NotNull String gameId){
        Game game = gameService.getGame(gameId);

        GameResponse gameResponse = new GameResponse(game);
        if (GameStatus.COMPLETED.equals(game.getStatus())){
            gameResponse.setMessage(gameResponse.getMessage()+" User: "+game.getPlayer1()+" won the game.");
        }
        return  gameResponse;
    }

    private void validateColor(String color){
        try {
            DiscColor.valueOf(color);
        } catch (Exception e){
            throw new IllegalArgumentException("Valid values for disc colors are "+DiscColor.RED+" and "+DiscColor.YELLOW);
        }

    }
}
