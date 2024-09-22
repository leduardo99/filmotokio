package com.tokioshool.filmotokio.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tokioshool.filmotokio.domain.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	public User findByUsername(String username);
	public User findByActive(boolean bool);
	public User findById(long id);
}
