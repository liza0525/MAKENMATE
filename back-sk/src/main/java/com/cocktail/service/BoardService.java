package com.cocktail.service;

import java.util.List;

import com.cocktail.model.Board.board;

public interface BoardService {
    
    //게시판 전체 조회
    List<board> getAllBoard();

    //게시글 번호로 게시판 하나 검색
    board findById(int bid);
}
