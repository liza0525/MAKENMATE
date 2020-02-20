package com.cocktail.model.boardRecipe;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.cocktail.model.like.BoardRecipeLike;
import com.cocktail.model.user.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "boardrecipe")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BoardRecipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rid;

    @Column
    private String title;

    @Column
    private String contents;

    @Column
    private String regdate;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_uid", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JsonBackReference
    private User user;


    @OneToMany(mappedBy = "boardRecipe")
    @JsonManagedReference
    private List<BoardRecipeLike> boardRecipeLike = new ArrayList<>();

}