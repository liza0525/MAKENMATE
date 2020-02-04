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
public class Cocktail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    // @ManyToOne(targetEntity = Comments.class, fetch = FetchType.EAGER)
    // @JoinColumn(name = "cmid")
    // private Comments comments;

}
