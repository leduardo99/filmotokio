package com.tokioshool.filmotokio.repository;

import java.util.ArrayList;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tokioshool.filmotokio.domain.Film;

@Repository
public interface FilmRepository extends CrudRepository<Film, Long> {
	Set<Film> findAll();
	ArrayList<Film> findByTitleContainingIgnoreCase(String title);
	Film findByTitle(String title);
	Film findById(long id);
}
