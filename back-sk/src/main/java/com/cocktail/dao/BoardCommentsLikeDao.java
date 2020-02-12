package com.cocktail.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cocktail.model.like.BoardCommentsLike;
import com.cocktail.model.like.CommentsLike;

public interface BoardCommentsLikeDao extends JpaRepository<BoardCommentsLike, Long> {
    List<BoardCommentsLike> findAllByUser_uid(int uid);
    BoardCommentsLike findByUser_uidAndComments_cmid(int uid, int cmid);
    Long countByComments_cmid(int cmid);
}