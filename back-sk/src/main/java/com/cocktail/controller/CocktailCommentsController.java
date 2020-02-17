package com.cocktail.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cocktail.dao.CocktailCommentsDao;
import com.cocktail.dao.CocktailDao;
import com.cocktail.dao.UserDao;
import com.cocktail.exception.CocktailException;
import com.cocktail.model.BasicResponse;
import com.cocktail.model.Cocktail;
import com.cocktail.model.comments.CocktailComments;
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
@RequestMapping(value = "/backend/comments")
public class CocktailCommentsController {
    @Autowired
    CocktailCommentsDao commentsDao;
    @Autowired
    CocktailDao cocktailDao;
    @Autowired
    UserDao userDao;

    @GetMapping("/cocktail/{cid}")
    @ApiOperation(value = "댓글")
    public Object load(@PageableDefault(size = 5, sort = { "cmid" }, direction = Direction.DESC) final Pageable pageable, @PathVariable final int cid) {
        // final Cocktail cocktail = cocktailDao.getCocktailByCid(cid);
        final Page<CocktailComments> comments = commentsDao.findAllByCocktail_cid(cid,pageable);
        final List<String> UserArray = new ArrayList<>();
        for (int idex = 0; idex < comments.getNumberOfElements(); ++idex) {
        	comments.toList().get(idex).setCount(comments.toList().get(idex).getComments().size());
            User user = userDao.getUserByUid(comments.toList().get(idex).getUser_uid()).orElseThrow(CocktailException::new);
            UserArray.add(user.getNickname());
        }
        Map<String, Object> result = new HashMap<>();
        result.put("comments", comments);
        result.put("UserArray", UserArray);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/cocktail/{cid}")
    @ApiOperation(value = "댓글 생성")
    public Object create(@PathVariable final int cid, @RequestParam(required = true) final String username,
            @RequestParam(required = true) final String content) {
        final Cocktail cocktail = cocktailDao.getCocktailByCid(cid);
        final User user = userDao.getUserByNickname(username).orElseThrow(CocktailException::new);
        final CocktailComments newComment = new CocktailComments();
        newComment.setUser_uid(user.getUid());
        newComment.setContent(content);
        newComment.setCocktail(cocktail);
        System.out.println(cocktail.getCid());
        // cocktail.getCommentsArray().add(newComment);
        commentsDao.save(newComment);
        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";
        // System.out.println(user.toString());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/cocktail/{cmid}")
    @ApiOperation(value = "댓글 삭제")
    public Object delete(@PathVariable final int cmid) {
        final CocktailComments comment = commentsDao.getCommentsByCmid(cmid);
        commentsDao.delete(comment);
        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/cocktail/{cmid}")
    @ApiOperation(value = "댓글 수정")
    public Object update(@PathVariable final int cmid, @RequestParam(required = true) final String content) {
        final CocktailComments comment = commentsDao.getCommentsByCmid(cmid);
        comment.setContent(content);
        commentsDao.save(comment);
        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
}