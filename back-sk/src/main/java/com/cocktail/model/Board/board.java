package com.cocktail.model.board;

import java.io.Serializable;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.cocktail.model.Comments;

@Entity
public class board implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bid;

    @Column
    private String title;

    @Lob
    private byte[] file;

    @Column
    private String contents;
    @Column
    private String regdate;

    @Column
    private int user_uid;

    @Override
    public String toString() {
        return "board [bid=" + bid + ", contents=" + contents + ", file=" + Arrays.toString(file) + ", regdate="
                + regdate + ", title=" + title + ", user_uid=" + user_uid + "]";
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    public int getUser_uid() {
        return user_uid;
    }

    public void setUser_uid(int user_uid) {
        this.user_uid = user_uid;
    }

    @ManyToOne(targetEntity = Comments.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "cmid")
    private Comments comments;
}