package com.cocktail.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cocktail.dao.BoardRecipeDao;
import com.cocktail.dao.FileDAO;
import com.cocktail.dao.UserDao;
import com.cocktail.dao.UserScrapDao;
import com.cocktail.exception.CocktailException;
import com.cocktail.model.UploadFile;
import com.cocktail.model.boardRecipe.BRdetail;
import com.cocktail.model.boardRecipe.BoardRecipe;
import com.cocktail.model.user.User;
import com.cocktail.model.user.UserScrap;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class BoardRecipeServiceImpl implements BoardRecipeService {

    @Autowired
    private BoardRecipeDao boardRecipeDao;

    @Autowired
    private UserDao userdao;

    @Autowired
    private UserScrapDao userScrapDao;

    @Autowired
    private FileDAO filedao;
    // 공유게시판 전체 리스트 조회
    @Override
    public Page<BoardRecipe> getAllBoardRecipe(Pageable pageable) {
        return boardRecipeDao.findAll(pageable);
    }

    // 글번호로 공유게시판 상세조회
    @Override
    public BRdetail findById(int rid) {
        // BRdetail br = boardRecipeDao.findBRdetailById(bid);
        // BoardRecipe boardrecipe = boardRecipeDao.findByRid(bid).orElseThrow();
        BoardRecipe boardrecipe = boardRecipeDao.findById(rid).orElseThrow(CocktailException::new);
        BRdetail br = new BRdetail();        
        br.setRid(boardrecipe.getRid());
        br.setTitle(boardrecipe.getTitle());
        br.setContents(boardrecipe.getContents());
        br.setRegdate(boardrecipe.getContents());
        br.setUser_name(boardrecipe.getUser().getNickname());
        
        List<UploadFile> file = filedao.list(boardrecipe.getRid());
        ArrayList<String> bb = new ArrayList<>();
        if(file.size() != 0) {
            for(int i=0; i < file.size(); i++){
                bb.add(i, file.get(i).getFileName());
                //filename 담아서 brdetail에 넣어서 상세조회 해보기
            }
        }
        br.setFilelist(bb);
        System.out.println(br);
        return br;
    }

    // 로그인한 유저가 스크랩한 유저 목록에 있는지 없는지
    @Override
    public Boolean getUserIdScrappingList(int rid, String username) {
        List<UserScrap> userScrapList = userScrapDao.findAllByBoardrecipe_rid(rid);
        List<String> usernameList = new ArrayList<>();
        Boolean isScrapping = true;
        for (int i = 0; i < userScrapList.size(); i++) {
            usernameList.add(userScrapList.get(i).getUser().getNickname());
        }
        if (usernameList.contains(username)) {
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
        // r.setImage();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time = new Date();
        String time1 = format1.format(time);
        br.setRegdate(time1);
        br.setTitle(brdetail.getTitle());
        User u = userdao.findByNickname(brdetail.getUser_name());
        br.setUser(u);
        br = boardRecipeDao.save(br);

        //해당 file board 번호 업데이트
        String str = (String) brdetail.getFile();
        String text = str.replace("[", "").replace("]", "");
        System.out.println(text);
        String[] wpqkf = text.split(",");
        for(int i = 0; i < wpqkf.length; i ++){
            UploadFile file = filedao.findById(Integer.parseInt(wpqkf[i])).orElseThrow();
            file.setBoardno(br.getRid());
            filedao.save(file);
        }
        
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
        boardRecipeDao.save(br);
    }

    @Override
    public void deleteById(int bid) {
        boardRecipeDao.deleteById(bid);
    }

}