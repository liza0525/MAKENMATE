package com.cocktail.service;

import java.util.List;

import com.cocktail.model.boardRecipe.BRdetail;
import com.cocktail.model.boardRecipe.BoardRecipe;

public interface BoardRecipeService {

    // 칵테일레시피공유게시판 전체 리스트 조회
    List<BoardRecipe> getAllBoardRecipe();

    // 글 번호로 게시판 상세 보기
    BoardRecipe findById(int rid);

    // 공유게시글 작성
    int save(BRdetail bRdetail);

    // 공유게시글 수정
    void updateById(BRdetail brdetail);

    // 공유게시글 삭제
    void deleteById(int bid);
}
