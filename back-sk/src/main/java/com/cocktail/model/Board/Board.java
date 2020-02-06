package com.cocktail.model.board;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cocktail.model.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "board")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Board implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bid;

    @Column
    private String title;

    @Lob
    private byte[] file;

    @Column
    private String contents;

    @Column
    private String regdate;

    @Column
    private int user_uid;

    // @JsonIgnore
    // @OneToMany
    // @JoinColumn(name = "cmid")
    // private List<Comments> commentsArray = new ArrayList<>();

    // @ManyToOne
    // @JoinColumn(name = "user_uid", referencedColumnName = "uid")
    // private User user_uid;
    // @ManyToOne(targetEntity = Comments.class, fetch = FetchType.EAGER)
    // @JoinColumn(name = "cmid")
    // private Comments comments;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_uid", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    // @Column
    // private int user_uid;
}
