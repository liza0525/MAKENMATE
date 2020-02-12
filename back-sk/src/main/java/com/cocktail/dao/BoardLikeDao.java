package com.cocktail.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cocktail.model.like.BoardLike;
import com.cocktail.model.like.CocktailLike;

public interface BoardLikeDao extends JpaRepository<BoardLike, Long> {
    List<BoardLike> findAllByUser_uid(int uid);
    BoardLike findByUser_uidAndBoard_bid(int uid, int bid);
    Long countByBoard_bid(int bid);
}