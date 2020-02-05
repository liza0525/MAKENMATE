package com.cocktail.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.cocktail.dao.UserDao;
import com.cocktail.dao.UserScrapDao;
import com.cocktail.exception.CocktailException;
import com.cocktail.model.BasicResponse;
import com.cocktail.model.board.BoardRecipe;
import com.cocktail.model.user.User;
import com.cocktail.model.user.UserScrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = { "*" }, maxAge = 3600)
@RestController
public class UserScrapController {
    @Autowired
    UserScrapDao userScrapDao;

    @GetMapping(value = "/user/scrap/{uid}")
    public Object userScrapList(@PathVariable final int uid) {
        
        final List<UserScrap> userScrapList = userScrapDao.findAllByUser_uid(uid);
        
        final BasicResponse result = new BasicResponse();
        final List<BoardRecipe> boardRecipeList = new ArrayList<>();
        for (int i = 0; i < userScrapList.size(); i++) 
            boardRecipeList.add(userScrapList.get(i).getBoardrecipe());
        
        result.status = true;
        result.data = "success";
        result.object = boardRecipeList;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}