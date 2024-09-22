package com.tokioshool.filmotokio.service;

import java.util.ArrayList;
import java.util.Set;

import com.tokioshool.filmotokio.domain.Film;

public interface FilmService {
	public boolean add(Film film);
	public Set<Film> findAll();
	public ArrayList<Film> findByTitlePiece(String title);
	public Film findByTitle(String title);
	public Film findById(long id);
}
