package com.cocktail.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cocktail.model.boardRecipe.BRdetail;
import com.cocktail.model.boardRecipe.BoardRecipe;

//JpaRepository를 상속받으며 매개변수로 도메인클래스와 키타입을 기술한다.
public interface BoardRecipeDao extends JpaRepository<BoardRecipe, Integer> {

    @Query(value = "Select new com.coctail.dao.boardRecipe.BoardRecipe(br.rid, br.title, br.contents, br.image, br.regdate, u.nickname"
            + "From boardrecipe br RIGHT JOIN br.user u"
            +"Where br.bid = ?1", nativeQuery = true)
    BRdetail findBRdetailById(@Param("bid") int bid);

}
