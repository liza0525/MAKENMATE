package com.cocktail.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cocktail.model.CommentsLike;

public interface CommentsLikeDao extends JpaRepository<CommentsLike, Long> {
    List<CommentsLike> findAllByUser_uid(int uid);
    CommentsLike findByUser_uidAndComments_cmid(int uid, int cmid);
    Long countByComments_cmid(int cmid);
}