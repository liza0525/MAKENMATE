package com.cocktail.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "cocktail")
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

    @JsonIgnore
    @OneToMany
    @JoinColumn(name = "cmid")
    private List<Comments> commentsArray = new ArrayList<>();

    // @ManyToOne(targetEntity = Comments.class, fetch = FetchType.EAGER)
    // @JoinColumn(name = "comments_cmid")
    // private Comments comments;

}
