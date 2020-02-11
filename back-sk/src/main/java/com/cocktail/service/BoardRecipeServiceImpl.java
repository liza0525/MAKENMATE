package com.cocktail.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cocktail.dao.BoardRecipeDao;
import com.cocktail.dao.UserDao;
import com.cocktail.dao.UserScrapDao;
import com.cocktail.exception.CocktailException;
import com.cocktail.model.boardRecipe.BRdetail;
import com.cocktail.model.boardRecipe.BoardRecipe;
import com.cocktail.model.user.User;
import com.cocktail.model.user.UserScrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardRecipeServiceImpl implements BoardRecipeService {

    @Autowired
    private BoardRecipeDao boardRecipeDao;

    @Autowired
    private UserDao userdao;

    @Autowired
    private UserScrapDao userScrapDao;

    // 공유게시판 전체 리스트 조회
    @Override
    public List<BoardRecipe> getAllBoardRecipe() {
        return boardRecipeDao.findAll();
    }

    // 글번호로 공유게시판 상세조회
    @Override
    public BoardRecipe findById(int rid) {
        // BRdetail br = boardRecipeDao.findBRdetailById(bid);
        //BoardRecipe boardrecipe = boardRecipeDao.findByRid(bid).orElseThrow();
        BoardRecipe boardrecipe = boardRecipeDao.findById(rid).orElseThrow(CocktailException::new);
        return boardrecipe;
    }

    // 로그인한 유저가 스크랩한 유저 목록에 있는지 없는지
    @Override
    public Boolean getUserIdScrappingList(int rid, String username){
        List<UserScrap> userScrapList = userScrapDao.findAllByBoardrecipe_rid(rid);
        List<String> usernameList = new ArrayList<>();
        Boolean isScrapping = true;
        for (int i = 0 ; i < userScrapList.size() ; i++) {
            usernameList.add(userScrapList.get(i).getUser().getNickname());
        }
        if (usernameList.contains(username)){
            isScrapping = true;
        } else {
            isScrapping = false;
        }
        return isScrapping;
    }

    @Override
    public int save(BRdetail brdetail) {
        BoardRecipe br = new BoardRecipe();
        br.setContents(brdetail.getContents());
        //r.setImage();
        SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
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
        //br.setImage(image);
        SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
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