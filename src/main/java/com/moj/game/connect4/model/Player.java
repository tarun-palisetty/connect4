package com.moj.game.connect4.model;

import com.moj.game.connect4.common.DiscColor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by tarun on 05/01/2017.
 */
public class Player {

    private String userId;

    private String color;

    private DiscColor discColor;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public DiscColor getDiscColor() {
        return discColor;
    }

    public void setDiscColor(DiscColor discColor) {
        this.discColor = discColor;
    }

}
