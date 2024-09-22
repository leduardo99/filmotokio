package com.tokioshool.filmotokio.service;

import java.util.ArrayList;
import java.util.Set;

import com.tokioshool.filmotokio.domain.Person;
import com.tokioshool.filmotokio.domain.TypePersonEnum;

public interface PersonService {
	public boolean add(Person person);
	public ArrayList<Person> findByTypePersonEnum(TypePersonEnum type);
	public Person findByName(String name);
	public Person findById(long id);
	public Set<Person> findAll();
}
