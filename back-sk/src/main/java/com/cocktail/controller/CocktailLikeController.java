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

import com.cocktail.dao.CocktailDao;
import com.cocktail.dao.CocktailLikeDao;
import com.cocktail.dao.UserDao;
import com.cocktail.exception.CocktailException;
// import com.cocktail.dao.UserDao;
import com.cocktail.model.BasicResponse;
import com.cocktail.model.Cocktail;
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
public class CocktailLikeController {

    @Autowired
    UserDao userDao;
    @Autowired
    CocktailDao cocktailDao;
    @Autowired
    CocktailLikeDao cocktailLikeDao;

    @PostMapping("/cocktail/like")
    @ApiOperation(value = "좋아요 누르기")
    public Object clickLike(@RequestParam(required = true) final String username,
            @RequestParam(required = true) final int cid) {
        User user = userDao.findByNickname(username);
        Cocktail cocktail = cocktailDao.getCocktailByCid(cid);
        final BasicResponse result = new BasicResponse();
        CocktailLike cl = new CocktailLike((long) 0, user, cocktail);
        cocktailLikeDao.save(cl);
        result.status = true;
        result.data = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/cocktail/like")
    @ApiOperation(value = "좋아요 삭제")
    public Object deleteLike(@RequestParam(required = true) final String username,
            @RequestParam(required = true) final int cid) {
        User user = userDao.findByNickname(username);
        Cocktail cocktail = cocktailDao.getCocktailByCid(cid);
        CocktailLike cl = cocktailLikeDao.findByUser_uidAndCocktail_cid(user.getUid(), cid);
        cocktailLikeDao.deleteById(cl.getId());

        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/cocktail/getlikebyuser")
    @ApiOperation(value = "좋아요한 칵테일 가져오기")
    public Object getLikebyuser(@RequestParam(required = true) final String username) {
        User user = userDao.getUserByNickname(username).orElseThrow(CocktailException::new);
        List<CocktailLike> cls = user.getCocktailLike();
        List<Cocktail> cocktails = new ArrayList<>();
        for (CocktailLike cl : cls) {
            cocktails.add(cl.getCocktail());
        }
        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";
        result.object = cocktails;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/cocktail/getlikebyuserandcocktail")
    @ApiOperation(value = "칵테일 좋아요 여부 확인")
    public Object getLikeByUserAndCocktail(@RequestParam(required = true) final String username,
            @RequestParam(required = true) final int cid) {
        User user = userDao.getUserByNickname(username).orElseThrow(CocktailException::new);
        List<CocktailLike> cls = user.getCocktailLike();
        Cocktail cocktail = cocktailDao.getCocktailByCid(cid);
        Cocktail res = null;
        for (CocktailLike cl : cls) {
            if (cocktail.getCid() == cl.getCocktail().getCid())
                res = cl.getCocktail();
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

    @GetMapping("/cocktail/getlikebycocktail")
    @ApiOperation(value = "칵테일의 좋아요 수")
    public Object getLikebycocktail(@RequestParam(required = true) final int cid) {
        Long cocktailLikes = cocktailLikeDao.countByCocktail_cid(cid);
        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";
        result.object = cocktailLikes;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}