package com.cocktail.model.user;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cocktail.model.boardRecipe.BoardRecipe;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "userscrap")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UserScrapId.class)
public class UserScrap implements Serializable {
    @Id
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_uid")
    private User user;

    @Id
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "boardrecipe_rid")
    private BoardRecipe boardrecipe;
}