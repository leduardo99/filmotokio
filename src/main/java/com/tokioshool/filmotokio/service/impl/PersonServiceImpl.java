package com.tokioshool.filmotokio.service.impl;

import java.util.ArrayList;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tokioshool.filmotokio.domain.Person;
import com.tokioshool.filmotokio.domain.TypePersonEnum;
import com.tokioshool.filmotokio.repository.PersonRepositoy;
import com.tokioshool.filmotokio.service.PersonService;

@Service
public class PersonServiceImpl implements PersonService {
	
	@Autowired
	private PersonRepositoy personRepository;
	
	@Override
	public boolean add(Person person) {
		personRepository.save(person);
		return true;
	}

	@Override
	public ArrayList<Person> findByTypePersonEnum(TypePersonEnum type) {
		return personRepository.findByTypePersonEnum(type);
	}

	@Override
	public Person findByName(String name) {
		return personRepository.findByName(name);
	}

	@Override
	public Person findById(long id) {
		return personRepository.findById(id);
	}

	@Override
	public Set<Person> findAll() {
		return personRepository.findAll();
	}
}
