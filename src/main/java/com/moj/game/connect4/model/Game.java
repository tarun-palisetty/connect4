package com.moj.game.connect4.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.moj.game.connect4.common.DiscColor;
import com.moj.game.connect4.common.GameStatus;
import com.moj.game.connect4.exception.InvalidGamePlayException;
import jersey.repackaged.com.google.common.hash.HashCode;

/**
 * Created by tarun on 05/01/2017.
 */
public class Game {

    public static final String OBJECT_KEY = "GAME";

    private String id;

    private Player player1;
    private Player player2;

    private DiscColor[][] board = new DiscColor[6][7];

    private int lastPlayedX = -1;
    private int lastPlayedY = -1;

    private GameStatus status = GameStatus.CREATED;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public DiscColor[][] getBoard() {
        return board;
    }

    public void setBoard(DiscColor[][] board) {
        this.board = board;
    }

    public int getLastPlayedX() {
        return lastPlayedX;
    }

    public void setLastPlayedX(int lastPlayedX) {
        this.lastPlayedX = lastPlayedX;
    }

    public int getLastPlayedY() {
        return lastPlayedY;
    }

    public void setLastPlayedY(int lastPlayedY) {
        this.lastPlayedY = lastPlayedY;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    @JsonIgnore
    public DiscColor getLastPlayedDisc(){
        if (lastPlayedX == -1 && lastPlayedY == -1){
            return player2.getDiscColor();
        }

        return board[lastPlayedX][lastPlayedY];
    }

    public boolean calculateWinner(){
        return validateInRow() || validateInColumn() || validateInForwardDiagonal() || validateReverseDiagonal();
    }

    public boolean validateInRow(){
        int start = (lastPlayedY - 3) > 0? lastPlayedY -3 : 0;
        int end = (lastPlayedY+3) < 6?lastPlayedY+3 : 6;
        int count = 0;

        for (int i=start; i<=end ; i++){
            if (getLastPlayedDisc().equals(board[lastPlayedX][i])){
                count++;

                if (count == 4){
                    return true;
                }
            }
            else {
                count = 0;
            }
        }
        return false;
    }

    public boolean validateInColumn(){
        int start = (lastPlayedX - 3) > 0? lastPlayedX -3 : 0;
        int end = (lastPlayedX+3) < 6?lastPlayedX+3 : 5;
        int count = 0;

        for (int i=start; i<=end ; i++){
            if (getLastPlayedDisc().equals(board[i][lastPlayedY])){
                count++;

                if (count == 4){
                    return true;
                }
            }
            else {
                count = 0;
            }
        }
        return false;
    }

    public boolean validateInForwardDiagonal(){
        int startX = lastPlayedX;
        int startY = lastPlayedY;

        for (int i=0; i<3; i++){
            if (startX == 0 || startY == 0){
                break;
            }

            startX--;
            startY--;
        }

        int endX = lastPlayedX;
        int endY = lastPlayedY;
        for (int i=0; i<3; i++){
            if (endX ==5 || endY == 6){
                break;
            }
            endX++;
            endY++;
        }

        int count=0;

        for (int i=startX, j=startY; i<=endX || j<=endY; i++, j++){
            if(getLastPlayedDisc().equals(board[i][j])){
                count++;
                if (count==4){
                    return true;
                }
            }else{
                count=0;
            }
        }

        return false;
    }

    private boolean validateReverseDiagonal(){
        int startX = lastPlayedX;
        int startY = lastPlayedY;

        for (int i=0; i<3; i++){
            if (startX==0 || startY ==6){
                break;
            }

            startX--;
            startY++;
        }

        int endX=lastPlayedX;
        int endY=lastPlayedY;

        for (int i=0; i<3; i++){
            if (endX == 5 || endY == 0){
                break;
            }
            endX++;
            endY--;
        }

        int count=0;
        for (int i=startX, j=startY; i<=endX || j>=endY; i++, j--){
            if(getLastPlayedDisc().equals(board[i][j])){
                count++;
                if (count==4){
                    return true;
                }
            }else {
                count=0;
            }
        }
        return false;
    }

    public void dropDisc(DiscColor disc, int column){
        if (board[0][column] != null){
            throw new InvalidGamePlayException("No space for the column: "+column);
        }

        for(int i=5; i>=0 ; i--){
            if (board[i][column] == null){
                board[i][column] = disc;
                this.lastPlayedX=i;
                this.lastPlayedY=column;
                break;
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Game game = (Game) o;

        if (id != null ? !id.equals(game.id) : game.id != null) return false;
        if (player1 != null ? !player1.equals(game.player1) : game.player1 != null) return false;
        return player2 != null ? player2.equals(game.player2) : game.player2 == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (player1 != null ? player1.hashCode() : 0);
        result = 31 * result + (player2 != null ? player2.hashCode() : 0);
        return result;
    }
}
