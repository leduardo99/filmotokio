package com.tokioshool.filmotokio.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tokioshool.filmotokio.domain.Score;

@Repository
public interface ScoreRepository extends CrudRepository<Score, Long> {
	List<Score> findAll();
	List<Score> findByNameFilm(String name);
}
