package com.tokioshool.filmotokio.service;

import java.util.List;

import com.tokioshool.filmotokio.domain.Film;
import com.tokioshool.filmotokio.domain.Score;
import com.tokioshool.filmotokio.domain.User;

public interface ScoreService {
	public boolean add(Score score, Film film, User user);
	public List<Score> findAll();
	public List<Score> findByFilmName(String name);
}
