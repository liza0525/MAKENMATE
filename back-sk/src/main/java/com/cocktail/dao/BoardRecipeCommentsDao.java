package com.cocktail.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cocktail.model.comments.BoardRecipeComments;
import com.cocktail.model.comments.CocktailComments;;

public interface BoardRecipeCommentsDao extends JpaRepository<BoardRecipeComments, String> {
    Page<BoardRecipeComments> findAllByBoardRecipe_rid(int boardrecipe_rid, Pageable pageable);

    BoardRecipeComments getCommentsByCmid(int cmid);
    // List<Comments> findByCocktail(Cocktail cocktail);

}
