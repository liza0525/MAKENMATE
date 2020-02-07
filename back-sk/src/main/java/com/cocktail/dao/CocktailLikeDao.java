package com.cocktail.dao;

import java.util.List;

import com.cocktail.model.user.CocktailLike;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CocktailLikeDao extends JpaRepository<CocktailLike, String> {
    List<CocktailLike> findAllByUser_uid(int user_uid);

    List<CocktailLike> findAllByCocktail_cid(int cocktail_cid);
}