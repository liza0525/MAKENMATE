package com.cocktail.controller;

import java.util.HashMap;
import java.util.Map;

import com.cocktail.model.BasicResponse;
import com.cocktail.model.Board.Bdetail;
import com.cocktail.service.BoardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@CrossOrigin(origins = { "*" }, maxAge = 3600) // "*" => http://localhost:3000
@RestController
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService boardservice;

    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public Object boardlist() {
        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";
        result.object = this.boardservice.getAllBoard();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    //게시판 번호로 상세조회
    @GetMapping(value = "/{boardno}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Bdetail> getBoard(@PathVariable("boardno") int boardno) {
        return new ResponseEntity<Bdetail>(boardservice.findById(boardno), HttpStatus.OK);
    }
    
    // @PostMapping
    // public ResponseEntity<board> save(@RequestBody board board, @RequestParam(required = true) String username){
    //     return new ResponseEntity<board>(boardservice.save(board,username), HttpStatus.OK);
    // }

    @PostMapping
    public ResponseEntity<Integer> save(@RequestBody Bdetail bdetail){
        return new ResponseEntity<Integer>(boardservice.save(bdetail), HttpStatus.OK);
    }


    @PutMapping(value="/{boardno}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Map<String, String>> updateBoard(@RequestBody Bdetail bdetail){
        System.out.println(bdetail);
        boardservice.updateById(bdetail);
        Map<String,String> resultMap = new HashMap<>();
        resultMap.put("data","success");
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{boardno}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Map<String, String>> deleteBoard(@PathVariable("boardno") int boardno) {
        boardservice.deleteById(boardno);
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("data", "Success");
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }
}