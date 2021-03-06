package com.cocktail.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.cocktail.model.like.CocktailLike;
import com.cocktail.model.user.User;
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
@Table(name = "cocktail")
@ToString
public class Cocktail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cid")
    private int cid;

    private String cname;
    private String base;
    private String degree;
    private String description;
    private String method;
    private String material;
    private String image;
    private String snack;
    private String bar;
    
    // @JsonIgnore
    // @OneToMany
    // @JoinColumn(name = "cmid")
    // private List<Comments> commentsArray = new ArrayList<>();

    // @ManyToOne(targetEntity = Comments.class, fetch = FetchType.EAGER)
    // @JoinColumn(name = "comments_cmid")
    // private Comments comments;

    @OneToMany(mappedBy = "cocktail")
    @JsonManagedReference
    private List<CocktailLike> cocktails = new ArrayList<>();

}
