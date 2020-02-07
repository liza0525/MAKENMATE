package com.cocktail.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "comments")
@ToString
public class Comments implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cmid")
    private int cmid;

    private String content;
    private String regdate;

    @NonNull
    private int user_uid;
    // private int cocktail_cid;
    @ManyToOne
    @JoinColumn(name = "cocktail_cid")
    @JsonBackReference
    private Cocktail cocktail;

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
}
