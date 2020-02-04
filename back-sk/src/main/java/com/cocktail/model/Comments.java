package com.cocktail.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cmid;

    private String content;
    private String regdate;

    // @OneToMany
    // private List<User> UserArray = new ArrayList<>();
    // @OneToMany
    // private List<Cocktail> cocktailArray = new ArrayList<>();
    // @OneToMany
    // private List<board> boardArray = new ArrayList<>();
    // @OneToMany
    // private List<BoardRecipe> BoardRecipeArray = new ArrayList();
}
