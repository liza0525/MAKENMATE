package com.cocktail.model.meeting;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "meeting")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Meeting implements Serializable{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mid;

	@Column
	private String author;
	
    @Column
    private String title;

    @Column
    private String latitude;

    @Column
    private String longitude;
    
    @Column
    private int count;

    @Column
    private String place;

    @Column
    private String date;
    
    @Column
    private int people;
    
    @OneToMany(mappedBy = "meeting")
    @JsonManagedReference
    private List<UserMeeting> usermeeting = new ArrayList<>();


}
