package com.cocktail.service;

import java.util.List;

import com.cocktail.model.board.Bdetail;
import com.cocktail.model.board.board;

public interface BoardService {
    
    //게시판 전체 리스트 조회
    List<board> getAllBoard();

    //글 번호로 게시판 상세 보기 
    Bdetail findById(int bid);

    //게시글 작성
    board save(board board, String username);

    //게시글 수정
    void updateById(int bid, board board, String username);

    //게시글 삭제
    void deleteById(int bid);
}
