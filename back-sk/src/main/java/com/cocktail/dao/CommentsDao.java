package com.cocktail.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cocktail.model.Comments;;

public interface CommentsDao extends JpaRepository<Comments, String> {

}
