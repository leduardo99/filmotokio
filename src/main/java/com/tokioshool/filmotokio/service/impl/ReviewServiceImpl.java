package com.tokioshool.filmotokio.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tokioshool.filmotokio.domain.Review;
import com.tokioshool.filmotokio.repository.ReviewRepository;
import com.tokioshool.filmotokio.service.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;
	
	@Override
	public List<Review> findAll() {
		return reviewRepository.findAll();
	}

}
