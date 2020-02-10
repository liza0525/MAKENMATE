package com.cocktail.controller;

import java.util.HashMap;
import java.util.Map;

import com.cocktail.model.BasicResponse;
import com.cocktail.model.boardRecipe.BRdetail;
import com.cocktail.model.boardRecipe.BoardRecipe;
import com.cocktail.service.BoardRecipeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@CrossOrigin(origins = { "*" }, maxAge = 3600) // "*" => http://localhost:3000
@RestController
@RequestMapping("/boardrecipe")
public class BoardRecipeController{

    @Autowired
    private BoardRecipeService boardrecipeservice;

    //공유게시판 게시글 전체 조회
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public Object boardRecipeList() {
        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";
        result.object = this.boardrecipeservice.getAllBoardRecipe();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //공유게시판 상세조회
    @GetMapping(value="/{boardrecipeno}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BoardRecipe> getBoardRecipe(@PathVariable("boardrecipeno") int boardrecipeno) {
        return new ResponseEntity<BoardRecipe>(boardrecipeservice.findById(boardrecipeno), HttpStatus.OK);
    }
    
    //공유게시판 입력
    @PostMapping
    public ResponseEntity<Integer> save(@RequestBody BRdetail brdetail){
        return new ResponseEntity<Integer>(boardrecipeservice.save(brdetail), HttpStatus.OK);
    }

    @PutMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Map<String, String>> updateBoard(@RequestBody BRdetail brdetail) {
        boardrecipeservice.updateById(brdetail);
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("data", "success");
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{boardrecipeno}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Map<String, String>> deleteBoard(@PathVariable("boardrecipeno") int boardrecipeno) {
        boardrecipeservice.deleteById(boardrecipeno);
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("data", "Success");
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }
}