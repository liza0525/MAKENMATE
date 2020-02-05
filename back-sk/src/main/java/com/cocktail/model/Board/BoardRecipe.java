package com.cocktail.model.board;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.cocktail.model.user.UserScrap;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Id;

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
    private String image;

    @Column
    private String contents;

    @Column
    private String regdate;
    
    // @ManyToOne
    // @JoinColumn(name = "user_uid")
    @Column
    private int user_uid;
    @JsonManagedReference
    @OneToMany(mappedBy = "boardrecipe")
    private List<UserScrap> userScrapList = new ArrayList<>();
}