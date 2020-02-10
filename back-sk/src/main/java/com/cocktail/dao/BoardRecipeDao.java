package com.cocktail.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.cocktail.model.boardRecipe.BoardRecipe;

//JpaRepository를 상속받으며 매개변수로 도메인클래스와 키타입을 기술한다.
public interface BoardRecipeDao extends JpaRepository<BoardRecipe, Integer> {
    //@Query(value = "Select * From boardrecipe br RIGHT JOIN user u ON br.user_uid = u.uid Where rid = :rid", nativeQuery = true)
    //oardRecipe find@Param("rid") int rid);

	BoardRecipe getBoardRecipeByRid(int rid);
    Optional<BoardRecipe> findByRid(int rid);
    // List<board> findByComments(int bid);
}
