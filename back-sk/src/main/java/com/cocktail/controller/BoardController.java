package com.cocktail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cocktail.model.BasicResponse;
import com.cocktail.model.board.board;
import com.cocktail.service.BoardService;

@RestController
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService boardservice;

    @GetMapping("/list")
    public Object boardlist() {
        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";
        result.object = this.boardservice.getAllBoard();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/boardno")
    public ResponseEntity<board> getBoard(@PathVariable("boardno") int boardno) {
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" + boardno);
        return new ResponseEntity<board>(boardservice.findById(boardno), HttpStatus.OK);

    }

    // public Object board(int bid){

    // // final JSONObject boardData = new JSONObject();
    // //board b = boardservice.getBoard(bid);
    // //boardData.put("board",b);
    // final BasicResponse result = new BasicResponse();
    // result.status = true;
    // result.data = "success";
    // result.object = this.boardservice.getBoard(bid);
    // return new ResponseEntity<>(result, HttpStatus.OK);
    // }
}