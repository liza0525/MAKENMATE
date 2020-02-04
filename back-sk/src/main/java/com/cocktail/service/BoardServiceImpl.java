package com.cocktail.service;

import java.util.List;

import com.cocktail.dao.BoardDao;
import com.cocktail.dao.UserDao;
import com.cocktail.exception.CocktailException;
import com.cocktail.model.board.Bdetail;
import com.cocktail.model.board.board;
import com.cocktail.model.user.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardDao boardDao;

    @Autowired
    private UserDao userDao;

    //전체조회
    @Override
    public List<board> getAllBoard() {
        return boardDao.findAll();
    }

    //글번호로 상세조회
    @Override
    public Bdetail findById(int bid) {
        board board = boardDao.findById(bid); 
        User u = userDao.findByUid(board.getUser_uid()).orElseThrow(CocktailException::new);
        Bdetail b = new Bdetail();
        b.setBid(board.getBid());
        b.setFile(board.getFile());
        b.setContents(board.getContents());
        b.setRegdate(board.getRegdate());
        b.setTitle(board.getContents());
        b.setUser_name(u.getNickname());
        System.out.println(u.getNickname());
        return b;
    }

    //글 작성
    @Override
    public board save(board board, String username) {
        //nicknamed으로 유저 pk 검색
        User u  = userDao.findByNickname(username);
        //board.setUser_uid(u);
        board.setUser_uid(u.getUid());

        boardDao.save(board);
        return board;
    }

    //글 수정
    @Override
    public void updateById(int bid, board board, String username) {
        board b = boardDao.findById(bid);
        System.out.println(b);
        b.setContents(board.getContents());
        //b.setFile(board.getFile()); //파일 수정
        b.setTitle(board.getTitle());
        // User u  = userDao.findByNickname(username);
        // b.setUser_uid(u.getUid());
        boardDao.save(b);
    }

	@Override
	public void deleteById(int bid) {
        boardDao.deleteById(bid);
	}
}