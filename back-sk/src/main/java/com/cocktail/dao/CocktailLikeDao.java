package com.cocktail.dao;

import java.util.List;

import com.cocktail.model.user.CocktailLike;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CocktailLikeDao extends JpaRepository<CocktailLike, Long> {
    List<CocktailLike> findAllByUser_uid(int uid);
    CocktailLike findByUser_uidAndCocktail_cid(int uid, int cid);
    Long countByCocktail_cid(int cid);
}