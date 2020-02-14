package com.cocktail.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cocktail.dao.BoardRecipeDao;
import com.cocktail.dao.BoardRecipeLikeDao;
import com.cocktail.dao.UserDao;
import com.cocktail.exception.CocktailException;
// import com.cocktail.dao.UserDao;
import com.cocktail.model.BasicResponse;
import com.cocktail.model.Cocktail;
import com.cocktail.model.boardRecipe.BoardRecipe;
import com.cocktail.model.like.BoardRecipeLike;
import com.cocktail.model.like.CocktailLike;
import com.cocktail.model.user.User;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = { "*" }, maxAge = 3600) // "*" => http://localhost:3000
@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@RestController
@RequestMapping(value = "/backend")
public class BoardRecipeLikeController {

    @Autowired
    UserDao userDao;
    @Autowired
    BoardRecipeDao boardRecipeDao;
    @Autowired
    BoardRecipeLikeDao boardRecipeLikeDao;

    @PostMapping("/boardrecipe/like")
    @ApiOperation(value = "좋아요 누르기")
    public Object clickLike(@RequestParam(required = true) final String username,
            @RequestParam(required = true) final int rid) {
        User user = userDao.findByNickname(username);
        BoardRecipe boardRecipe = boardRecipeDao.findByRid(rid).orElseThrow(CocktailException::new);
        final BasicResponse result = new BasicResponse();
        BoardRecipeLike rl = new BoardRecipeLike((long) 0, user, boardRecipe);
        boardRecipeLikeDao.save(rl);
        result.status = true;
        result.data = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/boardrecipe/like")
    @ApiOperation(value = "좋아요 삭제")
    public Object deleteLike(@RequestParam(required = true) final String username,
            @RequestParam(required = true) final int rid) {
        User user = userDao.findByNickname(username);
        BoardRecipe boardRecipe = boardRecipeDao.findByRid(rid).orElseThrow(CocktailException::new);
        BoardRecipeLike rl = boardRecipeLikeDao.findByUser_uidAndBoardRecipe_rid(user.getUid(), rid);
        boardRecipeLikeDao.deleteById(rl.getId());

        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/boardrecipe/getlikebyuser")
    @ApiOperation(value = "좋아요한 칵테일 가져오기")
    public Object getLikebyuser(@RequestParam(required = true) final String username) {
        User user = userDao.getUserByNickname(username).orElseThrow(CocktailException::new);
        List<BoardRecipeLike> rls = user.getBoardRecipeLike();
        List<BoardRecipe> boardRecipe = new ArrayList<>();
        for (BoardRecipeLike cl : rls) {
            boardRecipe.add(cl.getBoardRecipe());
        }
        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";
        result.object = boardRecipe;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/boardrecipe/getlikebyuserandcocktail")
    @ApiOperation(value = "칵테일 좋아요 여부 확인")
    public Object getLikeByUserAndCocktail(@RequestParam(required = true) final String username,
            @RequestParam(required = true) final int rid) {
        User user = userDao.getUserByNickname(username).orElseThrow(CocktailException::new);
        List<BoardRecipeLike> rls = user.getBoardRecipeLike();
        BoardRecipe boardRecipe = boardRecipeDao.getBoardRecipeByRid(rid);
        BoardRecipe res = null;
        for (BoardRecipeLike rl : rls) {
            if (boardRecipe.getRid() == rl.getBoardRecipe().getRid())
                res = rl.getBoardRecipe();
        }
        final BasicResponse result = new BasicResponse();
        if (res == null)
            result.object = null;
        else
            result.object = res;
        result.status = true;
        result.data = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/boardrecipe/getlikebycocktail")
    @ApiOperation(value = "칵테일의 좋아요 수")
    public Object getLikebycocktail(@RequestParam(required = true) final int rid) {
        Long boardRecipeLikes = boardRecipeLikeDao.countByBoardRecipe_rid(rid);
        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";
        result.object = boardRecipeLikes;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}