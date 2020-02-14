package com.cocktail.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cocktail.model.comments.BoardComments;;

public interface BoardCommentsDao extends JpaRepository<BoardComments, String> {
    Page<BoardComments> findAllByBoard_bid(int board_bid, Pageable pageable);

    BoardComments getCommentsByCmid(int cmid);

}
