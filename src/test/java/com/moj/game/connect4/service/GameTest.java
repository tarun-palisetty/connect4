package com.moj.game.connect4.service;

import com.moj.game.connect4.common.DiscColor;
import com.moj.game.connect4.exception.InvalidGamePlayException;
import com.moj.game.connect4.model.Game;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by tarun on 05/01/2017.
 */


public class GameTest {

    @Test
    public void testDropDiscToFixedLocation() throws  Exception{
        Game game = new Game();

        game.dropDisc(DiscColor.RED, 0);
        assertEquals(game.getBoard()[5][0], DiscColor.RED);
    }

    @Test
    public void testDropMultipleDiscsToFixedLocation() throws Exception{
        Game game = new Game();

        game.dropDisc(DiscColor.RED, 0);
        game.dropDisc(DiscColor.RED, 0);
        assertEquals(game.getBoard()[5][0], DiscColor.RED);
        assertEquals(game.getBoard()[4][0], DiscColor.RED);
    }

    @Test(expected = InvalidGamePlayException.class)
    public void testRowOverFlowShouldThrowException() throws Exception{
        Game game = new Game();

        game.dropDisc(DiscColor.RED, 0);
        game.dropDisc(DiscColor.RED, 0);
        game.dropDisc(DiscColor.RED, 0);
        game.dropDisc(DiscColor.RED, 0);
        game.dropDisc(DiscColor.RED, 0);
        game.dropDisc(DiscColor.RED, 0);
        game.dropDisc(DiscColor.RED, 0);
    }

    @Test
    public void testCalculateWinnerInRowReturnTrue() throws Exception{
        Game game = new Game();

        game.dropDisc(DiscColor.RED, 0);
        game.dropDisc(DiscColor.RED, 1);
        game.dropDisc(DiscColor.RED, 3);
        game.dropDisc(DiscColor.RED, 2);
        assertTrue(game.calculateWinner());
    }

    @Test
    public void testCalculateWinnerInRowReturnsFalse() throws Exception{
        Game game = new Game();

        game.dropDisc(DiscColor.RED, 0);
        game.dropDisc(DiscColor.RED, 1);
        game.dropDisc(DiscColor.RED, 4);
        game.dropDisc(DiscColor.RED, 3);
        assertFalse(game.calculateWinner());
    }


    @Test
    public void testCalculateWinnerInRowReturnsFalseWithOppositeColor() throws Exception{
        Game game = new Game();

        game.dropDisc(DiscColor.RED, 0);
        game.dropDisc(DiscColor.RED, 1);
        game.dropDisc(DiscColor.YELLOW, 2);
        game.dropDisc(DiscColor.RED, 4);
        game.dropDisc(DiscColor.RED, 3);
        assertFalse(game.calculateWinner());
    }

    @Test
    public void testCalculateWinnerInColumnReturnsTrue() throws Exception{
        Game game = new Game();

        game.dropDisc(DiscColor.RED, 0);
        game.dropDisc(DiscColor.RED, 0);
        game.dropDisc(DiscColor.RED, 0);
        game.dropDisc(DiscColor.RED, 0);
        assertTrue(game.calculateWinner());
    }

    @Test
    public void testCalculateWinnerInColumnReturnsFalse() throws Exception{
        Game game = new Game();

        game.dropDisc(DiscColor.RED, 0);
        game.dropDisc(DiscColor.RED, 0);
        game.dropDisc(DiscColor.RED, 0);
        game.dropDisc(DiscColor.YELLOW, 0);
        assertFalse(game.calculateWinner());
    }


    @Test
    public void testCalculateWinnerInFowardDiagonalShouldReturnTrue() throws Exception{
        Game game = new Game();

        game.dropDisc(DiscColor.RED, 3);
        game.dropDisc(DiscColor.YELLOW, 3);
        game.dropDisc(DiscColor.YELLOW, 3);
        game.dropDisc(DiscColor.YELLOW, 3);

        game.dropDisc(DiscColor.YELLOW, 2);
        game.dropDisc(DiscColor.RED, 2);
        game.dropDisc(DiscColor.YELLOW, 2);
        game.dropDisc(DiscColor.YELLOW, 2);

        game.dropDisc(DiscColor.YELLOW, 1);
        game.dropDisc(DiscColor.YELLOW, 1);
        game.dropDisc(DiscColor.RED, 1);
        game.dropDisc(DiscColor.YELLOW, 1);

        game.dropDisc(DiscColor.YELLOW, 0);
        game.dropDisc(DiscColor.YELLOW, 0);
        game.dropDisc(DiscColor.YELLOW, 0);
        game.dropDisc(DiscColor.RED, 0);

        assertTrue(game.calculateWinner());
    }

    @Test
    public void testCalculateWinnerInBackwardDiagonalShouldReturnTrue() throws Exception{
        Game game = new Game();

        game.dropDisc(DiscColor.RED, 3);
        game.dropDisc(DiscColor.YELLOW, 3);
        game.dropDisc(DiscColor.YELLOW, 3);
        game.dropDisc(DiscColor.YELLOW, 3);

        game.dropDisc(DiscColor.YELLOW, 2);
        game.dropDisc(DiscColor.RED, 2);
        game.dropDisc(DiscColor.YELLOW, 2);
        game.dropDisc(DiscColor.YELLOW, 2);

        game.dropDisc(DiscColor.YELLOW, 0);
        game.dropDisc(DiscColor.YELLOW, 0);
        game.dropDisc(DiscColor.YELLOW, 0);
        game.dropDisc(DiscColor.RED, 0);

        //game.dropDisc(DiscColor.YELLOW, 1);
        game.dropDisc(DiscColor.YELLOW, 1);
        game.dropDisc(DiscColor.YELLOW, 1);
        game.dropDisc(DiscColor.RED, 1);

        assertTrue(game.calculateWinner());
    }
}
