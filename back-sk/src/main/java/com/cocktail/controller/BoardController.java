package com.cocktail.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.Multipart;

import com.cocktail.dao.BoardDao;
import com.cocktail.dao.UserDao;
import com.cocktail.exception.CocktailException;
import com.cocktail.model.BasicResponse;
import com.cocktail.model.board.Bdetail;
import com.cocktail.model.board.Board;
import com.cocktail.model.user.User;
import com.cocktail.service.BoardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.data.domain.Sort.Direction;

@CrossOrigin(origins = { "*" }, maxAge = 3600) // "*" => http://localhost:3000
@RestController
@RequestMapping("/backend/board")
public class BoardController {

    @Autowired
    private BoardService boardservice;

    @Autowired
    private UserDao userDao;

    @Autowired
    private BoardDao boardDao;

    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public Object boardlist(
            @PageableDefault(size = 20, sort = { "bid" }, direction = Direction.DESC) Pageable pageable) {

        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";
        result.object = this.boardservice.getAllBoard(pageable);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping(value = "/search",produces = {MediaType.APPLICATION_JSON_VALUE})
    public Object boardSearchList(@PageableDefault(size = 20, sort = { "rid" }, direction = Direction.DESC) Pageable pageable,
    		@RequestParam(required = true) String searchData) {
    	final BasicResponse result = new BasicResponse();
    	result.status = true;
    	result.data = "success"; 
    	result.object = this.boardservice.getAllBoardTitleLike(searchData, pageable);
    	return new ResponseEntity<>(result, HttpStatus.OK);
    }
    // 게시판 번호로 상세조회
    @GetMapping(value = "/{boardno}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Bdetail> getBoard(@PathVariable("boardno") int boardno) {
        return new ResponseEntity<Bdetail>(boardservice.findById(boardno), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Integer> save(@RequestBody Bdetail bdetail) {
        bdetail.setContents(bdetail.getContents().replace("\n", "<br/>"));
        System.out.println(bdetail);
        return new ResponseEntity<Integer>(boardservice.save(bdetail), HttpStatus.OK);
    }

    @PutMapping(value = "/{boardno}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Map<String, String>> updateBoard(@RequestBody Bdetail bdetail) {
        System.out.println(bdetail);
        boardservice.updateById(bdetail);
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("data", "success");
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{boardno}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Map<String, String>> deleteBoard(@PathVariable("boardno") int boardno) {
        boardservice.deleteById(boardno);
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("data", "Success");
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @GetMapping(value = "/user/{user_uid}")
    public Object getBoardByUser(
            @PageableDefault(size = 5, sort = { "count" }, direction = Direction.DESC) final Pageable pageable,
            @PathVariable final int user_uid) {
        final Page<Board> boards = boardDao.findAllByUser_uid(user_uid, pageable);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("boards", boards);
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }
}