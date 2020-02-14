package com.cocktail.model.like;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cocktail.model.Cocktail;
import com.cocktail.model.boardRecipe.BoardRecipe;
import com.cocktail.model.comments.CocktailComments;
import com.cocktail.model.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "boardrecipelike")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardRecipeLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_uid")
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "boardrecipe_rid")
    @JsonBackReference
    private BoardRecipe boardRecipe;
}