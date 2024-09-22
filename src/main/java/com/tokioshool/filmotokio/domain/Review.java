package com.tokioshool.filmotokio.domain;

import java.time.LocalDate;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity(name = "review")
@Table(name = "reviews")
public class Review {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column
	private String title;
	
	@Column
	private String textReview;
	
	@Column 
	private LocalDate date;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User reUser;
	
	@ManyToOne
	@JoinColumn(name = "film_id")
	private Film film_review;
}
