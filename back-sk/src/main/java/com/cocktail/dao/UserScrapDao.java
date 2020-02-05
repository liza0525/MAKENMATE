package com.cocktail.dao;

import java.util.ArrayList;
import java.util.List;

import com.cocktail.model.board.BoardRecipe;
import com.cocktail.model.user.UserScrap;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserScrapDao extends JpaRepository<UserScrap, String> {
    List<UserScrap> findAllByUser_uid(int user_uid);
}