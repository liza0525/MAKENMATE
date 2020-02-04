package com.cocktail.model.board;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.cocktail.model.Comments;

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
public class board implements Serializable {

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
    // @ManyToOne
    // @JoinColumn(name = "user_uid", referencedColumnName = "uid")
    // private User user_uid;
    // @ManyToOne(targetEntity = Comments.class, fetch = FetchType.EAGER)
    // @JoinColumn(name = "cmid")
    // private Comments comments;
}