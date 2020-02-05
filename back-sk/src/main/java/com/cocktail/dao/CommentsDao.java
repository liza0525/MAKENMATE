package com.cocktail.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import com.cocktail.model.Cocktail;
import com.cocktail.model.Comments;
import com.cocktail.model.Material;;

public interface CommentsDao extends JpaRepository<Comments, String> {
    List<Comments> findAllByCocktail_cid(int cocktail_cid);
    // List<Comments> findByCocktail(Cocktail cocktail);

}
