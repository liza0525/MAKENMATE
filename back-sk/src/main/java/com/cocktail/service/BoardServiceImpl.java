package com.cocktail.service;

import java.util.List;
import java.util.Optional;

import com.cocktail.dao.BoardDao;
import com.cocktail.model.Board.board;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardDao boardDao;

    @Override
    public List<board> getAllBoard() {
        return boardDao.findAll();
    }

    @Override
    public board findById(int bid) {
        board board = boardDao.findById(bid); 
        return board;
    }
}