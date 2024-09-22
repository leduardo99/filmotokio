package com.tokioshool.filmotokio.repository;

import java.util.ArrayList;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tokioshool.filmotokio.domain.Person;
import com.tokioshool.filmotokio.domain.TypePersonEnum;

@Repository
public interface PersonRepositoy extends CrudRepository<Person, Long> {
	ArrayList<Person> findByTypePersonEnum(TypePersonEnum type);
	Person findByName(String name);
	Person findById(long id);
	Set<Person> findAll();
}
