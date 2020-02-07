package com.cocktail.model.user;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cocktail.model.Cocktail;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "cocktaillike")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(CocktailLike.class)
@ToString
public class CocktailLike implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_uid")
    @JsonBackReference
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "cocktail_cid")
    @JsonBackReference
    private Cocktail cocktail;
}