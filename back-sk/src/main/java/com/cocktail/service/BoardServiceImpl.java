package com.cocktail.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cocktail.dao.BoardDao;
import com.cocktail.dao.UserDao;
import com.cocktail.exception.CocktailException;
import com.cocktail.model.board.Bdetail;
import com.cocktail.model.board.Board;
import com.cocktail.model.user.User;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardDao boardDao;

    @Autowired
    private UserDao userDao;

    // 전체조회
    @Override
    public Page<Board> getAllBoard(Pageable pageable) {
        return boardDao.findAll(pageable);
    }

    // 글번호로 상세조회
    @Override
    public Bdetail findById(int bid) {
        Board board = boardDao.findById(bid);
        User u = userDao.getUserByUid(board.getUser().getUid()).orElseThrow(CocktailException::new);
        Bdetail b = new Bdetail();
        b.setBid(board.getBid());
        b.setFile(board.getFile());
        b.setContents(board.getContents());
        SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
        Date time = new Date();
        String time1 = format1.format(time);
        b.setRegdate(time1);
        b.setTitle(board.getTitle());
        b.setUser_name(u.getNickname());
        return b;
    }

    // 글 수정
    @Override
    public void updateById(Bdetail bdetail) {
        Board b = boardDao.findById(bdetail.getBid());
        b.setContents(bdetail.getContents());
        // b.setFile(board.getFile()); //파일 수정
        b.setTitle(bdetail.getTitle());
        boardDao.save(b);
    }

    // 글 삭제
    @Override
    public void deleteById(int bid) {
        boardDao.deleteById(bid);
    }

    // 글작성
    @Override
    public int save(Bdetail bdetail) {
        Board b = new Board();
        String username = bdetail.getUser_name();
        User u = userDao.findByNickname(username);
        b.setContents(bdetail.getContents());
        // b.setFile(bdetail.getFile());
        b.setRegdate(bdetail.getRegdate());
        b.setTitle(bdetail.getTitle());
        b.setUser(u);
        b = boardDao.save(b);
        return b.getBid();
    }
}