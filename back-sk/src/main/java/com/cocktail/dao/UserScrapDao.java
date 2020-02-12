package com.cocktail.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cocktail.model.user.UserScrap;
import com.cocktail.model.user.UserScrapId;

public interface UserScrapDao extends JpaRepository<UserScrap, UserScrapId> {
	Page<UserScrap> findAllByUser_uid(int user_uid, Pageable pageable);
    List<UserScrap> findAllByBoardrecipe_rid(int boardRecipe_rid);
}