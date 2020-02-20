package com.cocktail.model.board;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Bdetail implements Serializable {
    private int bid;

    private String title;

    private Object file;

    private String contents;

    private String regdate;

    private String user_name;
    
    private ArrayList<String> filelist;
}