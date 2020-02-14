package com.cocktail.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cocktail.model.board.Board;
import com.cocktail.model.user.User;

//JpaRepository를 상속받으며 매개변수로 도메인클래스와 키타입을 기술한다.
public interface BoardDao extends JpaRepository<Board, Integer> {
    Board findById(int bid);

    Page<Board> findAllByUser_uid(int user_uid, Pageable pageable);
}
