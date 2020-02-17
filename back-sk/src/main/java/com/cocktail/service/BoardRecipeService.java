package com.cocktail.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cocktail.model.boardRecipe.BRdetail;
import com.cocktail.model.boardRecipe.BoardRecipe;

public interface BoardRecipeService {

    // 칵테일레시피공유게시판 전체 리스트 조회
    Page<BoardRecipe> getAllBoardRecipe(Pageable pageable);

    // 글 번호로 게시판 상세 보기
    BRdetail findById(int rid);

    // 해당 유저가 스크랩을 했는지 안했는지
    Boolean getUserIdScrappingList(int boardrecipeno, String username);

    // 공유게시글 작성
    int save(BRdetail bRdetail);

    // 공유게시글 수정
    void updateById(BRdetail brdetail);

    // 공유게시글 삭제
    void deleteById(int bid);

	Object getAllBoardRecipeLike(String searchData, Pageable pageable);
}
