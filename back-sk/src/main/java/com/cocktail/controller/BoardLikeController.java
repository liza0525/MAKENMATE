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

import com.cocktail.dao.BoardDao;
import com.cocktail.dao.BoardLikeDao;
import com.cocktail.dao.UserDao;
import com.cocktail.exception.CocktailException;
// import com.cocktail.dao.UserDao;
import com.cocktail.model.BasicResponse;
import com.cocktail.model.Cocktail;
import com.cocktail.model.board.Board;
import com.cocktail.model.like.BoardLike;
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
public class BoardLikeController {

    @Autowired
    UserDao userDao;
    @Autowired
    BoardDao boardDao;
    @Autowired
    BoardLikeDao boardLikeDao;

    @PostMapping("/board/like")
    @ApiOperation(value = "좋아요 누르기")
    public Object clickLike(@RequestParam(required = true) final String username,
            @RequestParam(required = true) final int bid) {
        User user = userDao.findByNickname(username);
        Board board = boardDao.findById(bid);
        final BasicResponse result = new BasicResponse();
        BoardLike bl = new BoardLike((long) 0, user, board);
        boardLikeDao.save(bl);
        result.status = true;
        result.data = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/board/like")
    @ApiOperation(value = "좋아요 삭제")
    public Object deleteLike(@RequestParam(required = true) final String username,
            @RequestParam(required = true) final int bid) {
        User user = userDao.findByNickname(username);
        Board board = boardDao.findById(bid);
        BoardLike bl = boardLikeDao.findByUser_uidAndBoard_bid(user.getUid(), bid);
        boardLikeDao.deleteById(bl.getId());

        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/board/getlikebyuser")
    @ApiOperation(value = "좋아요한 칵테일 가져오기")
    public Object getLikebyuser(@RequestParam(required = true) final String username) {
        User user = userDao.getUserByNickname(username).orElseThrow(CocktailException::new);
        List<BoardLike> bls = user.getBoardLike();
        List<Board> boards = new ArrayList<>();
        for (BoardLike bl : bls) {
            boards.add(bl.getBoard());
        }
        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";
        result.object = boards;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/board/getlikebyuserandcocktail")
    @ApiOperation(value = "칵테일 좋아요 여부 확인")
    public Object getLikeByUserAndCocktail(@RequestParam(required = true) final String username,
            @RequestParam(required = true) final int bid) {
        User user = userDao.getUserByNickname(username).orElseThrow(CocktailException::new);
        List<BoardLike> bls = user.getBoardLike();
        Board board = boardDao.findById(bid);
        Board res = null;
        for (BoardLike bl : bls) {
            if (board.getBid() == bl.getBoard().getBid())
                res = bl.getBoard();
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

    @GetMapping("/board/getlikebycocktail")
    @ApiOperation(value = "칵테일의 좋아요 수")
    public Object getLikebycocktail(@RequestParam(required = true) final int bid) {
        Long boardLikes = boardLikeDao.countByBoard_bid(bid);
        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";
        result.object = boardLikes;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}