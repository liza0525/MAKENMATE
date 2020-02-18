package com.cocktail.model.meeting;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Mdetail implements Serializable{
	 	private int mid;

		private String author;
		
	    private String title;

	    private String latitude;

	    private String longitude;
	    
	    private String place;

	    private int limit;

	    private String date;
}
