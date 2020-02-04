package com.cocktail.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cocktail.model.Comments;
import com.cocktail.model.Material;;

public interface CommentsDao extends JpaRepository<Comments, String> {

}
