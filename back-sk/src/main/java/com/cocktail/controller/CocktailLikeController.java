package com.cocktail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;

import com.cocktail.dao.CocktailDao;
import com.cocktail.dao.CocktailLikeDao;
import com.cocktail.dao.UserDao;
import com.cocktail.exception.CocktailException;
// import com.cocktail.dao.UserDao;
import com.cocktail.model.BasicResponse;
import com.cocktail.model.Cocktail;
import com.cocktail.model.user.CocktailLike;
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
public class CocktailLikeController {

    @Autowired
    UserDao userDao;
    @Autowired
    CocktailDao cocktailDao;
    @Autowired
    CocktailLikeDao cocktailLikeDao;

    @PostMapping("/cocktail/like")
    @ApiOperation(value = "좋아요 누르기")
    public Object clickLike(@RequestParam(required = true) final String email,
            @RequestParam(required = true) final int cid) {
        User user = userDao.getUserByEmail(email).orElseThrow(CocktailException::new);
        Cocktail cocktail = cocktailDao.getCocktailByCid(cid);
        System.out.println(user.getUid());
        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/cocktail/getlikebyuser")
    @ApiOperation(value = "좋아요한 칵테일 가져오기")
    public Object getLikebyuser(@RequestParam(required = true) final String email) {
        User user = userDao.getUserByEmail(email).orElseThrow(CocktailException::new);
        List<CocktailLike> cocktailLikeCidArray = cocktailLikeDao.findAllByUser_uid(user.getUid());
        for (int i = 0; i < cocktailLikeCidArray.size(); ++i) {

        }
        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";
        result.object = cocktailLikeCidArray;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/cocktail/getlikebycocktail")
    @ApiOperation(value = "칵테일의 좋아요 수")
    public Object getLikebycocktail(@RequestParam(required = true) final int cid) {
        Cocktail cocktail = cocktailDao.getCocktailByCid(cid);
        int cocktailLikes = cocktailLikeDao.findAllByCocktail_cid(cocktail.getCid()).size();
        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";
        result.object = cocktailLikes;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}