package com.moj.game.connect4.controller;

import javax.validation.constraints.NotNull;

/**
 * Created by tarun on 05/01/2017.
 */
public class GamePlayDto {

    @NotNull
    private String userId;

    @NotNull
    private String color;

    @NotNull
    private int column;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
