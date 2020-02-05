package com.cocktail.controller;

import java.util.List;

import com.cocktail.dao.UserScrapDao;
import com.cocktail.model.BasicResponse;
import com.cocktail.model.board.BoardRecipe;

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
        
        final List<BoardRecipe> userScrapList = userScrapDao.findAllByUid(uid);
        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";
        result.object = userScrapList;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}