package com.tokioshool.filmotokio.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tokioshool.filmotokio.domain.Film;
import com.tokioshool.filmotokio.domain.Score;
import com.tokioshool.filmotokio.domain.User;
import com.tokioshool.filmotokio.repository.ScoreRepository;
import com.tokioshool.filmotokio.service.ScoreService;

@Service
public class ScoreServiceImpl implements ScoreService {

	@Autowired
	private ScoreRepository scoreRepository;

	@Override
	public boolean add(Score score, Film film, User user) {
		score.setUser_score(user);
		score.setFilm_score(film);
		scoreRepository.save(score);
		return true;
	}

	@Override
	public List<Score> findAll() {
		return scoreRepository.findAll();
	}

	@Override
	public List<Score> findByFilmName(String name) {
		return scoreRepository.findByNameFilm(name);
	}
}
