package com.cocktail.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.cocktail.model.user.UserScrap;
import com.cocktail.model.user.UserScrapId;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserScrapDao extends JpaRepository<UserScrap, UserScrapId> {
    List<UserScrap> findAllByUser_uid(int user_uid);
}