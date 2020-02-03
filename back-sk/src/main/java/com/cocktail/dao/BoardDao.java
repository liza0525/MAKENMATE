package com.cocktail.dao;

import java.util.List;
import com.cocktail.model.Board.board;

import org.springframework.data.jpa.repository.JpaRepository;
//JpaRepository를 상속받으며 매개변수로 도메인클래스와 키타입을 기술한다.
public interface BoardDao extends JpaRepository<board, Integer> {
    List<board> findAll();
    board findById(int bid);
}
   
