package com.cocktail.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.cocktail.dao.BoardRecipeDao;
import com.cocktail.dao.UserDao;
import com.cocktail.dao.UserScrapDao;
import com.cocktail.exception.CocktailException;
import com.cocktail.model.BasicResponse;
import com.cocktail.model.boardRecipe.BoardRecipe;
import com.cocktail.model.user.User;
import com.cocktail.model.user.UserScrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 3600)
@RestController
public class UserScrapController {
    @Autowired
    UserScrapDao userScrapDao;

    @Autowired
    UserDao userDao;

    @Autowired
    BoardRecipeDao boardRecipeDao;

    @GetMapping("/user/scrap/")
    @ApiOperation(value = "스크랩 목록")
    public Object userScrapList(@RequestParam final String username) {
        final User user = userDao.getUserByNickname(username).orElseThrow(CocktailException::new);
        final List<UserScrap> userScrapList = userScrapDao.findAllByUser_uid(user.getUid());

        final BasicResponse result = new BasicResponse();
        final List<BoardRecipe> boardRecipeList = new ArrayList<>();
        for (int i = 0; i < userScrapList.size(); i++)
            boardRecipeList.add(userScrapList.get(i).getBoardrecipe());

        result.status = true;
        result.data = "success";
        result.object = boardRecipeList;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/user/scrap/")
    @ApiOperation(value = "스크랩 저장")
    public Object userScrapCreate(@RequestParam final String username, @RequestParam final int rid) {
        final BasicResponse result = new BasicResponse();
        final UserScrap addScrap = new UserScrap();
        final User user = userDao.getUserByNickname(username).orElseThrow(CocktailException::new);
        final BoardRecipe boardRecipe = boardRecipeDao.getOne(rid);
        addScrap.setUser(user);
        addScrap.setBoardrecipe(boardRecipe);
        userScrapDao.save(addScrap);
        result.status = true;
        result.data = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/user/scrap/")
    @ApiOperation(value = "스크랩 취소")
    public Object userScrapRemove(@RequestParam final String username, @RequestParam final int rid) {
        final BasicResponse result = new BasicResponse();
        final UserScrap deleteScrap = new UserScrap();
        final User user = userDao.getUserByNickname(username).orElseThrow(CocktailException::new);
        final BoardRecipe boardRecipe = boardRecipeDao.getOne(rid);
        deleteScrap.setUser(user);
        deleteScrap.setBoardrecipe(boardRecipe);
        userScrapDao.delete(deleteScrap);
        result.status = true;
        result.data = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}