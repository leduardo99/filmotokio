package com.tokioshool.filmotokio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilmDTO {
	private long id;
	private String title;
	private int year;
	private int duration;
	private String synopsis;
}
