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

import com.cocktail.dao.BoardDao;
import com.cocktail.dao.FileDAO;
import com.cocktail.dao.UserDao;
import com.cocktail.exception.CocktailException;
import com.cocktail.model.UploadFile;
import com.cocktail.model.board.Bdetail;
import com.cocktail.model.board.Board;
import com.cocktail.model.user.User;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardDao boardDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private FileDAO filedao;
    
    // 전체조회
    @Override
    public List<Bdetail> getAllBoard() {
        List<Board> b = boardDao.findAll();
        List<Bdetail> bdlist = new ArrayList<>();
        for (int i = 0; i < b.size(); i++){
            String name = b.get(i).getUser().getNickname();
            Bdetail bd = new Bdetail();
            bd.setBid(b.get(i).getBid());
            bd.setContents(b.get(i).getContents());
            bd.setFile(b.get(i).getFile());
            bd.setRegdate(b.get(i).getRegdate());
            bd.setTitle(b.get(i).getTitle());
            bd.setUser_name(name);
            bdlist.set(i, bd);
        }
        return bdlist;
    }
    
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

        List<UploadFile> file = filedao.list(board.getBid());
        ArrayList<String> bb = new ArrayList<>();
        if(file.size() != 0) {
            for(int i=0; i < file.size(); i++){
                bb.add(i, file.get(i).getFileName());
            }
        }
        System.out.println(bb);
        b.setFilelist(bb);

        
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
        b.setContents(bdetail.getContents());
        // b.setFile(bdetail.getFile());
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time = new Date();
        String time1 = format1.format(time);
        b.setRegdate(time1);
        b.setTitle(bdetail.getTitle());
        User u = userDao.findByNickname(bdetail.getUser_name());
        b.setUser(u);
        b = boardDao.save(b);

        //해당 file board 번호 업데이트
        String str = (String) bdetail.getFile();
        String text = str.replace("[", "").replace("]", "");
 
        if (!text.equals("49104755")) {
        String[] wpqkf = text.split(",");
        for(int i = 0; i < wpqkf.length; i ++){
            UploadFile file = filedao.findById(Integer.parseInt(wpqkf[i]));
            file.setBoardno(b.getBid());
            filedao.save(file);
        }
        }
        return b.getBid();
    }
}