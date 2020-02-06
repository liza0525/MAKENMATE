package com.cocktail.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cocktail.model.board.Board;
import com.cocktail.model.boardRecipe.BoardRecipe;

//JpaRepository를 상속받으며 매개변수로 도메인클래스와 키타입을 기술한다.
public interface BoardRecipeDao extends JpaRepository<BoardRecipe, Integer> {
    Board findById(int bid);

    // List<board> findByComments(int bid);
}
