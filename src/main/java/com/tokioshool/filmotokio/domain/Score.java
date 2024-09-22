package com.tokioshool.filmotokio.domain;

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
@Entity(name = "score")
@Table(name = "scores")
public class Score {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column
	private int value;
	
	@Column
	private String nameFilm;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user_score;
	
	@ManyToOne
	@JoinColumn(name = "film_id")
	private Film film_score;
	
	@Override
	public String toString() {
		
		return super.toString();
	}
}
