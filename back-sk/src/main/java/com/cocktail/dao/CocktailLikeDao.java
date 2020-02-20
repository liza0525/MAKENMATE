package com.cocktail.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cocktail.model.like.CocktailLike;

public interface CocktailLikeDao extends JpaRepository<CocktailLike, Long> {
    List<CocktailLike> findAllByUser_uid(int uid);
    CocktailLike findByUser_uidAndCocktail_cid(int uid, int cid);
    Long countByCocktail_cid(int cid);
}