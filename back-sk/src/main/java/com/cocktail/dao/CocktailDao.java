package com.cocktail.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cocktail.model.Cocktail;

public interface CocktailDao extends JpaRepository<Cocktail, String> {
    Cocktail getCocktailByCid(int cid);

    Page<Cocktail> findByMaterialLike(String filtered, Pageable pageable);
}
