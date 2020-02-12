package com.cocktail.model.comments;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.cocktail.model.boardRecipe.BoardRecipe;
import com.cocktail.model.like.BoardRecipeCommentsLike;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "boardrecipecomments")
@ToString
public class BoardRecipeComments implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cmid")
    private int cmid;

    private String content;
    private String regdate;

    private int user_uid;
    // private int cocktail_cid;
    @ManyToOne
    @JoinColumn(name = "boardrecipe_rid")
    @JsonBackReference
    private BoardRecipe boardRecipe;

    // @ManyToOne
    // @JoinColumn(name = "user_uid", insertable = false, updatable = false)
    // private User user;

    // @ManyToOne
    // @JoinColumn(name = "board_bid", insertable = false, updatable = false)
    // private board board;
    // @OneToMany
    // private List<User> UserArray = new ArrayList<>();
    // @OneToMany
    // private List<Cocktail> cocktailArray = new ArrayList<>();
    // @OneToMany
    // private List<board> boardArray = new ArrayList<>();
    // @OneToMany
    // private List<BoardRecipe> BoardRecipeArray = new ArrayList();
    
    @OneToMany(mappedBy = "comments")
    @JsonManagedReference
    private List<BoardRecipeCommentsLike> comments = new ArrayList<>();
    
    private int count;
}
