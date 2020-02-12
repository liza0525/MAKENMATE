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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cocktail.dao.CocktailCommentsDao;
import com.cocktail.dao.CommentsLikeDao;
import com.cocktail.dao.UserDao;
import com.cocktail.exception.CocktailException;
import com.cocktail.model.BasicResponse;
import com.cocktail.model.comments.CocktailComments;
import com.cocktail.model.like.CommentsLike;
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
public class CommentsLikeController {

    @Autowired
    UserDao userDao;
    @Autowired
    CocktailCommentsDao commentsDao;
    @Autowired
    CommentsLikeDao commentsLikeDao;

    @PostMapping("/comments/like")
    @ApiOperation(value = "좋아요 누르기")
    public Object clickLike(@RequestParam(required = true) final String username,
            @RequestParam(required = true) final int cmid) {
        User user = userDao.findByNickname(username);
        CocktailComments comments = commentsDao.getCommentsByCmid(cmid);
        comments.setCount(comments.getCount()+1);
        commentsDao.save(comments);
        CommentsLike cl = new CommentsLike((long) 0, user, comments);
        commentsLikeDao.save(cl);
        final BasicResponse result = new BasicResponse();
        
        result.status = true;
        result.data = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/comments/like")
    @ApiOperation(value = "좋아요 삭제")
    public Object deleteLike(@RequestParam(required = true) final String username,
            @RequestParam(required = true) final int cmid) {
        User user = userDao.findByNickname(username);
        CocktailComments comments = commentsDao.getCommentsByCmid(cmid);
        CommentsLike cl = commentsLikeDao.findByUser_uidAndComments_cmid(user.getUid(), cmid);
        comments.setCount(comments.getCount()-1);
        commentsDao.save(comments);
        commentsLikeDao.deleteById(cl.getId());

        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/comments/getlikebyuser")
    @ApiOperation(value = "좋아요한  댓글 가져오기")
    public Object getLikebyuser(@RequestParam(required = true) final String username) {
        User user = userDao.getUserByNickname(username).orElseThrow(CocktailException::new);
        List<CommentsLike> cls = user.getCommentsLike();
        List<CocktailComments> comments = new ArrayList<>();
        for (CommentsLike cl : cls) {
            comments.add(cl.getComments());
        }
        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";
        result.object = comments;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/comments/getlikebyuserandcomments")
    @ApiOperation(value = "댓글 좋아요 여부 확인")
    public Object getLikeByUserAndComments(@RequestParam(required = true) final String username,
            @RequestParam(required = true) final int cmid) {
        User user = userDao.getUserByNickname(username).orElseThrow(CocktailException::new);
        List<CommentsLike> cls = user.getCommentsLike();
        CocktailComments comments= commentsDao.getCommentsByCmid(cmid);
        CocktailComments res = null;
        for (CommentsLike cl : cls) {
            if (comments.getCmid() == cl.getComments().getCmid())
                res = cl.getComments();
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

    @GetMapping("/comments/getlikebycomments")
    @ApiOperation(value = "댓글의 좋아요 수")
    public Object getLikebycocktail(@RequestParam(required = true) final int cmid) {
        Long commentsLike = commentsLikeDao.countByComments_cmid(cmid);
        System.out.println(commentsLike);
        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";
        result.object = commentsLike;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}