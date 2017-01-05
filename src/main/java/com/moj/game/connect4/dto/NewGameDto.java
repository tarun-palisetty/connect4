package com.moj.game.connect4.dto;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by tarun on 05/01/2017.
 */

public class NewGameDto {
    @NotNull
    @NotEmpty
    private String userId;

    @NotNull
    @NotEmpty
    private String color;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
