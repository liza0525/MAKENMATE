package com.cocktail.service;

import java.util.List;

import com.cocktail.model.board.Bdetail;
import com.cocktail.model.board.Board;

public interface BoardService {

    // 게시판 전체 리스트 조회
    List<Board> getAllBoard();

    // 글 번호로 게시판 상세 보기
    Bdetail findById(int bid);

    //게시글 작성
    Board save(Board board, String username);
    int save(Bdetail bdetail);

    //게시글 수정
    void updateById(Bdetail bdetail);

    // 게시글 삭제
    void deleteById(int bid);
}
