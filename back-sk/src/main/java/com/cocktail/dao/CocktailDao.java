package com.cocktail.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import com.cocktail.model.Cocktail;

public interface CocktailDao extends JpaRepository<Cocktail, String> {
    Cocktail getCocktailByCid(int cid);

    Page<Cocktail> findByMaterialLike(String filtered, Pageable pageable);

    Page<Cocktail> findByCnameLike(String searchfiltered, Pageable pageable);

    List<Cocktail> findByMaterialLike(String filtered);

    List<Cocktail> findByCnameLike(String searchfiltered);

    // List<Cocktail> findByComments(int cid);
}
