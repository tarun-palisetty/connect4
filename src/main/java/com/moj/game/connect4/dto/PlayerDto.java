package com.moj.game.connect4.dto;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by tarun on 05/01/2017.
 */
public class PlayerDto {

    @NotEmpty
    @NotNull
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
