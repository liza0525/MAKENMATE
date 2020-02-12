package com.cocktail.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cocktail.model.like.BoardRecipeCommentsLike;
import com.cocktail.model.like.CommentsLike;

public interface BoardRecipeCommentsLikeDao extends JpaRepository<BoardRecipeCommentsLike, Long> {
    List<BoardRecipeCommentsLike> findAllByUser_uid(int uid);
    BoardRecipeCommentsLike findByUser_uidAndComments_cmid(int uid, int cmid);
    Long countByComments_cmid(int cmid);
}