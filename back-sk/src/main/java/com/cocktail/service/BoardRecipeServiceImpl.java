package com.cocktail.service;

import java.util.List;

import com.cocktail.dao.BoardRecipeDao;
import com.cocktail.model.boardRecipe.BRdetail;
import com.cocktail.model.boardRecipe.BoardRecipe;

import org.springframework.beans.factory.annotation.Autowired;

public class BoardRecipeServiceImpl implements BoardRecipeService {

    @Autowired
    private BoardRecipeDao boardRecipeDao;

    //공유게시판 전체 리스트 조회
    @Override
    public List<BoardRecipe> getAllBoardRecipe() {
        return boardRecipeDao.findAll();
    }

    //글번호로 공유게시판 상세조회
    @Override
    public BRdetail findById(int bid) {
        BRdetail br = boardRecipeDao.findBRdetailById(bid);
        return br;
    }

    @Override
    public int save(BRdetail brdetail) {
        return 0;
    }

    @Override
    public void updateById(BRdetail brdetail) {
    }

    @Override
    public void deleteById(int bid) {
    }

}