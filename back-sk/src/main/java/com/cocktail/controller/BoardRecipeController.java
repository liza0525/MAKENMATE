package com.cocktail.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cocktail.model.BasicResponse;
import com.cocktail.model.boardRecipe.BRdetail;
import com.cocktail.model.boardRecipe.BoardRecipe;
import com.cocktail.service.BoardRecipeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;

@CrossOrigin(origins = { "*" }, maxAge = 3600) // "*" => http://localhost:3000
@RestController
@RequestMapping("/boardrecipe")
public class BoardRecipeController{

    @Autowired
    private BoardRecipeService boardrecipeservice;

    //공유게시판 게시글 전체 조회
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public Object boardRecipeList(@PageableDefault(size = 20, sort = { "rid" }, direction = Direction.DESC) Pageable pageable) {
        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success"; 
        result.object = this.boardrecipeservice.getAllBoardRecipe(pageable);
        System.out.println(this.boardrecipeservice.getAllBoardRecipe(pageable).getContent());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //공유게시판 상세조회
    @GetMapping(value="/{boardrecipeno}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BRdetail> getBoardRecipe(@PathVariable("boardrecipeno") int boardrecipeno) {
        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";
        result.object = this.boardrecipeservice.getAllBoardRecipe(); 
        return new ResponseEntity<BRdetail>(boardrecipeservice.findById(boardrecipeno), HttpStatus.OK);
    }
    
    //공유게시판 스크랩한 유저 목록
    @GetMapping(value="/scrapped/{boardrecipeno}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Boolean> getUserIdScrappingList(@PathVariable("boardrecipeno") int boardrecipeno, @RequestParam("username") String username){
        return new ResponseEntity<Boolean>(boardrecipeservice.getUserIdScrappingList(boardrecipeno, username), HttpStatus.OK);
    }

    //공유게시판 입력
    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Integer> save(@RequestBody BRdetail brdetail){
        System.out.println(brdetail);
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