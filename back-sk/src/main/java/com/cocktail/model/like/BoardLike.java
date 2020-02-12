package com.cocktail.model.like;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cocktail.model.board.Board;
import com.cocktail.model.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "boardlike")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_uid")
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "board_bid")
    @JsonBackReference
    private Board board;
}