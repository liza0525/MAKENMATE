package com.cocktail.model.Board;

import java.io.Serializable;

import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name = "board")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Bdetail implements Serializable{
    private int bid;

    private String title;

    private byte[] file;

    private String contents;
    
    private String regdate;
    
    private String user_name;
}