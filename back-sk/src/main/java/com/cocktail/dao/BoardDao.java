package com.cocktail.dao;

import com.cocktail.model.Board.Board;

import org.springframework.data.jpa.repository.JpaRepository;
//JpaRepository를 상속받으며 매개변수로 도메인클래스와 키타입을 기술한다.
public interface BoardDao extends JpaRepository<Board, Integer> {
   Board findById(int bid);
}
   
