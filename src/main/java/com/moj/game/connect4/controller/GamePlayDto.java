package com.moj.game.connect4.controller;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by tarun on 05/01/2017.
 */
public class GamePlayDto {

    @NotNull
    @NotEmpty
    private String userId;

    private String color;

    @Min(0)
    @Max(6)
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
