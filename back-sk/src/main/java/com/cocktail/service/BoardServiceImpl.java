package com.cocktail.service;

import java.util.List;

import com.cocktail.dao.BoardDao;
import com.cocktail.dao.UserDao;
import com.cocktail.exception.CocktailException;
import com.cocktail.model.board.Bdetail;
import com.cocktail.model.board.Board;
import com.cocktail.model.user.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardDao boardDao;

    @Autowired
    private UserDao userDao;

    // 전체조회
    @Override
    public List<Board> getAllBoard() {
        return boardDao.findAll();
    }

    // 글번호로 상세조회
    @Override
    public Bdetail findById(int bid) {
        Board board = boardDao.findById(bid);
        User u = userDao.findByUid(board.getUser().getUid()).orElseThrow(CocktailException::new);
        Bdetail b = new Bdetail();
        b.setBid(board.getBid());
        b.setFile(board.getFile());
        b.setContents(board.getContents());
        b.setRegdate(board.getRegdate());
        b.setTitle(board.getTitle());
        b.setUser_name(u.getNickname());
        System.out.println(u.getNickname());
        return b;
    }

    // 글 수정
    @Override
    public void updateById(Bdetail bdetail) {
        Board b = boardDao.findById(bdetail.getBid());
        b.setContents(bdetail.getContents());
        //b.setFile(board.getFile()); //파일 수정
        b.setTitle(bdetail.getTitle());
        boardDao.save(b);
    }

    //글 삭제
	@Override
	public void deleteById(int bid) {
        boardDao.deleteById(bid);
	}

    //글작성
    @Override
    public int save(Bdetail bdetail) {
        Board b = new Board();
        String username = bdetail.getUser_name();
        User u = userDao.findByNickname(username);
        System.out.println(username);
        b.setContents(bdetail.getContents());
        //b.setFile(bdetail.getFile());
        b.setRegdate(bdetail.getRegdate());
        b.setTitle(bdetail.getTitle());
        System.out.println(u);
        b.setUser(u);
        b = boardDao.save(b);
        return b.getBid();
    }
}