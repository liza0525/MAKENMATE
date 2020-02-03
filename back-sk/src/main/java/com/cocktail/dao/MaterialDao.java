package com.cocktail.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cocktail.model.Material;;

public interface MaterialDao extends JpaRepository<Material, String> {
    Material getMaterialByMname(String mname);
}
