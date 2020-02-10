package com.cocktail.model.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.cocktail.model.boardRecipe.BoardRecipe;

public class UserScrapId implements Serializable{
    private int user;

    private int boardrecipe;

    public UserScrapId(){}
    public UserScrapId(int user, int boardrecipe){
        super();
        this.user = user;
        this.boardrecipe = boardrecipe;
    }
}