package com.cocktail.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cocktail.model.board.Bdetail;
import com.cocktail.model.board.Board;

public interface BoardService {

    // 게시판 전체 리스트 조회
    Page<Board> getAllBoard(Pageable pageable);

    // 글 번호로 게시판 상세 보기
    Bdetail findById(int bid);

    // 게시글 작성
    int save(Bdetail bdetail);

    // 게시글 수정
    void updateById(Bdetail bdetail);

    // 게시글 삭제
    void deleteById(int bid);
}
