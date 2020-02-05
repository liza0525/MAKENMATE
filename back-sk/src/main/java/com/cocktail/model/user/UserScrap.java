package com.cocktail.model.user;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cocktail.model.board.BoardRecipe;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "userscrap")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserScrap {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_uid")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "boardrecipe_rid")
    private BoardRecipe boardrecipe;
}