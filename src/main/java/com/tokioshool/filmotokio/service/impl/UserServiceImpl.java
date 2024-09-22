package com.tokioshool.filmotokio.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tokioshool.filmotokio.domain.Role;
import com.tokioshool.filmotokio.domain.User;
import com.tokioshool.filmotokio.repository.RoleRepository;
import com.tokioshool.filmotokio.repository.UserRepository;
import com.tokioshool.filmotokio.security.Constant;
import com.tokioshool.filmotokio.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public boolean add(User user) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setCreationDate(LocalDate.now());
		user.setActive(false);
		Role userRole = roleRepository.findByName(Constant.USER_ROLE);
		user.setRoles(new HashSet<>(Collections.singletonList(userRole)));
		userRepository.save(user);
		return true;
	}
	
	@Override
	public boolean addAdmin(User user) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setCreationDate(LocalDate.now());
		user.setActive(false);
		userRepository.save(user);
		return true;
	}

	@Override
	public void remove(User user) {
		userRepository.delete(user);
		
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public User findByActive(boolean bool) {
		return userRepository.findByActive(bool);
	}

	@Override
	public boolean modifyActive(long id) {
		User user = userRepository.findById(id);
		user.setActive(false);
		userRepository.save(user);
		return true;
	}

	@Override
	public boolean modifyLastLogin(long id) {
		User user = userRepository.findById(id);
		user.setLastLogin(LocalDateTime.now());
		userRepository.save(user);
		return true;
	}
}
