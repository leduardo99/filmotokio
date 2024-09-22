package com.tokioshool.filmotokio.service.impl;

import java.util.ArrayList;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tokioshool.filmotokio.domain.Film;
import com.tokioshool.filmotokio.repository.FilmRepository;
import com.tokioshool.filmotokio.service.FilmService;

@Service
public class FilmServiceImpl implements FilmService {
	
	@Autowired
	private FilmRepository filmRepository;

	@Override
	public Set<Film> findAll() {
			return filmRepository.findAll();
	}

	@Override
	public ArrayList<Film> findByTitlePiece(String title) {
		return filmRepository.findByTitleContainingIgnoreCase(title);
	}

	@Override
	public Film findByTitle(String title) {
		return filmRepository.findByTitle(title);
	}

	@Override
	public boolean add(Film film) {
		film.setMigrate(false);
		filmRepository.save(film);
		return true;
	}

	@Override
	public Film findById(long id) {
		return filmRepository.findById(id);
	}
}