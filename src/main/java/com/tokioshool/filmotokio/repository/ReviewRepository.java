package com.tokioshool.filmotokio.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tokioshool.filmotokio.domain.Review;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {
	List<Review>findAll();
}
