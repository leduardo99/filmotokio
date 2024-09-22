package com.tokioshool.filmotokio.service;

import com.tokioshool.filmotokio.domain.User;

public interface UserService {
	public boolean add(User user);
	public boolean addAdmin(User user);
	public void remove(User user);
	public User findByUsername(String username);
	public User findByActive(boolean bool);
	public boolean modifyActive(long id);
	public boolean modifyLastLogin(long id);
}
