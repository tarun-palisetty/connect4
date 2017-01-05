package com.moj.game.connect4.controller;

import com.moj.game.connect4.common.DiscColor;
import com.moj.game.connect4.common.GameStatus;
import com.moj.game.connect4.dto.GamePlayDto;
import com.moj.game.connect4.dto.NewGameDto;
import com.moj.game.connect4.dto.PlayerDto;
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
    public GameResponse startNewGame(@RequestBody @Validated NewGameDto player){
        validateColor(player.getColor());
        Game game = gameService.startNewGame(player.getUserId(), player.getColor());
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
    public GameResponse joinGame(@PathVariable @NotNull String gameId, @RequestBody @Validated PlayerDto player) throws  GameNotFoundException, NoPlayerSpaceException {
        Game game = gameService.joinGame(gameId, player.getUserId());
        GameResponse gameResponse = new GameResponse(game);
        gameResponse.setMessage("User: "+ player.getUserId()+" joined the game: "+gameId+" with disc color: "+game.getPlayer2().getDiscColor());
        return gameResponse;
    }


    @RequestMapping(value = "/games/{gameId}/discs", method = RequestMethod.PUT)
    //@ResponseStatus(HttpStatus.OK)
    public GameResponse playGame(@PathVariable @NotNull String gameId, @RequestBody @Validated GamePlayDto dto) throws GameNotFoundException, InvalidGameStatusException, InvalidGamePlayException {
        if (dto.getColumn()<1 || dto.getColumn()>7){
            throw new IllegalArgumentException("Column value should be between 1 and 7");
        }

        Game game = gameService.play(gameId, dto.getUserId(), dto.getColumn()-1);
        GameResponse gameResponse = new GameResponse(game);
        gameResponse.setMessage("User: "+ dto.getUserId()+" dropped the disc on column: "+ dto.getColumn()+" for the game: "+gameId);

        if (GameStatus.COMPLETED.equals(game.getStatus())){
            gameResponse.setMessage(gameResponse.getMessage()+" User: "+ dto.getUserId()+" won the game.");
        }

        return gameResponse;
    }

    @RequestMapping(value = "/games/{gameId}/outcome", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public GameResponse gameOutCome(@PathVariable @NotNull String gameId) throws GameNotFoundException{
        Game game = gameService.getGame(gameId);

        GameResponse gameResponse = new GameResponse(game);
        if (GameStatus.COMPLETED.equals(game.getStatus())){
            gameResponse.setMessage(gameResponse.getMessage()+" Game: "+gameId+" won the game.");
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
