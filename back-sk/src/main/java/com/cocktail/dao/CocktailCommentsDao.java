package com.cocktail.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cocktail.model.comments.CocktailComments;;

public interface CocktailCommentsDao extends JpaRepository<CocktailComments, String> {
    Page<CocktailComments> findAllByCocktail_cid(int cocktail_cid, Pageable pageable);

    CocktailComments getCommentsByCmid(int cmid);
    // List<Comments> findByCocktail(Cocktail cocktail);

}
