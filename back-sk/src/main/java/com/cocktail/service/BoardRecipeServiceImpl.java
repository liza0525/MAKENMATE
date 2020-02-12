package com.cocktail.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.cocktail.dao.BoardRecipeDao;
import com.cocktail.dao.UserDao;
import com.cocktail.exception.CocktailException;
import com.cocktail.model.boardRecipe.BRdetail;
import com.cocktail.model.boardRecipe.BoardRecipe;
import com.cocktail.model.user.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardRecipeServiceImpl implements BoardRecipeService {

    @Autowired
    private BoardRecipeDao boardRecipeDao;

    @Autowired
    private UserDao userdao;

    // 공유게시판 전체 리스트 조회
    @Override
    public List<BoardRecipe> getAllBoardRecipe() {
        return boardRecipeDao.findAll();
    }

    // 글번호로 공유게시판 상세조회
    @Override
    public BoardRecipe findById(int rid) {
        // BRdetail br = boardRecipeDao.findBRdetailById(bid);
        // BoardRecipe boardrecipe = boardRecipeDao.findByRid(bid).orElseThrow();
        BoardRecipe boardrecipe = boardRecipeDao.findById(rid).orElseThrow(CocktailException::new);
        System.out.println(boardrecipe);
        return boardrecipe;
    }

    @Override
    public int save(BRdetail brdetail) {
        BoardRecipe br = new BoardRecipe();
        br.setContents(brdetail.getContents());
        // r.setImage();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time = new Date();
        String time1 = format1.format(time);
        br.setRegdate(time1);
        br.setTitle(brdetail.getTitle());
        User u = userdao.findByNickname(brdetail.getUser_name());
        br.setUser(u);
        br = boardRecipeDao.save(br);
        return br.getRid();
    }

    @Override
    public void updateById(BRdetail brdetail) {
        BoardRecipe br = boardRecipeDao.getBoardRecipeByRid(brdetail.getRid());
        br.setContents(brdetail.getContents());
        // br.setImage(image);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time = new Date();
        String time1 = format1.format(time);
        br.setRegdate(time1);
        br.setTitle(brdetail.getTitle());
        System.out.println(br);
        boardRecipeDao.save(br);
    }

    @Override
    public void deleteById(int bid) {
        boardRecipeDao.deleteById(bid);
    }

}