package com.cocktail.model.boardRecipe;

import java.io.Serializable;

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
public class BRdetail implements Serializable {
    private int rid;

    private String title;

    private String contents;

    private String regdate;

    private String user_name;
}