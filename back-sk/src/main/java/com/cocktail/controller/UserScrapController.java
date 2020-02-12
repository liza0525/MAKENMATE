package com.cocktail.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cocktail.dao.BoardRecipeDao;
import com.cocktail.dao.UserDao;
import com.cocktail.dao.UserScrapDao;
import com.cocktail.exception.CocktailException;
import com.cocktail.model.BasicResponse;
import com.cocktail.model.boardRecipe.BoardRecipe;
import com.cocktail.model.user.User;
import com.cocktail.model.user.UserScrap;

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
    public Object userScrapList(final Pageable pageable, @RequestParam final String username) {
        final User user = userDao.getUserByNickname(username).orElseThrow(CocktailException::new);
        final Page<UserScrap> userScrapList = userScrapDao.findAllByUser_uid(user.getUid(),pageable);
        System.out.println(userScrapList.getContent());
        final BasicResponse result = new BasicResponse();
        final List<BoardRecipe> boardRecipeList = new ArrayList<>();
        for (int i = 0; i < userScrapList.toList().size(); i++)
            boardRecipeList.add(userScrapList.toList().get(i).getBoardrecipe());

        Page<BoardRecipe> page = new PageImpl<>(boardRecipeList,pageable,boardRecipeList.size());
        result.status = true;
        result.data = "success";
        result.object = page;
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
    public Object userScrapRemove(final Pageable pageable, @RequestParam final String username, @RequestParam final int rid) {
        final BasicResponse result = new BasicResponse();
        final UserScrap deleteScrap = new UserScrap();
        final User user = userDao.getUserByNickname(username).orElseThrow(CocktailException::new);
        final BoardRecipe boardRecipe = boardRecipeDao.getOne(rid);
        deleteScrap.setUser(user);
        deleteScrap.setBoardrecipe(boardRecipe);
        userScrapDao.delete(deleteScrap);
        final Page<UserScrap> userScrapList = userScrapDao.findAllByUser_uid(user.getUid(),pageable);
        
        final List<BoardRecipe> boardRecipeList = new ArrayList<>();
        for (int i = 0; i < userScrapList.toList().size(); i++)
            boardRecipeList.add(userScrapList.toList().get(i).getBoardrecipe());
        
        Page<BoardRecipe> page = new PageImpl<>(boardRecipeList,pageable,boardRecipeList.size());
        result.status = true;
        result.data = "success";
        result.object = page;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}