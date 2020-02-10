package com.cocktail.model.boardRecipe;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.cocktail.model.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
    private String image;

    @Column
    private String regdate;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_uid", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    //@JsonIgnore
    private User user;

    // @Column
    // private int user_uid;
    // @JsonManagedReference
    // @OneToMany(mappedBy = "boardrecipe")
    // private List<UserScrap> userScrapList = new ArrayList<>();
}