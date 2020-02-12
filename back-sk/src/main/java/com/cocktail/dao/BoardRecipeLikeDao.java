package com.cocktail.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cocktail.model.like.BoardRecipeLike;
import com.cocktail.model.like.CocktailLike;

public interface BoardRecipeLikeDao extends JpaRepository<BoardRecipeLike, Long> {
    List<BoardRecipeLike> findAllByUser_uid(int uid);
    BoardRecipeLike findByUser_uidAndBoardRecipe_rid(int uid, int rid);
    Long countByBoardRecipe_rid(int rid);
}