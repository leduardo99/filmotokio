package com.tokioshool.filmotokio.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
	private long id;
	private String title;
	private String textReview;
	private LocalDate date;
	private FilmDTO film_review;
}
